package com.example.templatekotlin1.common.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "RoomDb")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
    //This annotation marks the method provideDao as a provider of noteDoa.
    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.userDao()
}