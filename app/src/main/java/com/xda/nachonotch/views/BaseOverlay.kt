package com.xda.nachonotch.views

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.view.WindowManager

abstract class BaseOverlay(
        context: Context,
        backgroundResource: Int = 0,
        backgroundColor: Int = Int.MIN_VALUE
) : View(context) {
    open val params = WindowManager.LayoutParams().apply {
        flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
                WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS

        type = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_PRIORITY_PHONE
        } else {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }

        format = PixelFormat.TRANSLUCENT
    }

    init {
        if (backgroundResource != 0) {
            setBackgroundResource(backgroundResource)
        } else if (backgroundColor != Int.MIN_VALUE) {
            setBackgroundColor(backgroundColor)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isForceDarkAllowed = false
        }
    }

    open fun update(wm: WindowManager) {
        try {
            wm.updateViewLayout(this, params)
        } catch (e: Exception) {}
    }

    open fun add(wm: WindowManager) {
        try {
            wm.addView(this, params)
        } catch (e: Exception) {}
    }

    open fun remove(wm: WindowManager) {
        try {
            wm.removeView(this)
        } catch (e: Exception) {}
    }

    open fun show(wm: WindowManager) {
        params.alpha = 1f
        update(wm)
    }

    open fun hide(wm: WindowManager) {
        params.alpha = 0f
        update(wm)
    }

    final override fun setBackgroundResource(resid: Int) {
        super.setBackgroundResource(resid)
    }

    final override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
    }
}