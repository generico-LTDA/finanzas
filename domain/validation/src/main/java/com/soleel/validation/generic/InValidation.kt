package com.soleel.validation.generic


interface InValidation<In, Out> {
    fun execute(input: In): Out
}