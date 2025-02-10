package com.tw.datamigrator.annotations

import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class OnDatabaseTypeCondition : Condition {
    override fun matches(context: ConditionContext, metadata: AnnotatedTypeMetadata): Boolean {
        val attributes = metadata.getAnnotationAttributes(ConditionalOnDatabaseType::class.java.name)
        val databaseType = attributes?.get("databaseType") as String?
        val configuredDatabaseType = context.environment.getProperty("datasource.source.type")
        return databaseType.equals(configuredDatabaseType, ignoreCase = true)
    }
}