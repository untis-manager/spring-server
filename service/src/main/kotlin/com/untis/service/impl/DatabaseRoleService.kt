package com.untis.service.impl

import com.untis.database.repository.RoleRepository
import com.untis.database.repository.UserRepository
import com.untis.model.Role
import com.untis.model.UserPermissions
import com.untis.service.RoleService
import com.untis.service.mapping.createRoleEntity
import com.untis.service.mapping.createRoleModel
import com.untis.service.mapping.createUserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class DatabaseRoleService @Autowired constructor(

    val roleRepository: RoleRepository,

    val userRepository: UserRepository

) : RoleService {

    override fun getAll() = roleRepository
        .findAll()
        .map(::createRoleModel).toSet()


    override fun getById(id: Long) = roleRepository
        .findById(id).get()
        .let(::createRoleModel)

    override fun getByIdAndUser(id: Long, user: Long): Role? {
        throw NotImplementedError("A role is not bound to a specific user")
    }

    override fun getAllForUser(user: Long): List<Role> {
        throw NotImplementedError("A role is not bound to a specific user")
    }


    override fun getUsersWithRole(roleId: Long) = userRepository
        .getByRole(roleId)
        .map(::createUserModel)
        .toSet()


    override fun existsById(id: Long) = roleRepository
        .existsById(id)


    override fun create(model: Role) =
        model.let(::createRoleEntity)
            .let(roleRepository::save)
            .let(::createRoleModel)


    override fun delete(id: Long) = roleRepository
        .findById(id).get()
        .apply(roleRepository::delete)
        .let(::createRoleModel)


    @Suppress("DuplicatedCode")
    override fun update(
        role: Role
    ): Role {
        if (!roleRepository.existsById(role.id!!)) throw IllegalStateException("Can't find role with id '${role.id}'")

        return createRoleEntity(role)
            .let(roleRepository::save)
            .let(::createRoleModel)
    }

    override fun createOrGetAdminRole(): Role {
        val existing = roleRepository.findAll().firstOrNull { it.name == "admin" }
        if (existing != null) return createRoleModel(existing)

        val role = Role(
            id = null,
            name = "admin",
            permissions = UserPermissions.Admin
        )

        val roleEntity = createRoleEntity(role)
        val savedEntity = roleRepository.save(roleEntity)
        return createRoleModel(savedEntity)
    }

}