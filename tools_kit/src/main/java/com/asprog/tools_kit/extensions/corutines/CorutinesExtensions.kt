package com.asprog.tools_kit.extensions.corutines

import kotlinx.coroutines.Job

fun Job?.cancelForce() {
    if (this != null) {
        if (this.isActive)
            this.cancel()
    }
}