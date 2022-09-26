package change.aaa.rtmpdemo.ble

import android.content.Context
import android.util.Log
import change.aaa.rtmpdemo.pfld.PfldFragmentNewStyle
import change.aaa.rtmpdemo.pfld.PfldFragmentNewStyle.Companion.BLE_CONNECT
import com.hc.bluetoothlibrary.DeviceModule
import com.jeremyliao.liveeventbus.LiveEventBus
import java.util.*

sealed class BleEvent
data class BleScanUpdateEvent(val deviceModule: DeviceModule) : BleEvent()
object BleScanEndEvent : BleEvent()
object BleScanFoundHcEvent : BleEvent()
object BleConnectPendingEvent : BleEvent()
object BleConnectSuccessEvent : BleEvent()
object BleConnectErrorEvent : BleEvent()
data class BleDataReadEvent(val data: String) : BleEvent()


object HcBleManager {

    private var mDeviceModule: DeviceModule? = null
    private var mConnected = false
    private var mConnecting = false

    private val mMessageBuffer = ArrayDeque<String>(100)

    private val eventListeners = mutableListOf<(BleEvent) -> Unit>()

    fun init(context: Context) {
        HoldBluetooth.getInstance().initHoldBluetooth(context, object : HoldBluetooth.UpdateList {
            override fun update(isStart: Boolean, deviceModule: DeviceModule?) {
                if (deviceModule == null) {
//                    if (!isStart && !HcBleManager.isFound()) notifyEvent(BleScanEndEvent)
                    return
                }
                /**
                 * 此处进行蓝牙连接
                 */
                val name = deviceModule.name
//                Log.e("anan", "update: ${deviceModule.name}")
                notifyEvent(BleScanUpdateEvent(deviceModule))
                if (name.contains(DEVICE_NAME_PATTERN)) {
                    notifyEvent(BleScanFoundHcEvent)
                    LiveEventBus.get(BLE_CONNECT).post(true)
                    mDeviceModule = deviceModule
                }
            }

            override fun updateMessyCode(isStart: Boolean, deviceModule: DeviceModule?) {
                return update(isStart, deviceModule)
            }
        })

        HoldBluetooth.getInstance().setOnReadListener(object : HoldBluetooth.OnReadDataListener {
            override fun errorDisconnect(deviceModule: DeviceModule?) {
                mConnected = false
                mConnecting = false
                notifyEvent(BleConnectErrorEvent)
            }


            override fun readData(mac: String?, data: ByteArray?) {
                data?.let {
                    val str = decodeData(data)
                    notifyEvent(BleDataReadEvent(str))
                    mMessageBuffer.add(str)
                    var s = ArrayList<String>(3)
                    s =
                        str.split("\n") as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
                    LiveEventBus.get(PfldFragmentNewStyle.BLE_INFO).post(s)
                    Log.e("anan", "readData: $str")
                }

            }

            override fun connectSucceed() {
                mConnected = true
                mConnecting = false
                notifyEvent(BleConnectSuccessEvent)
            }

            override fun readLog(className: String?, data: String?, lv: String?) {}
            override fun readVelocity(velocity: Int) {}
            override fun endScan() {
                mConnected = false
                mConnecting = false
                notifyEvent(BleScanEndEvent)
            }

            override fun readNumber(number: Int) {}
            override fun reading(isStart: Boolean) {}

        })
    }

    fun connect() {
        HoldBluetooth.getInstance().connect(mDeviceModule)
        mConnecting = true
    }

    fun startScan() {
        HoldBluetooth.getInstance().scan(true)
    }

    fun stopScan() {
        HoldBluetooth.getInstance().stopScan()
    }

    fun isFound() = mDeviceModule != null
    fun isConnecting() = mConnecting
    fun isConnected() = mConnected

    fun notifyEvent(event: BleEvent) {
        eventListeners.forEach {
            it.invoke(event)
        }
    }

    fun registerEventListener(listener: (BleEvent) -> Unit) {
        eventListeners.add(listener)
        if (isConnected()) {
            listener(BleConnectSuccessEvent)
            return
        }
        if (isConnecting()) {
            listener(BleConnectPendingEvent)
        }

    }

    fun unregisterEventListener(listener: (BleEvent) -> Unit) {
        eventListeners.remove(listener)
    }

    private fun decodeData(data: ByteArray): String {
        return Analysis.getByteToString(data, "gbk", false)
    }

    const val DEVICE_NAME_PATTERN = "HJWS"
}