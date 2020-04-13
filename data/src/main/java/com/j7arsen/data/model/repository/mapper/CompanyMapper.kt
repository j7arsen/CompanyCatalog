package com.j7arsen.data.model.repository.mapper

import com.j7arsen.data.model.mapper.Mapper
import com.j7arsen.data.model.net.CompanyEntity
import com.j7arsen.domain.model.CompanyModel

class CompanyMapper : Mapper<CompanyEntity, CompanyModel> {

    override fun map(origin: CompanyEntity) =
        CompanyModel(id = origin.id, name = origin.name, img = origin.img)
}