package com.untis.controller.rest.user

import com.untis.controller.base.ControllerScope
import com.untis.controller.body.request.personal.EditProfileRequest
import com.untis.controller.body.response.FullUserResponse
import com.untis.controller.body.response.RoleResponse
import com.untis.controller.validating.validateGenderString
import com.untis.controller.validating.validateLocalDate
import com.untis.model.AddressInfo
import com.untis.model.User
import com.untis.service.UserService
import com.untis.service.mapping.createGenderInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/user/")
class PersonalUserController @Autowired constructor(

    override val userService: UserService

) : ControllerScope() {

    /**
     * Endpoint that returns information about a users own profile.
     *
     * @param user The authenticated user
     */
    @GetMapping
    fun profile(
        @AuthenticationPrincipal user: User
    ): FullUserResponse = FullUserResponse.create(user)

    /**
     * Endpoint that allows a user to edit their profile options.
     *
     * Only accessible to those who are allowed to
     *
     * @param user The authenticated user
     * @param requestBody The request body with update information
     */
    @PatchMapping
    fun editProfile(
        @AuthenticationPrincipal user: User,
        @RequestBody requestBody: EditProfileRequest
    ): FullUserResponse {
        validateGenderString(requestBody.gender)
        validateLocalDate(requestBody.birthday)

        val updatedUser = user.copy(
            firstName = requestBody.firstName,
            lastName = requestBody.lastName,
            addressInfo = AddressInfo(
                country = requestBody.country,
                city = requestBody.city,
                zip = requestBody.zip,
                street = requestBody.street,
                houseNumber = requestBody.houseNumber
            ),
            gender = createGenderInfo(requestBody.gender),
            birthDate = LocalDate.parse(requestBody.birthday),
        )

        return userService
            .update(updatedUser)
            .let { FullUserResponse.create(it) }
    }

    /**
     * Endpoint that allows a user to delete their profile
     *
     * Only accessible to those who are allowed to
     *
     * @param user The authenticated user
     */
    @DeleteMapping
    fun deleteProfile(
        @AuthenticationPrincipal user: User
    ): FullUserResponse {
        userService.delete(user.id!!)

        return FullUserResponse.create(user)
    }

    /**
     * Endpoint that return the role information for the authenticated user
     *
     * @param user The authenticated user
     * @return The role
     */
    @GetMapping("/role/")
    fun getRole(
        @AuthenticationPrincipal user: User
    ): RoleResponse = RoleResponse.create(user.role)

}