package change.aaa.rtmpdemo.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object RDExecutor {
    private val mainThread: Executor = MainThreadExecutor()
    private val networkThread: Executor = Executors.newFixedThreadPool(3)

    fun runOnMainThread(runnable: Runnable) {
        mainThread.execute(runnable)
    }

    fun runOnNetworkThread(runnable: Runnable) {
        networkThread.execute(runnable)
    }

    private class MainThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            handler.post(command)
        }
    }
}