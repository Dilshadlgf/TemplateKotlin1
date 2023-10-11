package com.example.templatekotlin1.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.templatekotlin1.common.baseModel.User
import com.example.templatekotlin1.common.db.convertor.DateTimeConverter
import com.example.templatekotlin1.common.db.dao.UserDao


@TypeConverters(DateTimeConverter::class)
@Database(
    entities = [User::class],
    version = 12
)
abstract class AppDatabase :RoomDatabase() {
    abstract fun userDao(): UserDao
}