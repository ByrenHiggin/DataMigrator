package com.tw.datamigrator.controllers;

import com.tw.datamigrator.config.DataSourceConfig
import com.tw.datamigrator.services.Schema.SchemaServiceFactory;
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schema")
class SourceSchemaController(private val schemaServiceFactory: SchemaServiceFactory) {

    private val logger = KotlinLogging.logger {}


    @GetMapping("/source/{tableName}")
    fun getSourceTableSchema(@PathVariable tableName: String): List<Map<String, Any>>
    {
        logger.debug("Called /source/${tableName}")
        val schemaService = schemaServiceFactory.getSourceSchemaService()
        return schemaService.getTableSchema(
            tableName,
            "SAPCRM"
        )
    }
    @GetMapping("/target/{tableName}")
    fun getTargetTableSchema(@PathVariable tableName: String): List<Map<String, Any>> {
        logger.debug("Called /target/${tableName}")
        val schemaService = schemaServiceFactory.getTargetSchemaService()
        return schemaService.getTableSchema(
            tableName,
            "SAPCRM"
        )
    }
}