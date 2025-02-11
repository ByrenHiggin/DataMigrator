package com.tw.datamigrator.services.schema

import com.tw.datamigrator.models.schema.postgres.PostgresSchema
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*

class PostgresSchemaService(private val jdbcTemplate: JdbcTemplate) : SchemaServiceInterface{
    override fun getTableSchema(tableName: String, schema: String): PostgresSchema {
        val query = "select column_name, data_type, character_maximum_length, column_default, is_nullable\n" +
                "from INFORMATION_SCHEMA.COLUMNS where table_name = ?;"
        val result = jdbcTemplate.queryForList(query, tableName.uppercase(Locale.getDefault()), schema.uppercase(Locale.getDefault()))
        return PostgresSchema.fromMap(tableName, schema, result)

    }
}