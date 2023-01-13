package com.peterchege.pinstagram.core.core_room.database

import android.app.Application
import androidx.room.Room
import com.peterchege.pinstagram.core.core_common.Constants

class PinstagramLocalDataSource (app:Application) {

    val db  = Room.databaseBuilder(
        app,
        PinstagramDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()


}