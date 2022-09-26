package change.aaa.rtmpdemo.pfld

import android.util.Log
import change.aaa.rtmpdemo.util.FatiRequest
import change.aaa.rtmpdemo.util.RDExecutor
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import java.io.*
import java.net.Socket
import java.util.concurrent.ConcurrentHashMap

class SocketKeeper(
    private val listener: SocketListener
) {
    private var socket: Socket? = null
    var isConnected = false

    private var socketIn: BufferedReader? = null
    private var socketOut: PrintWriter? = null

    private val pktMap = ConcurrentHashMap<String, FatigueFeatureItemReq>()


    private val readerThread = Thread {
        var content: String?
        try {
            while (true) {
                if (socket != null && socket!!.isConnected && !socket!!.isInputShutdown) {
                    content = socketIn?.readLine()
                    if (content == null) continue
//                    Log.e("dydy", ": ${socketIn!!.readLine()}$content")
                    if ((content) != null) {
                        Log.v("dydy", "content $content")
                        val res = Gson().fromJson(
                            content.trim(),
                            FatigueFeatureItemRes::class.java
                        )
                        FatiRequest.send(res)
                        Log.v("dydy", "res_${res.toString()}")

                        LiveEventBus.get(PfldFragmentNewStyle.ALGORITHM_INFO).post(res)


                        /*val sendTime = pktMap.remove(res.uuid)?.timestamp
                        Log.v("dydy", sendTime.toString())


                        val latency = if (sendTime == null) {
                            -1
                        } else {
                            res.timestamp - sendTime
                        }
                        if (res.pred > 0.0F) {
                            RDExecutor.runOnMainThread { listener.onResult(true, latency) }
                        } else {
                            RDExecutor.runOnMainThread { listener.onResult(false, latency) }

                        }*/
                    }
                } else if (socket!!.isConnected) {
                    Log.e("anan", "isConnected: ", )
//                    connect()
                }
            }
        } catch (e: Exception) {
            isConnected = false
            RDExecutor.runOnMainThread { listener.onError() }
            e.printStackTrace()

        }
    }

    fun connect() {
        RDExecutor.runOnNetworkThread {
            try {
                socket = Socket(SERVER, PORT)
                isConnected = socket!!.isConnected
                socketIn = BufferedReader(InputStreamReader(socket!!.getInputStream(), "UTF-8"))
                socketOut = PrintWriter(
                    BufferedWriter(OutputStreamWriter(socket!!.getOutputStream())),
                    true
                )

                readerThread.start()
                RDExecutor.runOnMainThread { listener.onConnected() }
            } catch (e: Exception) {
                RDExecutor.runOnMainThread { listener.onError() }
                e.printStackTrace()
            }

        }
    }

    fun send(
        p70: Float,
        maxMouth: Int,
        mouthOpenWidthRate: Float,
        mouthOpenHeightRate: Float,
        eyebrowHeightRate: Float,
        eyebrowWidthRate: Float,
        eyeOpenRate: Float,
        leftTurnHead: Array<IntArray>
    ) {
        RDExecutor.runOnNetworkThread {
            try {
                if (socket != null && socket!!.isConnected && !socket!!.isOutputShutdown) {
                    val item = FatigueFeatureItemReq(
//                        UserModel.token,
//                        UUID.randomUUID().toString(),
                        p70,
                        maxMouth,
//                        System.currentTimeMillis(),
                        mouthOpenWidthRate,
                        mouthOpenHeightRate,
                        eyebrowHeightRate,
                        eyebrowWidthRate,
                        eyeOpenRate,
                        leftTurnHead
                    )
                    Log.d("anan", Gson().toJson(item))

//                    pktMap[item.uuid] = item
                    /*   if (socketOut!!.checkError()) {
                           connect()
                           Log.e("anan", "send: checkError")
                           return@runOnNetworkThread
                       }*/
                    socketOut!!.println(Gson().toJson(item))
                    socketOut!!.println("over")
                    socketOut?.flush()

                    connect()
//                    Log.e("anan", "send: checkError")
                    return@runOnNetworkThread
                }
            } catch (e: Exception) {
                RDExecutor.runOnMainThread { listener.onError() }
                e.printStackTrace()
            }

        }

    }


    companion object {
        //        const val SERVER = "192.168.18.188"
//        const val SERVER = "cn-xz-bgp.sakurafrp.com"
        const val SERVER = "47.94.90.140";
        const val PORT = 55533
    }

}

interface SocketListener {
    fun onConnected()
    fun onResult(isFatigue: Boolean, latency: Long) // latency毫秒
    fun onError() // 连接错误或发送错误 自动断开并尝试重连

}