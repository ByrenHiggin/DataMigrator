package com.tw.datamigrator.models.postgres

import com.tw.datamigrator.models.oracle.OracleSchemaRow
import com.tw.datamigrator.services.Schema.SchemaData

data class PostgresSchema(
    val tableName: String,
    val schema: String,
    val rows: List<OracleSchemaRow>
): SchemaData {
    companion object {
        fun fromMap(result: List<Map<String, Any>>): PostgresSchema {
            val rows = result.map {
                OracleSchemaRow(
                        it["COLUMN_NAME"] as String,
                        it["DATA_TYPE"] as String,
                        it["NULLABLE"] as String,
                        it["DATA_DEFAULT"] as String
                )
            }
            return PostgresSchema(
                    result[0]["TABLE_NAME"] as String,
                    result[0]["OWNER"] as String,
                    rows
            )
        }
    }
}