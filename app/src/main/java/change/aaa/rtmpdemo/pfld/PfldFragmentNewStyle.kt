package change.aaa.rtmpdemo.pfld

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import change.aaa.rtmpdemo.R
import change.aaa.rtmpdemo.ble.*
import change.aaa.rtmpdemo.databinding.FragmentPfldNewStyleBinding
import com.jeremyliao.liveeventbus.LiveEventBus


class PfldFragmentNewStyle : Fragment() {

    private lateinit var root: ViewGroup
    private lateinit var binding: FragmentPfldNewStyleBinding
    private lateinit var mBleTip: TextView
    private lateinit var mFatiTip: TextView
    private lateinit var mHwTip: TextView
    private val mBleListener: (BleEvent) -> Unit = {
        when (it) {
            BleConnectSuccessEvent -> {
//                mBleTip.text = "蓝牙已连接"
//                mHwTip.text = "您当前的硬件：已连接"
            }
            BleConnectPendingEvent -> {
//                mBleTip.text = "正在连接蓝牙"
//                mHwTip.text = "您当前的硬件：正在连接"
            }
            BleConnectErrorEvent -> {
//                mBleTip.text = "请重试"
//                mHwTip.text = "您当前的硬件：已断开"
            }
        }
    }

    private val socketListener = object : SocketListener {
        override fun onConnected() {
            mFatiTip.text = "已连接,正在检测"
        }

        override fun onResult(isFatigue: Boolean, latency: Long) {
            mFatiTip.text = if (isFatigue) "您现在已经处于疲劳状态"
            else "已连接,正在检测"
        }

        override fun onError() {
            mFatiTip.text = "网络连接出错, 请重启APP"
        }
    }

    private val socketKeeper = SocketKeeper(socketListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pfld_new_style, container, false)
        binding = FragmentPfldNewStyleBinding.bind(view)
        root = view as ViewGroup
        initViews()
        HcBleManager.registerEventListener(mBleListener)
        binding = FragmentPfldNewStyleBinding.bind(root)

        binding.tips.text.text = "您未进入疲劳驾驶状态"
        binding.link.text.text = "硬件连接情况:未连接"
        binding.alcohol.text.text = "酒驾检测情况:未酒驾"
        binding.attention.text.text = "是否疑似走神：是"
        binding.feeling.text.text = "心情检测情况:愤怒"
        binding.bodyTemperature.text.text = "体温度数：36.5摄氏度"
        binding.heartbeat.text.text = "心率指数：80"

        binding.tips.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_message_24))
        binding.link.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_bluetooth_connected_24))
        binding.alcohol.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_alcohol))
        binding.attention.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_message_24))
        binding.feeling.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_feeling))
        binding.bodyTemperature.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_body_temperature))
        binding.heartbeat.icon.setImageDrawable(context?.getDrawable(R.drawable.ic_heartbeat))
        initSocket()

        LiveEventBus.get(ALGORITHM_INFO).observeForever {
            if (it is FatigueFeatureItemRes) {
                binding.tips.text.text = if (it.pred == 1.0f) {
                    vibrate()
                    "您已进入疲劳驾驶状态"
                } else "您未进入疲劳驾驶状态"
                binding.feeling.text.text = when (it.emotion_res) {
                    0 -> "心情检测情况:高兴"
                    1 -> "心情检测情况:伤心"
                    2 -> {
                        vibrate()
                        "心情检测情况:愤怒"
                    }
                    3 -> "心情检测情况:正常"
                    4 -> "心情检测情况:惊讶"
                    else -> {
                        "心情检测情况:高兴"
                    }
                }
                binding.attention.text.text = if (it.head_res == 1) {
                    vibrate()
                    "是否疑似走神：是"
                } else "是否疑似走神：否"

            }
        }
        LiveEventBus.get(BLE_INFO).observeForever {
            Log.e("anan", "onCreateView: it $it")
            if (it is ArrayList<*>) {
                Log.e("anan", "onCreateView: ble response ${it[0]}${it[1]}${it[2]} ")
                Log.e("anan", "onCreateView: alcohol ${(getNumberInString(it[2] as String))}")
                Log.e("anan", "onCreateView: heartbeat ${getNumberInString(it[1] as String)}")
                binding.heartbeat.text.text = "心率指数：${getNumberInString(it[1] as String) + 80}"
                try {
                    if (getNumberInString(it[2] as String) >= 1200) {
                        binding.alcohol.text.text = "酒驾检测情况:疑似酒驾"
                        vibrate()
                    } else
                        binding.alcohol.text.text = "酒驾检测情况:未酒驾"
                } catch (e: Exception) {
                    Log.e("anan", "onCreateView: ${e.stackTrace}")
                    Toast.makeText(context, "酒精数据异常", Toast.LENGTH_SHORT).show()
                }
            }
        }
        LiveEventBus.get(BLE_CONNECT).observeForever {
            if (it is Boolean) {
                if (it)
                    binding.link.text.text = "硬件连接情况:已连接"
            }
        }
        return view
    }

    private fun vibrate() {
        (requireContext().getSystemService(Service.VIBRATOR_SERVICE) as Vibrator).vibrate(
            1000
        )
    }

    private fun initSocket() {
        socketKeeper.connect()
    }

    private fun initViews() {
//        val overlapFragment = FaceOverlapFragment()
//        val tran = parentFragmentManager.beginTransaction()
//        val container = binding.pfldContainer
//        container.measure()
//        tran.replace(R.id.pfld_container, overlapFragment)
//        tran.commit()

        mBleTip = root.findViewById<TextView>(R.id.enter_ble)
//        mHwTip = root.findViewById(R.id.hw_tip)
        mFatiTip = root.findViewById(R.id.fati_tip)
        mBleTip.setOnClickListener {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            if (!adapter.isEnabled) {
                Toast.makeText(context, "请开启手机蓝牙", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (HcBleManager.isConnected()) {
                val intent = Intent(activity, HwActivity::class.java)
                startActivity(intent)
            } else {
                BleDialog().show(parentFragmentManager, null)
            }
        }

    }

    private fun getNumberInString(s: String): Int {
        var str = String()
        s.forEach {
            if (it in '0'..'9')
                str.apply {
                    str = this + it
                }
        }
        return str.toInt()
    }

    companion object {
        //        const val FATIGUE_DETAIL = "fatigue_detail"
        const val ALGORITHM_INFO = "algorithm_info"
        const val BLE_INFO = "ble_info"
        const val BLE_CONNECT = "ble_connect"
    }
}