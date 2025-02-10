package com.tw.datamigrator

import com.tw.datamigrator.data.DatabaseProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DatabaseProperties::class)
class DataMigratorApplication

fun main(args: Array<String>) {
    runApplication<DataMigratorApplication>(*args)
}
