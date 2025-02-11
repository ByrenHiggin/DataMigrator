package com.tw.datamigrator.controllers

import com.tw.datamigrator.mappers.MapperService
import com.tw.datamigrator.models.schema.DTO.TransferSchema
import com.tw.datamigrator.services.schema.SchemaServiceFactory
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schema")
class SourceSchemaController(
        private val schemaServiceFactory:SchemaServiceFactory,
        private val mapperService: MapperService) {

    private val logger = KotlinLogging.logger {}


    @GetMapping("/source/{tableName}")
    fun getSourceTableSchema(@PathVariable tableName: String): TransferSchema
    {
        logger.debug("Called /source/${tableName}")
        val schemaService = schemaServiceFactory.getSourceSchemaService()
        var schema = schemaService.getTableSchema(
            tableName,
            "SAPCRM"
        )
        return mapperService.mapSchema(schema)
    }
    @GetMapping("/target/{tableName}")
    fun getTargetTableSchema(@PathVariable tableName: String): TransferSchema {
        logger.debug("Called /target/${tableName}")
        val schemaService = schemaServiceFactory.getTargetSchemaService()
        val schema = schemaService.getTableSchema(
            tableName,
            "SAPCRM"
        )
        return mapperService.mapSchema(schema)
    }
}