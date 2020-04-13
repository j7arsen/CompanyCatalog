package com.j7arsen.data.model.net

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyEntity(val id : String, val name : String, val img : String)