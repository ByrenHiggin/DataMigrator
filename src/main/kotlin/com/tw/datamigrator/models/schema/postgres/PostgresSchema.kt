package com.tw.datamigrator.models.schema.postgres

import com.tw.datamigrator.models.schema.oracle.OracleSchemaRow
import com.tw.datamigrator.models.schema.SchemaData

data class PostgresSchema(
    val tableName: String,
    val schema: String,
    val rows: List<OracleSchemaRow>
): SchemaData {
    companion object {
        fun fromMap(tableName: String, schema: String, result: List<Map<String, Any>>): PostgresSchema {
            val rows = result.map {
                OracleSchemaRow(
                        it["COLUMN_NAME"] as String,
                        it["DATA_TYPE"] as String,
                        it["NULLABLE"] as String,
                        it["DATA_DEFAULT"] as String
                )
            }
            return PostgresSchema(
                    tableName,
                    schema,
                    rows
            )
        }
    }
}