package com.soleel.common.argument

fun <T, R> ((T) -> R).withArgument(argument: T): () -> R {
    return { this(argument) }
}