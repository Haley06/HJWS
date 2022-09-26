package change.aaa.rtmpdemo.xfct

import android.util.Log
import change.aaa.rtmpdemo.pfld.FeatureCalc
import zeusees.tracking.Face
import java.lang.StringBuilder

class UseFeatureCompute(
    private val service: DetectorService,
    private val usePfld: UsePfld,
    private val onFeatureAvailable: (P70: Float, maxMouth: Int, mouthOpenWidthRate: Float, mouthOpenHeightRate: Float, eyebrowHeightRate: Float, eyebrowWidthRate: Float, eyeOpenRate: Float, leftTurnHead: Array<IntArray>) -> Unit
) {

    private val featureCalc =
        FeatureCalc { P70, maxMouth, mouthOpenWidthRate, mouthOpenHeightRate, eyebrowHeightRate, eyebrowWidthRate, eyeOpenRate, leftTurnHead ->
            val builder = StringBuilder()
            builder.append(P70)
            builder.append("\n")
            builder.append(maxMouth.toFloat() / 20F * 30F)
            val statusText = builder.toString()
            Log.v("dydy", "statusText_$statusText")
            onFeatureAvailable.invoke(
                P70,
                maxMouth,
                mouthOpenWidthRate,
                mouthOpenHeightRate,
                eyebrowHeightRate,
                eyebrowWidthRate,
                eyeOpenRate,
                leftTurnHead
            )

//        ThreadPoolExecutor


//        socketKeeper.send(P70, maxMouth)
        }

    fun create() {
//        Log.e("anan", "create: UseFeatureCompute" )
        usePfld.registTrackCallback(object : UsePfld.TrackCallBack {
            override fun onTrackdetectedFace(faces: List<Face>) {
                if (faces.isEmpty()) return
//                Log.e("anan", "create: onTrackdetectedFace" )
                featureCalc.onReceivePoints(
                    FeatureCalc.getLeftEye(faces[0].landmarks),
                    FeatureCalc.getRightEye(faces[0].landmarks),
                    FeatureCalc.getMouth(faces[0].landmarks),
                    faces[0].landmarks
                )
            }
        })
    }

    fun release() {
        usePfld.registTrackCallback(null)
    }

    fun start() {

    }

    fun stop() {
    }
}