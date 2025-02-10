package com.tw.datamigrator

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class sourceDBHealth(@Qualifier("sourceDataSource")private val dataSource: DataSource): HealthIndicator{
    override fun health(): Health {
        return try {
            dataSource.connection.use { connection ->
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
class targetDBHealth(@Qualifier("targetDataSource") private val dataSource: DataSource): HealthIndicator{
    override fun health(): Health {
        return try {
            dataSource.connection.use { connection ->
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