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

    @Bean
    @Qualifier("sourceDatabaseInstance")
    fun sourceDatabaseInstance(): DatabaseInstance {
        return DatabaseInstance(databaseProperties.source)
    }

    @Bean
    @Qualifier("targetDatabaseInstance")
    fun targetDatabaseInstance(): DatabaseInstance {
        return DatabaseInstance(databaseProperties.target)
    }


}
