package com.alikn.logexplorer

import android.content.Context
import android.content.Intent

object Logexplorer {
    fun monitorLogs(context: Context) {
        context.startService(Intent(context, LogExplorerService::class.java))
    }
}