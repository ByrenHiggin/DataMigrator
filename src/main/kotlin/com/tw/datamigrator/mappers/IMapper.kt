package com.tw.datamigrator.mappers

import com.tw.datamigrator.models.schema.DTO.TransferSchema
import com.tw.datamigrator.models.schema.SchemaData

interface IMapper {
    fun map(schemeData: SchemaData): TransferSchema
}