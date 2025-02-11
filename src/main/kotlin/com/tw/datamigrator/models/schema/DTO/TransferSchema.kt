package com.tw.datamigrator.models.schema.DTO

data class TransferSchema (
    val tableName: String,
    val schema: String,
    val rows: List<TransferSchemaRow>
)