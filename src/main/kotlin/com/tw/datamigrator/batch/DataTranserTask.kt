package com.tw.datamigrator.batch

import com.tw.datamigrator.config.DataSourceProperties

interface DataTransferTask {
    val connection: DataSourceProperties
    val startFrom: Int
    val endTo: Int
}