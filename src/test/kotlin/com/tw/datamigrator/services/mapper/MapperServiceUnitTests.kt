package com.tw.datamigrator.services.mapper

import com.tw.datamigrator.mappers.MapperService
import com.tw.datamigrator.models.schema.DTO.TransferSchema
import com.tw.datamigrator.models.schema.oracle.OracleSchema
import com.tw.datamigrator.models.schema.oracle.OracleSchemaRow
import com.tw.datamigrator.services.schema.OracleSchemaService
import com.tw.datamigrator.services.schema.SchemaServiceFactory
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(SpringExtension::class)
class MapperServiceUnitTests {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Nested
    @TestPropertySource(properties =
    ["datasource.source.type=postgres", "datasource.target.type=postgres"])
    inner class WhenCallingSchemaServiceFactory {

        @Mock
        private lateinit var schemaServiceFactory: SchemaServiceFactory
        @Mock
        private lateinit var oracleSchemaService: OracleSchemaService

        @Autowired
        private lateinit var mapperService: MapperService
        @Test
        fun shouldSuccessfullyMapFromOracleToGenericTransferSchema() {
            val expectedSchema = OracleSchema("adrc", "SAPCRM", listOf(OracleSchemaRow("ID", "NUMBER", "Y", "0")))
            Mockito.`when`(schemaServiceFactory.getSourceSchemaService())
                    .thenReturn(oracleSchemaService)
            val source = schemaServiceFactory.getSourceSchemaService()
            Mockito.`when`(source.getTableSchema("adrc", "SAPCRM"))
                    .thenReturn(expectedSchema)
            val result = mapperService.mapSchema(source.getTableSchema("adrc", "SAPCRM"))
            assertEquals(result::class.simpleName, TransferSchema::class.simpleName)
        }

    }


}