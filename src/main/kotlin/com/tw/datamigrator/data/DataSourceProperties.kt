package com.tw.datamigrator.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "datasource")
data class DatabaseProperties(
    var source: DataSourceProperties = DataSourceProperties(),
    var target: DataSourceProperties = DataSourceProperties()
)

data class DataSourceProperties(
    var type: String = "",
    var url: String = "",
    var driver: String = "",
    var user: String = "",
    var password: String = ""
)

