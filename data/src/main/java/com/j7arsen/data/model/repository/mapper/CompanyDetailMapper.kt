package com.j7arsen.data.model.repository.mapper

import com.j7arsen.data.model.mapper.Mapper
import com.j7arsen.data.model.net.CompanyDetailEntity
import com.j7arsen.domain.model.CompanyDetailModel

class CompanyDetailMapper : Mapper<CompanyDetailEntity, CompanyDetailModel> {

    override fun map(origin: CompanyDetailEntity) = CompanyDetailModel(
        id = origin.id,
        name = origin.name,
        img = origin.img,
        description = origin.description,
        lat = origin.lat,
        lon = origin.lon,
        www = origin.www,
        phone = origin.phone
    )
}