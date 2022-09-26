package change.aaa.rtmpdemo.pfld

import android.graphics.PointF
import android.util.Log
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

// maxMouth为次数 需换算 1秒30帧 1秒20次
class FeatureCalc(
    private val onCalc: (
        P70: Float, maxMouth: Int, mouthOpenWidthRate: Float,
        mouthOpenHeightRate: Float, eyebrowHeightRate: Float,
        eyebrowWidthRate: Float, eyeOpenRate: Float, leftTurnHead: Array<IntArray>
    ) -> Unit
) {

    private var countTime = 0 //量化总次数

    private var countCloseEye = 0 //量化内闭眼次数

    private var maxMouth = 0 //张嘴结算后量化内最大张嘴次数数
    private var countOpenMouth = 0 //当前张嘴过程张嘴次数数
    private var isMouthOpening = false //正在张嘴


    fun onReceivePoints(
        leftEye: List<PointF>,
        rightEye: List<PointF>,
        mouth: List<PointF>,
        landmarks: IntArray
    ) {
        countTime++
        val eyeClose = checkEyeClose(ratio(leftEye), ratio(rightEye))
        if (eyeClose) countCloseEye++

        val mouthOpen = checkMonthOpen(ratio(mouth))
        if (mouthOpen) {
            if (isMouthOpening) {
                countOpenMouth++
            } else {
                isMouthOpening = true
            }
            maxMouth = max(maxMouth, countOpenMouth)
        } else {
            isMouthOpening = false
            countOpenMouth = 0
        }
//        Log.e("anan", "onReceivePoints: $countTime $MAXTIME", )
        if (countTime >= MAXTIME) {
            if (!eyeClose && !mouthOpen) {
                val P70 = countCloseEye.toFloat() / countTime.toFloat()
                val curMaxMouth = maxMouth
                val mouthOpenWidthRate = getMouthWidth(landmarks)
                val mouthOpenHeightRate = getMouthHeight(landmarks)
                val eyebrowHeightRate = getEyebrowHeightRate(landmarks)
                val eyebrowWidthRate = getEyebrowWidthRate(landmarks)
                val eyeOpenRate = getEyeOpenRate(landmarks)
                val moveHeaderData = getMoveHeaderData(landmarks)

//                array.
                onCalc.invoke(
                    P70,
                    curMaxMouth,
                    mouthOpenWidthRate,
                    mouthOpenHeightRate,
                    eyebrowHeightRate,
                    eyebrowWidthRate,
                    eyeOpenRate,
                    moveHeaderData
                )

                countTime = 0
                countCloseEye = 0
                maxMouth = 0
            }
        }


    }

    //
//    fun getCurP70(): Float {
////        return
//    }
//
//    fun getCurMaxMouth(): Int {
//        return maxMouth
//    }
    data class Point constructor(
        var x: Float,
        var y: Float
    )

    companion object {
        const val MAXTIME = 40

        //        private const val thresholdEyes = 0.25F
        private const val thresholdEyes = 0.2F // 眼睛小瞪不到0.25*2，改到0.2*2
        private const val thresholdMouth = 0.40F

        private val indexLeftEye = listOf(94, 1, 53, 59, 67, 12)
        private val indexRightEye = listOf(27, 104, 85, 20, 47, 51)
        private val indexMouth = listOf(61, 40, 25, 42, 2, 63)


        private fun landmarks2Point(landmarks: IntArray): ArrayList<Point> {
            val list = ArrayList<Point>()
            for (i in (0 until landmarks.size / 2)) {
                list.add(Point(landmarks[i * 2].toFloat(), landmarks[i * 2 + 1].toFloat()))
            }
            return list
        }

        fun checkEyeClose(leftEyeRatio: Float, rightEyeRatio: Float): Boolean {
            return (leftEyeRatio + rightEyeRatio) < (thresholdEyes * 2)
        }

        fun checkMonthOpen(mouthRatio: Float): Boolean {
            return (mouthRatio > thresholdMouth)
        }

        fun ratio(contour: List<PointF>): Float {
            val a = euclidean(contour[1], contour[5])
            val b = euclidean(contour[2], contour[4])
            val c = euclidean(contour[0], contour[3])
            return (a + b) / (2.0F * c)
        }

        fun euclidean(p: PointF, q: PointF): Float {
            return sqrt((p.x - q.x).pow(2) + (p.y - q.y).pow(2))
        }

        fun getLeftEye(landmarks: IntArray): List<PointF> {
            val list = mutableListOf<PointF>()
            indexLeftEye.forEach {
                list.add(PointF(landmarks[it * 2].toFloat(), landmarks[it * 2 + 1].toFloat()))
            }
            return list
        }

        fun getRightEye(landmarks: IntArray): List<PointF> {
            val list = mutableListOf<PointF>()
            indexRightEye.forEach {
                list.add(PointF(landmarks[it * 2].toFloat(), landmarks[it * 2 + 1].toFloat()))
            }
            return list
        }

        fun getMouth(landmarks: IntArray): List<PointF> {
            val list = mutableListOf<PointF>()
            indexMouth.forEach {
                list.add(PointF(landmarks[it * 2].toFloat(), landmarks[it * 2 + 1].toFloat()))
            }
            return list
        }

        private fun getFaceWidth(landmarks: IntArray): Int {
            val list = landmarks2Point(landmarks)
            return abs(list[listContour[2]].x - list[listContour[3]].x).toInt()
        }

        private fun getFaceHeight(landmarks: IntArray): Int {
            val list = landmarks2Point(landmarks)
            return abs(list[listContour[0]].y - list[listContour[1]].y).toInt()
        }

        fun getMouthWidth(landmarks: IntArray): Float {
            val list = landmarks2Point(landmarks)
            return (abs(list[listCornerMouth[0]].x - list[listCornerMouth[1]].x) * 1.0f / getFaceWidth(
                landmarks
            ))
        }

        fun getMouthHeight(landmarks: IntArray): Float {
            val list = landmarks2Point(landmarks)
            return (abs(list[listLipsMid[0]].x - list[listLipsMid[1]].x) * 1.0f / getFaceHeight(
                landmarks
            ))
        }

        fun getEyebrowHeightRate(landmarks: IntArray): Float {
            val list = landmarks2Point(landmarks)
            var total = 0f
            listEyebrow.forEach {
                total += list[it].y - list[29].y
            }
            return total / 10.0f / getFaceHeight(landmarks)
        }

        fun getEyebrowWidthRate(landmarks: IntArray): Float {
            val list = landmarks2Point(landmarks)
            var total = 0f
            for (i in (0 until listEyebrow.size / 2)) {
                total += list[listEyebrow[i + 5]].x - list[listEyebrow[i]].x
            }
            return total / 5.0f / getFaceWidth(landmarks)
        }

        fun getEyeOpenRate(landmarks: IntArray): Float {
            val list = landmarks2Point(landmarks)
            var total = 0f
            var tem = -1
            listEyeOpen.forEach {
                total += list[it].y * tem * -1
                tem *= -1
            }
            return total / 4.0f / getFaceWidth(landmarks)
        }

        fun getMoveHeaderData(landmarks: IntArray): Array<IntArray> {
            val list = landmarks2Point(landmarks)
            val ans = Array(14) {
                return@Array IntArray(2)
            }
            listMoveHeader.withIndex().forEach {
                ans[it.index][0] = list[it.value].x.toInt()
                ans[it.index][1] = list[it.value].y.toInt()
            }

            return ans
        }
        /**
         * 以下数据是根据算法需求确定的, 是对Android中的106个点进行选取来获得算法需要的点
         */

        /**
         * listEyebrow 眉毛
         */
        private val listEyebrow = listOf(19, 84, 29, 79, 28, 24, 73, 70, 75, 74)

        /**
         * listCornerMouth 获取嘴巴张开宽度
         * listLipsMid 获取嘴巴张开高度
         * listEyeOpen 获取眼睛张开程度
         */
        private val listCornerMouth = listOf(45, 50)
        private val listLipsMid = listOf(36, 103)
        private val listEyeOpen = listOf(19, 84, 29, 79, 28, 24, 73, 70, 75, 74)

        /**
         * listMoveHeader 判断是否转头的数据
         */
        private val listMoveHeader =
            listOf(19, 28, 24, 74, 94, 59, 27, 20, 31, 93, 45, 50, 32, 0)

        /**
         * listContour 脸的轮廓 分别为上下左右四至点
         */
        private val listContour = listOf(29, 0, 8, 14)
    }


}