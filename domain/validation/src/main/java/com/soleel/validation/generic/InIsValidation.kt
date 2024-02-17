package com.soleel.validation.generic


interface InIsValidation<In, Is, Out> {
    fun execute(input: In, inputIs: Is): Out
}