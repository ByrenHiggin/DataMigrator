package com.tw.datamigrator.mappers

import com.tw.datamigrator.models.DTO.TransferSchema
import com.tw.datamigrator.services.Schema.SchemaData

interface IMapper {
    fun map(schemeData: SchemaData): TransferSchema
}