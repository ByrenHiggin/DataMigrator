package com.tw.datamigrator.services.Schema

import com.tw.datamigrator.controllers.SourceSchemaController
import com.tw.datamigrator.data.DatabaseProperties
import com.tw.datamigrator.enums.DatabaseTypeEnums
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class SchemaServiceFactory(
        private val databaseProperties: DatabaseProperties,
        @Qualifier("sourceJdbcTemplate") private val sourceJDBCTemplate: JdbcTemplate,
        @Qualifier("targetJdbcTemplate") private val targetJdbcTemplate: JdbcTemplate
) {
    private val logger = LoggerFactory.getLogger(SchemaServiceFactory::class.java)

    fun getSourceSchemaService(): SchemaServiceInterface {
        val sourceSchema = getSchemaType(databaseProperties.source.type.lowercase(), sourceJDBCTemplate)
        return sourceSchema
    }

    fun getTargetSchemaService(): SchemaServiceInterface {
        return getSchemaType(databaseProperties.target.type.lowercase(), targetJdbcTemplate)
    }
    fun getSchemaType(databaseType: String, jdbc: JdbcTemplate): SchemaServiceInterface {
        logger.debug("getting database type: $databaseType")

        return when(databaseType) {
            "oracle" -> OracleSchemaService(jdbc)
            "postgres" -> PostgresSchemaService(jdbc)
            else -> throw IllegalArgumentException("Unsupported source database type: ${databaseProperties.source.type}")
        }
    }
}