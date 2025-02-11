package com.tw.datamigrator.services.Schema

import com.tw.datamigrator.models.SchemaData

interface SchemaServiceInterface {
    fun getTableSchema(tableName: String, schema: String) : SchemaData
}

