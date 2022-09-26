package change.aaa.rtmpdemo.xfct

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.jeremyliao.liveeventbus.LiveEventBus

class DetectorService : Service() {



    private val pfManager = PfManager(this)


    override fun onCreate() {
        super.onCreate()
        pfManager.init()
        initBus()
    }

    private fun initBus() {
        Log.e("anan", "initBus: ", )
        pfManager.showWindow()
        LiveEventBus.get(CHANNEL_SET_WINDOWS_VISIBLE).observeForever { it ->
            (it as Boolean).let {
                if (it) {
                    Log.e("anan", "initBus: show")
                    pfManager.showWindow()
                } else {
                    pfManager.hideWindow()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("dydy", "started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun showFloatingWindow() {
        pfManager.showWindow()
    }

    companion object {
        const val CHANNEL_SET_WINDOWS_VISIBLE = "CHANNEL_SET_WINDOWS_VISIBLE"

    }
}