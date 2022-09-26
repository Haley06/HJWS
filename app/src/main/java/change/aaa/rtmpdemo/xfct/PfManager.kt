package change.aaa.rtmpdemo.xfct

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.graphics.PixelFormat
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.TextView
import change.aaa.rtmpdemo.R
import change.aaa.rtmpdemo.pfld.SocketListener
import change.aaa.rtmpdemo.util.dip2px

class PfManager(
    private val service: DetectorService
) {

    private lateinit var viewRoot: View
    private var windowManager: WindowManager? = null
    private var windowsLayoutParams: WindowManager.LayoutParams? = null

    private var cameraInited: Boolean = false
    private var cameraSurface: SurfaceView? = null
    private var cameraSurfaceHolder: SurfaceHolder? = null

    private val PREVIEW_WIDTH = 640
    private val PREVIEW_HEIGHT = 480

//    private lateinit var textureView: TextureView

    private lateinit var surfaceView: SurfaceView
    private lateinit var overlapView: SurfaceView
//    private lateinit var useCamera: UseCamera

    private lateinit var useCamera1: UseCamera1
    private lateinit var usePfld: UsePfld
    private lateinit var useFeatureCompute: UseFeatureCompute
    private lateinit var useSocket: UseSocket

    private lateinit var statusText: TextView

    private val onFeatureAvailable: (
        P70: Float, maxMouth: Int, mouthOpenWidthRate: Float,
        mouthOpenHeightRate: Float, eyebrowHeightRate: Float,
        eyebrowWidthRate: Float, eyeOpenRate: Float, leftTurnHead: Array<IntArray>
    ) -> Unit =
        { P70, maxMouth, mouthOpenWidthRate,
          mouthOpenHeightRate, eyebrowHeightRate,
          eyebrowWidthRate, eyeOpenRate, leftTurnHead
            ->
//            Log.e("anan", "${useSocket.keeper.isConnected}: ")
            if (useSocket.keeper.isConnected) {
                useSocket.send(
                    P70,
                    maxMouth,
                    mouthOpenWidthRate,
                    mouthOpenHeightRate,
                    eyebrowHeightRate,
                    eyebrowWidthRate,
                    eyeOpenRate,
                    leftTurnHead
                )
            }
        }


    fun init() {
        windowManager = service.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        windowsLayoutParams = buildLayoutParams()
        initFloatView(service.baseContext, R.layout.window_pfld)
    }


    fun release() {

    }

    fun showWindow() {
//        windowManager!!.addView(viewRoot, windowsLayoutParams)

        //添加布局到manager
        if (!isShow) {
            isShow = true
            mWindowManager!!.addView(mView, wmParams)
        }
        useCamera1.start()
        usePfld.start()
        useFeatureCompute.start()
        statusText.text = "正在连接服务器"
        useSocket.start()

    }

    fun hideWindow() {
        windowManager!!.removeView(viewRoot)
        isShow = false
        useCamera1.stop()
        usePfld.stop()
        useFeatureCompute.stop()
        statusText.text = "正在连接服务器"
        useSocket.stop()
    }


    private fun buildLayoutParams(): WindowManager.LayoutParams {
        val LAYOUT_FLAG: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        return WindowManager.LayoutParams(
            service.dip2px(200F).toInt(),
            service.dip2px(230F).toInt(),
            -280,
            -500,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
//        return WindowManager.LayoutParams().apply {
//            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//            } else {
//                WindowManager.LayoutParams.TYPE_PHONE
//            }
//
//            flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
//            format = PixelFormat.TRANSLUCENT
//            format = PixelFormat.RGBA_8888
//            width = service.dip2px(160F).toInt()
//            height = service.dip2px(160F).toInt()
////            gravity
//            x = 300
//            y = 300
//        }
    }

    private inner class PfSocketListener : SocketListener {
        override fun onConnected() {
            statusText.text = "Standing by"
        }

        override fun onResult(isFatigue: Boolean, latency: Long) {
            if (isFatigue) {
                statusText.text = "您已疲劳"
                val uri = Uri.parse("android.resource://link.dayang.rtmpdemo/" + R.raw.bell_short);

                val tone = RingtoneManager.getRingtone(service, uri)
                tone.play()
            } else {
                statusText.text = "Standing by"
            }

        }

        override fun onError() {
//            statusText.text = "网络连接出错, 请重启APP"
//            useSocket.keeper.connect()
        }

    }


    private var mWindowManager: WindowManager? = null
    private var wmParams: WindowManager.LayoutParams? = null
    private var mContext: Context? = null
    private var isShow: Boolean = false
    private lateinit var mView: View

    @SuppressLint("WrongConstant")
    fun initFloatView(context: Context, layoutId: Int) {
        mContext = context
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        wmParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            wmParams!!.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        wmParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //悬浮框居中
        wmParams!!.gravity = Gravity.CENTER
        wmParams!!.format = PixelFormat.RGBA_8888
        //悬浮窗的坐标，以左上角为中心，固定初始x,y值
        wmParams!!.x = 0
        wmParams!!.y = -600

        //悬浮窗宽高，可以固定大小
        wmParams!!.width = service.dip2px(200F).toInt()
        wmParams!!.height = service.dip2px(230F).toInt()
        //加载布局
        mView = LayoutInflater.from(context).inflate(layoutId, null)
        statusText = mView.findViewById(R.id.status_text)
//        windowManager!!.addView(viewRoot, windowsLayoutParams)

//        textureView = viewRoot.findViewById(R.id.window_surface_camera)
        surfaceView = mView.findViewById(R.id.window_surface_camera)
        overlapView = mView.findViewById(R.id.windows_surface_overlap)
//        surfaceView.visibility = View.INVISIBLE
//        overlapView.visibility = View.INVISIBLE

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.e("anan", "surfaceCreated: PfManager")
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.e("anan", "surfaceChanged: PfManager")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.e("anan", "surfaceDestroyed: PfManager")
            }

        })
//        useCamera = UseCamera(service, textureView)
//        useCamera.create()
        Log.e("anan", "init: ${surfaceView == null}")
        useCamera1 = UseCamera1(service, surfaceView)
        useCamera1.create()

        usePfld = UsePfld(service, useCamera1, overlapView)
        usePfld.create()
        Log.e("anan", "init: onFeatureAvailable")
        useFeatureCompute = UseFeatureCompute(service, usePfld, onFeatureAvailable)
        useFeatureCompute.create()

        useSocket = UseSocket(service, PfSocketListener())
        useSocket.create()

        mView.setOnTouchListener(object : View.OnTouchListener {
            var x = 0f
            var y = 0f
            var oddOffsetX = 0
            var oddOffsetY = 0
            override fun onTouch(view: View?, event: MotionEvent): Boolean {
                Log.e("anan", "onTouch: ${event.rawX}")
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = event.rawX
                        y = event.rawY
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val nowX = event.rawX
                        val nowY = event.rawY
                        val movedX = nowX - x
                        val movedY = nowY - y
                        x = nowX
                        y = nowY
                        windowsLayoutParams!!.x = (windowsLayoutParams!!.x + movedX).toInt()
                        windowsLayoutParams!!.y = (windowsLayoutParams!!.y + movedY).toInt()

                        // 更新悬浮窗控件布局
                        windowManager!!.updateViewLayout(view, windowsLayoutParams!!)
                    }
                    else -> {
                    }
                }
                return true
            }
        })
    }
}