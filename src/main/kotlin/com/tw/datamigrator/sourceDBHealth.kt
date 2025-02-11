package com.tw.datamigrator

import com.tw.datamigrator.config.DatabaseInstance
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class sourceDBHealth(@Qualifier("sourceDatabaseInstance")private val sourceDataSource: DatabaseInstance): HealthIndicator{
    override fun health(): Health {
        return try {
            sourceDataSource.dataSource.connection.use { connection ->
                if (connection.isValid(1000)) {
                    Health.up().build()
                } else {
                    Health.down().build()
                }
            }
        } catch (e: Exception) {
            Health.down(e).build()
        }
    }

}
@Component
class targetDBHealth(@Qualifier("targetDatabaseInstance") private val targetDataSource: DatabaseInstance): HealthIndicator{
    override fun health(): Health {
        return try {
            targetDataSource.dataSource.connection.use { connection ->
                if (connection.isValid(1000)) {
                    Health.up().build()
                } else {
                    Health.down().build()
                }
            }
        } catch (e: Exception) {
            Health.down(e).build()
        }
    }

}