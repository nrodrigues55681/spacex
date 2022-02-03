package com.mindera.rocketscience.utils

import java.io.IOException

sealed class ErrorHandling: IOException() {
    object NetworkUnavailableException: ErrorHandling()
}