package change.aaa.rtmpdemo.util

import android.content.Context

fun Context.dip2px(dpValue: Float): Float {
    val scale = resources.displayMetrics.density;
    return dpValue * scale
}
