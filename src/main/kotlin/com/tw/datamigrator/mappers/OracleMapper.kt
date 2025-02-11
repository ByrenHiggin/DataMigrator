package com.tw.datamigrator.mappers

import com.tw.datamigrator.models.schema.DTO.TransferSchema
import com.tw.datamigrator.models.schema.DTO.TransferSchemaRow
import com.tw.datamigrator.models.schema.oracle.OracleSchema
import com.tw.datamigrator.models.schema.SchemaData
import org.springframework.stereotype.Component

@Component
class OracleMapper: IMapper {
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