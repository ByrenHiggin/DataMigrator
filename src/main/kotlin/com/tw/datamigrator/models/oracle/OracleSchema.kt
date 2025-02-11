package com.tw.datamigrator.models.oracle

import com.tw.datamigrator.models.SchemaData

data class OracleSchema(
    val tableName: String,
    val schema: String,
    val rows: List<OracleSchemaRow>
): SchemaData {
    companion object {
        fun fromMap(tableName: String, schema: String, result: List<Map<String, Any>>): OracleSchema {
            val rows = result.map {
                OracleSchemaRow(
                        it["COLUMN_NAME"] as String,
                        it["DATA_TYPE"] as String,
                        it["NULLABLE"] as String,
                        it["DATA_DEFAULT"] as String
                )
            }
            return OracleSchema(
                    tableName,
                    schema,
                    rows
            )
        }
    }
}