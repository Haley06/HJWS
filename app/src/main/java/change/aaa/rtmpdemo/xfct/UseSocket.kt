package change.aaa.rtmpdemo.xfct

import change.aaa.rtmpdemo.pfld.SocketKeeper
import change.aaa.rtmpdemo.pfld.SocketListener

class UseSocket(
    private val service: DetectorService,
    private val listener: SocketListener
) {

    lateinit var keeper: SocketKeeper

    fun send(P70: Float, maxMouth: Int,mouthOpenWidthRate: Float, mouthOpenHeightRate: Float, eyebrowHeightRate: Float, eyebrowWidthRate: Float, eyeOpenRate: Float,leftTurnHead:Array<IntArray>) {
        keeper.send(P70, maxMouth, mouthOpenWidthRate, mouthOpenHeightRate, eyebrowHeightRate, eyebrowWidthRate, eyeOpenRate,leftTurnHead)
    }

    fun create() {
        keeper = SocketKeeper(listener)

    }

    fun release() {


    }

    fun start() {
        keeper.connect()
    }

    fun stop() {
    }
}