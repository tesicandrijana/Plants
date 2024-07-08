package com.example.houseplants

import android.app.Application
import com.example.houseplants.data.AppContainer
import com.example.houseplants.data.DefaultAppContainer

class PlantsApplication : Application() {
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}