package com.example.templatekotlin1.common.baseModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val dbId:Int,
    val uniqueId: String,
    val userName: String,
    val name: String,
    val lastName: String,
    val fatherName: String,
    val spouseName: String,
    val officialEmail: String,
    val joiningDate: String,
    val profile: String,
    val organisationId: String,
    val password: String,
    val pass: String,
    val role: String,
    val type: String,
    val status: String,
    val employeeId: String,
    val isAreaAssign: String,
    val mobile: String,
    val email: String,
    val dob: String,
    val gender: String,
    val lineManager: String,
    val profileImg: String,
    val licenseType: String
)
