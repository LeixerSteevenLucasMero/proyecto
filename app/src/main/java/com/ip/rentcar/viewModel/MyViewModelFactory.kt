package com.ip.rentcar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ip.rentcar.domain.UsersUseCase

class MyViewModelFactory(val usersUseCase: UsersUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UsersUseCase::class.java).newInstance(usersUseCase)
    }
}