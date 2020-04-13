package com.j7arsen.data.model.net

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CompanyDetailEntity(val id: String,
                          val name: String,
                          val img: String,
                          val description: String?,
                          val lat: Double?,
                          val lon: Double?,
                          val www: String?,
                          val phone: String?)