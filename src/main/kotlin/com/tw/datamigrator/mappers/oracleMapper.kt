package com.tw.datamigrator.mappers

import com.tw.datamigrator.models.DTO.TransferSchema
import com.tw.datamigrator.models.DTO.TransferSchemaRow
import com.tw.datamigrator.models.oracle.OracleSchema
import com.tw.datamigrator.services.Schema.SchemaData

class oracleMapper: IMapper {
    override fun map(schemeData: SchemaData): TransferSchema {
        val schema = schemeData as OracleSchema
        return TransferSchema(
                schema.tableName,
                schema.schema,
                schema.rows.map {
                    TransferSchemaRow(
                            it.columnName,
                            it.dataType,
                            it.nullable,
                            it.dataDefault
                    )
                }
        )
    }

}