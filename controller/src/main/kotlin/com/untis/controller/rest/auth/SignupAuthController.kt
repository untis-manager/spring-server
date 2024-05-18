package com.untis.controller.rest.auth

import com.untis.common.toLocalDate
import com.untis.common.toUUID
import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.auth.AdminUserRequest
import com.untis.controller.body.request.auth.TokenUserRequest
import com.untis.controller.body.request.auth.UserRequest
import com.untis.controller.body.request.personal.EmailUserRequest
import com.untis.controller.body.request.personal.EmailValidationTokenRequest
import com.untis.controller.body.response.FullUserResponse
import com.untis.controller.body.response.SignUpTokenResponse
import com.untis.controller.util.notFound
import com.untis.controller.util.ok
import com.untis.controller.validating.*
import com.untis.model.*
import com.untis.model.exception.RequestException
import com.untis.service.*
import com.untis.service.mapping.createGenderInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * Controller that handles creation of accounts.
 */
@RestController
@RequestMapping("/auth/signup/")
class SignupAuthController @Autowired constructor(

    override val signUpTokenService: SignUpTokenService,

    override val groupService: GroupService,

    private val serverSettingsService: ServerSettingsService,

    override val userService: UserService,

    private val userSecurityService: UserSecurityService,

    override val tokenService: SecurityTokenService,

    private val passwordEncoder: PasswordEncoder,

    @Value("\${untis.admin.secret}")
    private val secret: String

) : ControllerScope() {

    /**
     * Any client can submit an email and have an [SecurityTokenType.ValidateEmail] token sent to the email.
     *
     * If the email is already registered, no email is sent.
     *
     * Requires [SignUpMode.Free] and [SignUpMode.Free.needEmailVerification] to be enabled.
     *
     * @param requestBody The email to validate
     */
    @PutMapping("/free/")
    fun signupFreeEmail(
        @RequestBody requestBody: EmailValidationTokenRequest
    ): ResponseEntity<Any> {
        validateSignUpMode(true) { it is SignUpMode.Free }
        validateUserEmailUnique(requestBody.email)

        val user = userService.getByEmail(requestBody.email)
        if (user == null) {
            userSecurityService.sendEmailValidationEmail(requestBody.email)
        }

        return ResponseEntity.accepted().build()
    }

    /**
     * Any client can create an account.
     *
     * Requires [SignUpMode.Free] to be enabled.
     *
     * If [SignUpMode.Free.needEmailVerification] is enabled, the user also needs to pass a valid email-verification-token.
     *
     * @param requestBody The information to create a new user, and possibly the token
     */
    @PostMapping("/free/")
    fun signupFree(
        @RequestBody requestBody: EmailUserRequest
    ): ResponseEntity<FullUserResponse> {
        validateSignUpMode { it is SignUpMode.Free }

        val mode = serverSettingsService.get().signUpMode as SignUpMode.Free

        // Check for email token
        if (mode.needEmailVerification) {
            validateEmailVerifyingToken(requestBody.emailVerificationToken, requestBody.userData.email, mode)
        }

        // Validate inputs
        requestBody.userData.validateContents()

        // Create user
        val group = groupService.getById(mode.defaultGroupId)
        val permissions = groupService.getMergedPermissions(group.id!!)
        val user = requestBody.userData
            .toUser(permissions)
            .let {
                userService.create(it, group.id!!)
            }

        return ResponseEntity.ok().body(FullUserResponse.create(user))
    }


    /**
     * Any client can submit 1. a sign-up-token and 2. information about the new user.
     *
     * The user that is created will have its data from the token and the passed information. The token takes priority.
     *
     * Requires [SignUpMode.Token] to be active
     *
     * @param requestBody The user info and sign up token
     */
    @PostMapping("/signup/token")
    fun signupToken(
        @RequestBody requestBody: TokenUserRequest
    ): ResponseEntity<User> {
        validateSignUpMode { it == SignUpMode.Token }

        // Validate inputs
        validateUUIDString(requestBody.token)
        validateSignUpTokenExists(requestBody.token.toUUID())
        requestBody.userData.validateContents()

        // Check token
        val token = validateSignUpToken(requestBody.token)
        if (token.userData.groupIds.isEmpty()) return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR) // At least one group is needed

        // Create user
        val groups = token.userData.groupIds.map { groupService.getById(it) }
        val mergedPermissions = groupService.getMergedPermissions(groups.first().id!!)
        val user = userFromToken(token, requestBody.userData, mergedPermissions)
            .let(userService::create)

        userService.updateGroups(user.id!!, token.userData.groupIds)

        // We use it so we mark it
        signUpTokenService.incrementUsed(token.id!!)

        return ResponseEntity.ok().body(user)
    }

    /**
     * Any client can submit a sign-up-token uuid to check if it exists and receive the data stored in it
     *
     * @param id The path variable id of the token
     */
    @GetMapping("/token/{id}/")
    fun getSignupToken(
        @PathVariable id: String
    ): ResponseEntity<SignUpTokenResponse> {
        validateUUIDString(id)
        validateSignUpTokenExists(id.toUUID())

        val token = signUpTokenService.getForToken(id.toUUID())

        val stillValid = token != null &&
                (token.timesUsed == 0 || !token.isSingleUse) &&
                token.expirationDate.isAfter(LocalDateTime.now())

        return if (!stillValid) notFound()
        else ok(SignUpTokenResponse.create(token!!))
    }

    /**
     * Any client can create a user with [RolePermissions.All] authorities.
     *
     * The client needs to pass the 'untis.admin.secret' password.
     *
     * @param requestBody The information about the user and the secret.
     */
    @PostMapping("/secret/")
    fun createWithSecret(
        @RequestBody requestBody: AdminUserRequest
    ): ResponseEntity<FullUserResponse> {
        TODO("Figure out how to do this properly")
    }

    private fun validateSignUpMode(emailVerification: Boolean = false, matcher: (SignUpMode) -> Boolean) {
        val mode = serverSettingsService.get().signUpMode
        if (!matcher(mode)) throw RequestException.WrongSignUpMode(mode)

        if (mode is SignUpMode.Free) {
            if (mode.needEmailVerification != emailVerification) throw RequestException.NoEmailVerificationNeeded
        }
    }

    private fun validateEmailVerifyingToken(token: String?, passedEmail: String, mode: SignUpMode.Free) {
        if (token == null) throw RequestException.InvalidToken("")
        if (mode.needEmailVerification) {
            validateUUIDString(token)
            val uuid = token.toUUID()

            val isValid = userSecurityService.isEmailValidationTokenValid(uuid, passedEmail)
            if (!isValid) throw RequestException.InvalidToken(uuid.toString())

            val ecToken = tokenService.getByToken(uuid)!!
            tokenService.markAsUsed(ecToken.id!!)
        }
    }

    private fun UserRequest.toUser(
        permissions: PermissionsBundle
    ) = User(
        id = null,
        addressInfo = AddressInfo(
            country = country,
            city = city,
            zip = zipCode,
            street = street,
            houseNumber = houseNumber
        ),
        firstName = firstName,
        lastName = lastName,
        email = email,
        encodedPassword = passwordEncoder.encode(password),
        birthDate = birthday.toLocalDate(),
        gender = createGenderInfo(gender),
        permissions = permissions
    )


    private fun UserRequest.validateContents() {
        validateEmail(email)
        validateUserEmailUnique(email)
        validateGenderString(gender)
        validateName(firstName)
        validateName(lastName)
        validatePassword(password)
        validateLocalDate(birthday)
    }

    private fun validateSignUpToken(token: String): SignUpToken {
        validateUUIDString(token)
        val supToken = signUpTokenService.getForToken(token.toUUID())!!
        if (!signUpTokenService.isValid(supToken.token)) throw RequestException.InvalidToken(token)
        return supToken
    }

    private fun userFromToken(token: SignUpToken, body: UserRequest, permissions: PermissionsBundle) = User(
        id = null,
        addressInfo = AddressInfo(
            country = token.userData.country ?: body.country,
            city = token.userData.city ?: body.city,
            zip = token.userData.zipCode ?: body.zipCode,
            street = token.userData.street ?: body.street,
            houseNumber = token.userData.houseNumber ?: body.houseNumber
        ),
        firstName = token.userData.firstName ?: body.firstName,
        lastName = token.userData.lastName ?: body.lastName,
        email = token.userData.email ?: body.email,
        encodedPassword = passwordEncoder.encode(body.password),
        birthDate = token.userData.birthday ?: body.birthday.toLocalDate(),
        gender = token.userData.gender ?: createGenderInfo(body.gender),
        permissions = permissions
    )

}