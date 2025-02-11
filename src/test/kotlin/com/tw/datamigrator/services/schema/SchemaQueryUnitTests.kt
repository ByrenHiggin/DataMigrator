package com.tw.datamigrator.services.schema

import com.tw.datamigrator.config.DataSourceProperties
import com.tw.datamigrator.config.DatabaseInstance
import com.tw.datamigrator.config.DatabaseProperties
import com.tw.datamigrator.models.schema.oracle.OracleSchema
import com.tw.datamigrator.models.schema.oracle.OracleSchemaRow
import com.tw.datamigrator.models.schema.postgres.PostgresSchema
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.IllegalArgumentException

@SpringBootTest
@ExtendWith(SpringExtension::class)
class SchemaQueryUnitTests {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @MockitoBean
    @Qualifier("sourceDatabaseInstance")
    private lateinit var sourceDatabaseInstance: DatabaseInstance
    @MockitoBean
    @Qualifier("targetDatabaseInstance")
    private lateinit var targetDatabaseInstance: DatabaseInstance
    @MockitoBean
    private lateinit var databaseProperties: DatabaseProperties
    @Autowired
    private lateinit var schemaServiceFactory: SchemaServiceFactory

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sourceDatabaseInstance = Mockito.mock(DatabaseInstance::class.java)
        targetDatabaseInstance = Mockito.mock(DatabaseInstance::class.java)
        databaseProperties = Mockito.mock(DatabaseProperties::class.java)

        val sourceJdbcTemplate: JdbcTemplate = Mockito.mock(JdbcTemplate::class.java)
        val targetJdbcTemplate: JdbcTemplate = Mockito.mock(JdbcTemplate::class.java)
        val testSourceProperties = DataSourceProperties("oracle","jdbc:oracle:thin:@localhost:1521:xe", "username", "password", )
        val testTargetProperties = DataSourceProperties("postgres","jdbc:postgresql://localhost:5432/mydb", "username", "password")


        Mockito.`when`(sourceDatabaseInstance.jdbcConnection).thenReturn(sourceJdbcTemplate)
        Mockito.`when`(targetDatabaseInstance.jdbcConnection).thenReturn(targetJdbcTemplate)
        Mockito.`when`(sourceDatabaseInstance.properties).thenReturn(testSourceProperties)
        Mockito.`when`(targetDatabaseInstance.properties).thenReturn(testTargetProperties)
        Mockito.`when`(databaseProperties.source).thenReturn(testSourceProperties)
        Mockito.`when`(databaseProperties.target).thenReturn(testTargetProperties)

        schemaServiceFactory = SchemaServiceFactory(databaseProperties, sourceDatabaseInstance, targetDatabaseInstance)
    }

    @Nested
    inner class WhenCallingSchemaServiceFactory() {


        @Test
        fun shouldReturnCorrectDatabaseSourceType() {
            val expectedSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER"))
            Mockito.`when`(sourceDatabaseInstance.jdbcConnection.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(expectedSchema)
            val source = schemaServiceFactory.getSourceSchemaService()
            assertEquals(source::class.simpleName, OracleSchemaService::class.simpleName)
        }

        @Test
        fun shouldBeAbleToQuerySourceAndGetSchema() {
            val inputSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER", "NULLABLE" to "Y", "DATA_DEFAULT" to "0"))
            val expectedSchema = OracleSchema("adrc", "SAPCRM", listOf(OracleSchemaRow("ID", "NUMBER", "Y", "0")))
            Mockito.`when`(sourceDatabaseInstance.jdbcConnection.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(inputSchema)
            val source = schemaServiceFactory.getSourceSchemaService()
            val result = source.getTableSchema("adrc", "SAPCRM")
            assertEquals(expectedSchema, result)
        }

        @Test
        fun shouldBeAbleToQueryTargetAndGetSchema() {
            val inputSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER", "NULLABLE" to "Y", "DATA_DEFAULT" to "0"))
            val expectedSchema = PostgresSchema("adrc", "SAPCRM", listOf(OracleSchemaRow("ID", "NUMBER", "Y", "0")))
            Mockito.`when`(targetDatabaseInstance.jdbcConnection.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(inputSchema)
            val target = schemaServiceFactory.getTargetSchemaService()
            val result = target.getTableSchema("adrc", "SAPCRM")
            assertEquals(expectedSchema, result)
        }
    }

    @Nested
    inner class WhenCallingSchemaServiceFactoryWithBrokenConfiguration {
        @BeforeEach
        fun setUp() {
            val testSourceProperties = DataSourceProperties("broken","jdbc:oracle:thin:@localhost:1521:xe", "username", "password", )
            Mockito.`when`(sourceDatabaseInstance.properties).thenReturn(testSourceProperties)
            Mockito.`when`(databaseProperties.source).thenReturn(testSourceProperties)
        }
        @Test
        fun shouldReturnErrorWhenDatabaseSourceTypeIsIncorrect() {
            val expectedSchema = listOf(mapOf("COLUMN_NAME" to "ID", "DATA_TYPE" to "NUMBER", "NULLABLE" to "Y", "DATA_DEFAULT" to "0"))
            Mockito.`when`(sourceDatabaseInstance.jdbcConnection.queryForList(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(expectedSchema)
            assertThrows<IllegalArgumentException> {
                schemaServiceFactory.getSourceSchemaService()
            }
        }
    }



}