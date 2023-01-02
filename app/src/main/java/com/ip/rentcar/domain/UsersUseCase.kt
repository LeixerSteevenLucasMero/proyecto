package com.ip.rentcar.domain

import com.ip.rentcar.data.UsersDataSet
import com.ip.rentcar.ui.model.User

class UsersUseCase {

    private val usersDataSet = UsersDataSet()

    fun getListUsers():List<User>{
        return usersDataSet.createListUsers()
    }

    fun getStatusUser(): String{
        return usersDataSet.checkCurrentUser()
    }

    fun getCreateUser(user: User) : User{
        return usersDataSet.createAccount(user)
    }

    fun getLoginUser(email: String, password: String): String{
        return usersDataSet.signIn(email, password)
    }

}