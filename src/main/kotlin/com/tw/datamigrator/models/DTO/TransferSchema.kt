package com.tw.datamigrator.models.DTO

import com.tw.datamigrator.models.oracle.OracleSchemaRow

data class TransferSchema (
    val tableName: String,
    val schema: String,
    val rows: List<TransferSchemaRow>
)