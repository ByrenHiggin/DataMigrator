package com.tw.datamigrator.services.Schema

import org.springframework.jdbc.core.JdbcTemplate

interface SchemaServiceInterface {
    fun getTableSchema(tableName: String, schema: String) : SchemaData
}

