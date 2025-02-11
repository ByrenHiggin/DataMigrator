package com.tw.datamigrator.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

import javax.sql.DataSource

@Configuration
class DataSourceConfig(private val databaseProperties: DatabaseProperties) {
    private val logger = KotlinLogging.logger {}
    @Bean
    @Qualifier("sourceDataSource")
    fun sourceDataSource(): DataSource {
       return createDataSource(databaseProperties.source)!!
    }

    @Bean
    @Qualifier("targetDataSource")
    fun targetDataSource(): DataSource {
        return createDataSource(databaseProperties.target)!!
    }

    @Bean
    @Qualifier("sourceJdbcTemplate")
    fun sourceJDBCTemplate(): JdbcTemplate {
        return JdbcTemplate(sourceDataSource())
    }
    @Bean
    @Qualifier("targetJdbcTemplate")
    fun targetJDBCTemplate(): JdbcTemplate {
        return JdbcTemplate(targetDataSource())
    }

    fun createDataSource(dataSourceProperties: DataSourceProperties): DataSource? {
        try {
        return DataSourceBuilder.create()
            .url(dataSourceProperties.url)
            .username(dataSourceProperties.user)
            .password(dataSourceProperties.password)
            .driverClassName(dataSourceProperties.driver)
            .build()
        } catch (ex: Exception) {
            logger.error {"Failed to create DataSource for ${dataSourceProperties.url}  $ex" }
        }
        return null
    }
}
