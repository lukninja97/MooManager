package com.lukninja.moomanager

class Greeting {
    fun greeting(): String {
        return "Hello, ${com.lukninja.moomanager.Platform().platform}!"
    }
}