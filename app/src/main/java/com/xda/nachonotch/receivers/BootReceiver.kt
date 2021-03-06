package com.xda.nachonotch.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.xda.nachonotch.services.BackgroundHandler
import com.xda.nachonotch.util.launchOverlaySettings
import com.xda.nachonotch.util.prefManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            if (context.prefManager.isEnabled) {
                if (Settings.canDrawOverlays(context)) {
                    val service = Intent(context, BackgroundHandler::class.java)
                    ContextCompat.startForegroundService(context, service)
                } else {
                    context.launchOverlaySettings()
                }
            }
        }
    }
}
