package com.tw.datamigrator.config

import mu.KotlinLogging
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

class DatabaseInstance(dataSourceProperties: DataSourceProperties) {
    private val logger = KotlinLogging.logger {}

    var properties: DataSourceProperties = dataSourceProperties
    var jdbcConnection: JdbcTemplate
    var dataSource: DataSource

    init {
        this.dataSource = createDataSource(dataSourceProperties)!!
        this.jdbcConnection = JdbcTemplate(dataSource)
    }
    private fun createDataSource(dataSourceProperties: DataSourceProperties): DataSource? {
        return try {
            return DataSourceBuilder.create()
                    .url(dataSourceProperties.url)
                    .username(dataSourceProperties.user)
                    .password(dataSourceProperties.password)
                    .driverClassName(getDriverFromType(dataSourceProperties.type))
                    .build()
        } catch (ex: Exception) {
            logger.error {"Failed to create DataSource for ${dataSourceProperties.url}  $ex" }
            null
        }
    }

    private fun getDriverFromType(type: String): String {
        return when(type) {
            "oracle" -> "oracle.jdbc.OracleDriver"
            "postgres" -> "org.postgresql.Driver"
            else -> throw IllegalArgumentException("Unsupported database type: $type")
        }
    }
}