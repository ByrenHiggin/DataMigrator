package com.tw.datamigrator.services.schema

import com.tw.datamigrator.config.DatabaseInstance
import com.tw.datamigrator.config.DatabaseProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class SchemaServiceFactory(
        private val databaseProperties: DatabaseProperties,
        @Qualifier("sourceDatabaseInstance") private val sourceDatabaseInstance: DatabaseInstance,
        @Qualifier("targetDatabaseInstance") private val targetDatabaseInstance: DatabaseInstance
) {
    private val logger = LoggerFactory.getLogger(SchemaServiceFactory::class.java)

    fun getSourceSchemaService(): SchemaServiceInterface {
        return getSchemaType(sourceDatabaseInstance)
    }

    fun getTargetSchemaService(): SchemaServiceInterface {
        return getSchemaType(targetDatabaseInstance)
    }
    fun getSchemaType(dbInstance: DatabaseInstance): SchemaServiceInterface {
        logger.debug("getting database type: $dbInstance")

        return when(dbInstance.properties.type) {
            "oracle" -> OracleSchemaService(dbInstance.jdbcConnection)
            "postgres" -> PostgresSchemaService(dbInstance.jdbcConnection)
            else -> throw IllegalArgumentException("Unsupported source database type: ${databaseProperties.source.type}")
        }
    }
}