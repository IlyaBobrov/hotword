package com.asprog.hotword.data.repository

import com.asprog.hotword.data.interfaces.Repository

interface GameRepository : Repository {
    fun saveGame()
//    fun getLastGame()
}