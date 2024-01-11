package com.taufiqarinta.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.taufiqarinta.githubuser.data.local.FavoriteUser
import com.taufiqarinta.githubuser.data.local.FavoriteUserDao
import com.taufiqarinta.githubuser.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDao : FavoriteUserDao?
    private var userDb : UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}