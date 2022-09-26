package change.aaa.rtmpdemo.util

import android.view.View

fun View.invisibleIf(condition: Boolean) {
    visibility = if (condition) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}
fun View.goneIf(condition: Boolean) {
    visibility = if (condition) {
        View.GONE
    } else {
        View.VISIBLE
    }
}