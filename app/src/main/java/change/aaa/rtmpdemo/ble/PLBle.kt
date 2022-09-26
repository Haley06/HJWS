package change.aaa.rtmpdemo.ble

import android.content.Context
import androidx.lifecycle.MutableLiveData
import change.aaa.rtmpdemo.Event

class PLBle {

    val scanEnd = MutableLiveData<Event<Unit>>()


    fun init(context: Context) {
        HcBleManager.init(context)
    }

    fun startScan() {
        HcBleManager.startScan()
    }

    companion object {
        private var INSTANCE: PLBle? = null
        fun getInstance(): PLBle{
            if (INSTANCE == null) {
                INSTANCE = PLBle()
            }
            return INSTANCE!!
        }
    }
}