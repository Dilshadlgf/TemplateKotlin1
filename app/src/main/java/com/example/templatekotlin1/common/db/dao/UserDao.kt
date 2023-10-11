package com.example.templatekotlin1.common.db.dao

import androidx.room.*
import com.example.templatekotlin1.common.baseModel.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgent(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(user: List<User>)

    @Update
    fun updateAll(user: User)
    @Update
    fun updateAgent(user: User)

    @Delete
    fun deleteAgent(user: User)

    @Query("DELETE FROM User")
    fun clearTable()

    @Query("SELECT * FROM User ")
    fun getAllUser(): MutableList<User>


    @Query("SELECT * FROM User LIMIT 1")
    fun getAgent(): User

    @Query("SELECT COUNT(*) FROM User")
    fun getTotalCount():Int
}