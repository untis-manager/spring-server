package com.untis.controller.validating

import com.untis.controller.body.request.role.PermissionRequest
import com.untis.model.Permission
import com.untis.model.exception.RequestException

@Suppress("UNCHECKED_CAST")
fun <Type : Permission> validatePermissionRequest(
    request: PermissionRequest
): Type {
    val permission = when (request.type) {
        "simple" -> {
            when (request.stage) {
                1 -> Permission.Simple.Read
                2 -> Permission.Simple.Write
                else -> null
            }
        }

        "scoped" -> {
            when (request.stage) {
                1 -> Permission.Scoped.Not
                2 -> Permission.Scoped.Own
                3 -> Permission.Scoped.All
                4 -> Permission.Scoped.Edit
                else -> null
            }
        }

        "profile" -> {
            when (request.stage) {
                1 -> Permission.Profile.Not
                2 -> Permission.Profile.Read
                3 -> Permission.Profile.Edit
                else -> null
            }
        }

        "users" -> {
            when (request.stage) {
                1 -> Permission.Users.Not
                2 -> Permission.Users.Partial
                3 -> Permission.Users.Full
                4 -> Permission.Users.Edit
                else -> null
            }
        }

        else -> throw RequestException.ParamsBad("Did not find a permission type for '${request.type}'")
    }

    if (permission == null) throw RequestException.ParamsBad("Stage '${request.stage}' for '${request.type}' not valid.")

    val cast = permission as? Type
        ?: throw RequestException.ParamsBad("Provided permission type '${request.type}' does not match the required one.")

    return cast
}