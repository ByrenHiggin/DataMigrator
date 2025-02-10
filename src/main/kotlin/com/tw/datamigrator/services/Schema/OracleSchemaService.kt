package com.tw.datamigrator.services.Schema

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.util.*

class OracleSchemaService(private val jdbcTemplate: JdbcTemplate) : SchemaServiceInterface{
    override fun getTableSchema(tableName: String, schema: String): List<Map<String, Any>> {
        val query = "SELECT COLUMN_NAME, DATA_TYPE, NULLABLE, DATA_DEFAULT FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = ? AND OWNER = ?"
        return jdbcTemplate.queryForList(query, tableName.uppercase(Locale.getDefault()), schema.uppercase(Locale.getDefault()))
    }
}