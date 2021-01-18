package com.bentley.githubuser.data.remote.mapper

import com.bentley.githubuser.data.remote.entity.UserInfoEntity
import com.bentley.githubuser.domain.User
import com.bentley.localweather.data.mapper.EntityMapper
import javax.inject.Inject

class UserMapper @Inject constructor() : EntityMapper<List<UserInfoEntity>, List<User>> {
    override fun mapFromEntity(entity: List<UserInfoEntity>): List<User> {
        return entity.map {
            with(it) {
                User(name, profileUrl)
            }
        }
    }
}
