package com.cuongpq.basemvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cuongpq.basemvvm.data.local.dao.UserDao
import com.cuongpq.basemvvm.data.model.User
//******************************
//******************************
//***** Create by cuongpq  *****
//******************************
//******************************

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun fileDao() : FileDao
    abstract fun userDao() : UserDao
}