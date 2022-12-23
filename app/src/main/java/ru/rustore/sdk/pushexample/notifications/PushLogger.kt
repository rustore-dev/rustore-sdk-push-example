package ru.rustore.sdk.pushexample.notifications

import android.util.Log
import ru.rustore.sdk.pushclient.common.logger.Logger

class PushLogger(private val tag: String? = null) : Logger {
    override fun debug(message: String, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun error(message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun info(message: String, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    override fun verbose(message: String, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    override fun warn(message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun createLogger(tag: String): Logger {
        val newTag = if (this.tag != null) {
            "${this.tag}:$tag"
        } else {
            tag
        }
        return PushLogger(newTag)
    }
}