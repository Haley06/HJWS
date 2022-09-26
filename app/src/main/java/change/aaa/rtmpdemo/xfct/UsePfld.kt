package change.aaa.rtmpdemo.xfct

import android.graphics.*
import android.hardware.Camera
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.view.SurfaceView
import com.sample.tracking.PointCheck
import zeusees.tracking.Face
import zeusees.tracking.FaceTracking

@Suppress("DEPRECATION")
class UsePfld(
    private val service: DetectorService,
    private val useCamera1: UseCamera1,
    private val mOverlap: SurfaceView
) {

    private val MESSAGE_DRAW_POINTS = 100

    private var mMultiTrack106: FaceTracking? = null
    private var mListener: TrackCallBack? = null
    private lateinit var mHandlerThread: HandlerThread
    private lateinit var mHandler: Handler

    private lateinit var mNv21Data: ByteArray
    private lateinit var mTmpBuffer: ByteArray

    private var frameIndex = 0

    private lateinit var mPaint: Paint
    private val lockObj = Any()
    private var mIsPaused = false

    fun create() {

        mOverlap.setZOrderOnTop(true)
        mOverlap.holder.setFormat(PixelFormat.TRANSLUCENT)
        mNv21Data = ByteArray(useCamera1.PREVIEW_WIDTH * useCamera1.PREVIEW_HEIGHT * 2)
        mTmpBuffer = ByteArray(useCamera1.PREVIEW_WIDTH * useCamera1.PREVIEW_HEIGHT * 2)

        frameIndex = 0
        mPaint = Paint()
        mPaint.color = Color.rgb(57, 138, 243)
        val strokeWidth = Math.max(useCamera1.PREVIEW_HEIGHT / 240, 2)
        mPaint.strokeWidth = strokeWidth.toFloat()
        mPaint.style = Paint.Style.FILL
        mHandlerThread = HandlerThread("DrawFacePointsThread")
        mHandlerThread.start()
        mHandler = object : Handler(mHandlerThread.looper) {
            override fun handleMessage(msg: Message) {
                if (msg.what == MESSAGE_DRAW_POINTS) {
                    synchronized(lockObj) {
                        if (!mIsPaused) {
                            handleDrawPoints()
                        }
                    }
                }
            }
        }
        useCamera1.setPreviewCallback { data, _ ->
//            Log.e("anan", "create: setPreviewCallback",)
            synchronized(mNv21Data) { System.arraycopy(data, 0, mNv21Data, 0, data.size) }
            mHandler.removeMessages(MESSAGE_DRAW_POINTS)
            mHandler.sendEmptyMessage(MESSAGE_DRAW_POINTS)
        }


    }

    fun release() {

    }

    fun start() {
        mIsPaused = false

        if (mMultiTrack106 == null) {
            mMultiTrack106 =
                FaceTracking(Environment.getExternalStorageDirectory().path + "/FaceTracking/models")
        }
    }

    fun stop() {
        mHandler.removeMessages(MESSAGE_DRAW_POINTS)
        mIsPaused = true
//        synchronized(lockObj) {
//            if (mMultiTrack106 != null) {
//                mMultiTrack106 = null
//            }
//        }
    }

    private fun handleDrawPoints() {
        synchronized(mNv21Data) {
            System.arraycopy(
                mNv21Data,
                0,
                mTmpBuffer,
                0,
                mNv21Data.size
            )
        }
        val frontCamera = useCamera1.CameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT
        if (frameIndex == 0) {
            mMultiTrack106!!.FaceTrackingInit(
                mTmpBuffer,
                useCamera1.PREVIEW_HEIGHT,
                useCamera1.PREVIEW_WIDTH
            )
        } else {
            mMultiTrack106!!.Update(mTmpBuffer, useCamera1.PREVIEW_HEIGHT, useCamera1.PREVIEW_WIDTH)
        }
        frameIndex += 1
        val faceActions = mMultiTrack106!!.trackingInfo
        if (faceActions != null) {
//            Log.e("anan", "handleDrawPoints: pre onTrackdetectedFace")
            mListener?.onTrackdetectedFace(faceActions)
            if (!mOverlap.holder.surface.isValid) {
                return
            }
            val canvas = mOverlap.holder.lockCanvas() ?: return
            canvas.drawColor(0, PorterDuff.Mode.CLEAR)
            canvas.setMatrix(useCamera1.matrix)
            val rotate270 = useCamera1.mCameraInfo!!.orientation == 270
            for (r in faceActions) {
                val rect = Rect(
                    useCamera1.PREVIEW_HEIGHT - r.left,
                    r.top,
                    useCamera1.PREVIEW_HEIGHT - r.right,
                    r.bottom
                )
                val points = arrayOfNulls<PointF>(106)
                for (i in 0..105) {
                    if (PointCheck.check(i)) {
                        points[i] = PointF(
                            r.landmarks[i * 2].toFloat(),
                            r.landmarks[i * 2 + 1].toFloat()
                        )
//                        canvas.drawText(
//                            i.toString() + "",
//                            r.landmarks[i * 2].toFloat(),
//                            r.landmarks[i * 2 + 1].toFloat(), mPaint!!
//                        )
                    } else {
                        points[i] = PointF(0F, 0F)
                    }
                }
                val visibles = FloatArray(106)
                for (i in points.indices) {
                    visibles[i] = 1.0f
                    if (rotate270) {
                        points[i]!!.x = useCamera1.PREVIEW_HEIGHT - points[i]!!.x
                    }
                }
//                STUtils.drawFaceRect(
//                    canvas, rect, useCamera1.PREVIEW_HEIGHT,
//                    useCamera1.PREVIEW_WIDTH, frontCamera
//                )
//                STUtils.drawPoints(
//                    canvas, mPaint, points, visibles, useCamera1.PREVIEW_HEIGHT,
//                    useCamera1.PREVIEW_WIDTH, frontCamera
//                )
            }
            mOverlap.holder.unlockCanvasAndPost(canvas)
        }
    }


    fun registTrackCallback(callback: TrackCallBack?) {
        mListener = callback
    }

    interface TrackCallBack {
        fun onTrackdetectedFace(faces: List<Face>)
    }
}