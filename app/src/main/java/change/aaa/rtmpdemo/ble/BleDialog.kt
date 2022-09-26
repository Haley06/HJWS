package change.aaa.rtmpdemo.ble

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import change.aaa.rtmpdemo.R

class BleDialog : DialogFragment() {

    private lateinit var loading: ProgressBar
    private lateinit var tip: TextView
    private lateinit var scanCurrent: TextView
    private lateinit var scanCurrentMac: TextView
    private lateinit var dataContainer: LinearLayout


    private val mBleListener: (BleEvent) -> Unit = {
        when (it) {
            is BleScanEndEvent -> {
                if (!HcBleManager.isFound()) {
                    Toast.makeText(context, "扫描结束 未找到HC-08 请重试", Toast.LENGTH_SHORT).show()
                    changeState(BleDialogState.NOTFOUND)
                }
            }
            is BleScanUpdateEvent -> {
//                changeState(BleDialogState.SCANNING)
                scanCurrent.text = it.deviceModule.name
                scanCurrentMac.text = it.deviceModule.mac
            }
            is BleScanFoundHcEvent -> {
                Toast.makeText(context, "找到HC 正在链接", Toast.LENGTH_SHORT).show()
                changeState(BleDialogState.CONNECTING)
                Handler(Looper.getMainLooper()).postDelayed({

                    HcBleManager.connect()
                }, 500)
                HcBleManager.stopScan()
            }
            is BleConnectSuccessEvent -> {
//                Toast.makeText(context, "链接成功", Toast.LENGTH_SHORT).show()
                changeState(BleDialogState.SUCCESS)

                val intent = Intent(activity, HwActivity::class.java)
                startActivity(intent)
                dismiss()
            }
            is BleConnectErrorEvent -> {
                Toast.makeText(context, "链接失败 请重试", Toast.LENGTH_SHORT).show()
                changeState(BleDialogState.ERROR)
                dismiss()
            }
            is BleDataReadEvent -> {
                Toast.makeText(context, "接收到数据 ${it.data}", Toast.LENGTH_SHORT).show()
                dataContainer.addView(TextView(context).apply {
                    text = it.data
                })
            }
            is BleConnectPendingEvent -> {
                changeState(BleDialogState.CONNECTING)
            }
        }
    }
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_ble, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading = view.findViewById(R.id.loading)
        tip = view.findViewById(R.id.tip)
        scanCurrent = view.findViewById(R.id.scan_current)
        scanCurrentMac = view.findViewById(R.id.scan_current_mac)
        dataContainer = view.findViewById(R.id.data_container)
    }

    override fun onResume() {
        super.onResume()
        if (HcBleManager.isConnected()) {
            changeState(BleDialogState.SUCCESS)
        } else {
            changeState(BleDialogState.SCANNING)
            HcBleManager.startScan()

        }

        HcBleManager.registerEventListener(mBleListener)
    }

    override fun onPause() {
        super.onPause()
        HcBleManager.unregisterEventListener(mBleListener)
    }

    private fun changeState(state: BleDialogState) {
        when (state) {
            BleDialogState.SCANNING -> {
                loading.visibility = View.VISIBLE
                scanCurrent.visibility = View.VISIBLE
                scanCurrentMac.visibility = View.VISIBLE
                tip.text = "正在寻找HC-08"
            }
            BleDialogState.CONNECTING -> {
                loading.visibility = View.VISIBLE
                scanCurrent.visibility = View.GONE
                scanCurrentMac.visibility = View.GONE
                tip.text = "正在链接"

            }
            BleDialogState.SUCCESS -> {
                loading.visibility = View.GONE
                scanCurrent.visibility = View.GONE
                scanCurrentMac.visibility = View.GONE
                tip.text = "已链接 正在接收数据"

            }
            BleDialogState.ERROR -> {
                loading.visibility = View.GONE
                scanCurrent.visibility = View.GONE
                scanCurrentMac.visibility = View.GONE
                tip.text = "链接出错 请重试"

            }
            BleDialogState.NOTFOUND -> {
                loading.visibility = View.GONE
                scanCurrent.visibility = View.GONE
                scanCurrentMac.visibility = View.GONE
                tip.text = "扫描结束 未找到HC-08 请重试"
            }

        }

    }

    companion object {
        enum class BleDialogState {
            SCANNING, CONNECTING, SUCCESS, ERROR, NOTFOUND
        }
    }
}