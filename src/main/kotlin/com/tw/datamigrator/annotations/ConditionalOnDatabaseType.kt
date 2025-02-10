package com.tw.datamigrator.annotations

import org.springframework.context.annotation.Conditional

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Conditional(OnDatabaseTypeCondition::class)
annotation class ConditionalOnDatabaseType(val databaseType: String)