package com.tw.datamigrator.models.oracle

data class OracleSchemaRow(
    val columnName: String,
    val dataType: String,
    val nullable: String,
    val dataDefault: String
)