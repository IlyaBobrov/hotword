package com.asprog.hotword.navigation.game.data.repository

import com.asprog.hotword.interfaces.Repository

interface GameRepository : Repository {
    fun saveGame()
//    fun getLastGame()
}