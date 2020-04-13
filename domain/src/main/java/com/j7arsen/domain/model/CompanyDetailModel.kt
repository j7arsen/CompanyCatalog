package com.j7arsen.domain.model

import java.io.Serializable

class CompanyDetailModel(val id: String,
                          val name: String,
                          val img: String,
                          val description: String?,
                          val lat: Double?,
                          val lon: Double?,
                          val www: String?,
                          val phone: String?) : Serializable