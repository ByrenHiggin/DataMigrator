package com.tw.datamigrator.mappers

import com.tw.datamigrator.models.DTO.TransferSchema
import com.tw.datamigrator.models.SchemaData

interface IMapper {
    fun map(schemeData: SchemaData): TransferSchema
}