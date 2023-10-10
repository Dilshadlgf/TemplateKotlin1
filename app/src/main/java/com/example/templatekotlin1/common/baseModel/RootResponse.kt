package com.example.templatekotlin1.common.baseModel

import com.google.gson.JsonElement

data class RootResponse(val response: Response)

data class Response (
    val statusCode: Long,
    val message: String,
    val data: Data
)
data class Data (
    val dashboard: JsonElement,
    val project: JsonElement,
    val fund: JsonElement,
    val data: JsonElement,
    val department: JsonElement,
    val role: JsonElement,
    val uri: JsonElement,
    val FileURIs: JsonElement,
    val funder: JsonElement,
    val bill: JsonElement,
    val task: JsonElement,
    val subtask: JsonElement,
    val user: JsonElement,
    val vendor: JsonElement,
    val userType: JsonElement,
    val pagination:Pagination
)
