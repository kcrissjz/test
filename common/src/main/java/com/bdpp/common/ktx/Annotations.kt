
@file:Suppress("unused")

package com.bdpp.common.ktx

import kotlin.RequiresOptIn.Level.WARNING
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.*

@RequiresOptIn(level = WARNING)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY)
annotation class ExperimentalApi
