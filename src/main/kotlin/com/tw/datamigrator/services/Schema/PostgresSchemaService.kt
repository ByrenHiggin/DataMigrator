package com.tw.datamigrator.services.Schema

import com.tw.datamigrator.models.oracle.OracleSchema
import com.tw.datamigrator.models.postgres.PostgresSchema
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.util.*

class PostgresSchemaService(private val jdbcTemplate: JdbcTemplate) : SchemaServiceInterface{
    override fun getTableSchema(tableName: String, schema: String): PostgresSchema {
        val query = "SELECT COLUMN_NAME, DATA_TYPE, NULLABLE, DATA_DEFAULT FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = ? AND OWNER = ?"
        val result = jdbcTemplate.queryForList(query, tableName.uppercase(Locale.getDefault()), schema.uppercase(Locale.getDefault()))
        return PostgresSchema.fromMap(result)
    }
}