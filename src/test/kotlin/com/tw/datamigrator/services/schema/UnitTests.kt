package com.tw.datamigrator.services.schema

import com.tw.datamigrator.services.Schema.OracleSchemaService
import com.tw.datamigrator.services.Schema.SchemaServiceFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
class UnitTests {
    companion object {
        @DynamicPropertySource
        @JvmStatic
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("database.source.type") { "postgres" }
            registry.add("database.target.type") { "oracle" }
        }
    }
    @Nested
    inner class WhenCallingSchemaServiceFactory {
        @MockitoBean
        @Qualifier("sourceJdbcTemplate")
        private lateinit var sourceJdbcTemplate: JdbcTemplate
        @MockitoBean
        @Qualifier("targetJdbcTemplate")
        private lateinit var targetJdbcTemplate: JdbcTemplate
        @Autowired
        private lateinit var schemaServiceFactory: SchemaServiceFactory

        @Test
        fun ShouldReturnCorrectDatabaseSourceType() {
            val expectedSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER"))
            Mockito.`when`(sourceJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(expectedSchema)
            val source = schemaServiceFactory.getSourceSchemaService();
            assertEquals(source::class.simpleName, OracleSchemaService::class.simpleName)
        }

        @Test
        fun ShouldReturnErrorWhenDatabaseSourceTypeIsIncorrect() {
            val expectedSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER"))
            Mockito.`when`(sourceJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(expectedSchema)
            val source = schemaServiceFactory.getSourceSchemaService();
            assertEquals(source::class.simpleName, OracleSchemaService::class.simpleName)
        }
        @Test
        fun ShouldBeAbleToQuerySourceAndGetSchema() {
            val expectedSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER"))
            Mockito.`when`(sourceJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(expectedSchema)
            val source = schemaServiceFactory.getSourceSchemaService();
            val result = source.getTableSchema("adrc", "SAPCRM");
            assertEquals(expectedSchema, result)
        }
        @Test
        fun ShouldBeAbleToQueryTargetAndGetSchema() {
            val expectedSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER"))
            Mockito.`when`(sourceJdbcTemplate.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(expectedSchema)
            val target = schemaServiceFactory.getTargetSchemaService();
            val result = target.getTableSchema("adrc", "SAPCRM");
            assertEquals(expectedSchema, result)
        }
    }


}