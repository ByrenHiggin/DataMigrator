package com.tw.datamigrator.mappers

import com.tw.datamigrator.models.DTO.TransferSchema
import com.tw.datamigrator.models.SchemaData
import com.tw.datamigrator.models.oracle.OracleSchema
import com.tw.datamigrator.models.postgres.PostgresSchema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MapperService @Autowired constructor(private val mappers: List<IMapper>) {

    fun getMapper(schema: SchemaData): IMapper {
        when (schema::class) {
            OracleSchema::class -> return mappers.first { it is OracleMapper }
            PostgresSchema::class -> return mappers.first { it is PostgresMapper }
            else -> throw RuntimeException("No mapper found for schema $schema")
        }
    }

    fun mapSchema(schema: SchemaData): TransferSchema {
        val mapper = getMapper(schema)
        return mapper.map(schema) ?: throw RuntimeException("No mapper found for schema $schema")
    }
}