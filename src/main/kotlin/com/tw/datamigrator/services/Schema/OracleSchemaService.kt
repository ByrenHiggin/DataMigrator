package com.tw.datamigrator.services.Schema

import com.tw.datamigrator.models.oracle.OracleSchema
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*

class OracleSchemaService(private val jdbcTemplate: JdbcTemplate) : SchemaServiceInterface{
    override fun getTableSchema(tableName: String, schema: String): OracleSchema {
        val query = "SELECT COLUMN_NAME, DATA_TYPE, NULLABLE, DATA_DEFAULT FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = ? AND OWNER = ?"
        val result = jdbcTemplate.queryForList(query, tableName.uppercase(Locale.getDefault()), schema.uppercase(Locale.getDefault()))
        return OracleSchema.fromMap(tableName, schema, result)
    }
}

