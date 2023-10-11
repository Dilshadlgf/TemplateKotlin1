package com.example.templatekotlin1.common.baseModel

import androidx.room.Entity

data class Pagination(val pageNum: Long,
                      val limit: Long,
                      val count: Long,
                      val nextPage: Long,
                      val prevPage: Long,
                      val totalPage: Long,)
