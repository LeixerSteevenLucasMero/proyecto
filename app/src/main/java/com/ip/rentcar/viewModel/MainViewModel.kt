package com.ip.rentcar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ip.rentcar.domain.UsersUseCase
import com.ip.rentcar.ui.model.User

class MyViewModel(val usersUseCase: UsersUseCase):ViewModel() {

    private val listData = MutableLiveData<List<User>>()

    private val userData = MutableLiveData<User>()
    private val readUserData = MutableLiveData<User>()

    private val nameUserStatus = MutableLiveData<String>()
    private val nameUserLogin = MutableLiveData<String>()

    //status user

    fun checkCurrentUser(){
        setStatusCreateData(usersUseCase.getStatusUser())
    }

    fun setStatusCreateData(name: String){
        nameUserStatus.value = name
    }

    fun getStatusUsersLiveData():LiveData<String>{
        return nameUserStatus
    }
    //----------

    //status user

    fun createUser(user: User){
        setCreateData(usersUseCase.getCreateUser(user))
    }

    fun setCreateData(user: User){
        userData.value = user
    }

    fun getCreateUsersLiveData():LiveData<User>{
        return userData
    }

    //----------

    //login user

    fun loginUser(email: String, password: String){
        setLoginData(usersUseCase.getLoginUser(email, password))
    }

    fun setLoginData(name: String){
        nameUserLogin.value = name
    }

    fun getLoginUsersLiveData():LiveData<String>{
        return nameUserLogin
    }
    //----------

    fun setListData(listaUsers:List<User>){
        listData.value = listaUsers
    }

    fun getListUsers(){
        setListData(usersUseCase.getListUsers())
    }

    fun getListUsersLiveData():LiveData<List<User>>{
        return listData
    }


}