package com.tw.datamigrator.services.schema

import com.tw.datamigrator.models.schema.SchemaData

interface SchemaServiceInterface {
    fun getTableSchema(tableName: String, schema: String) : SchemaData
}

