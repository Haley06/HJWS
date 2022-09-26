package change.aaa.rtmpdemo.xfct

import android.content.res.Configuration
import android.graphics.Matrix
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView

@Suppress("DEPRECATION")
class UseCamera1(
    private val service: DetectorService,
    private val mSurfaceViewFW: SurfaceView
) {


    private var mCamera: Camera? = null
    var mCameraInfo: Camera.CameraInfo? = null
    private var mCameraInit = 0
    private var mSurfaceview: SurfaceView? = null
    private var mOverlap: SurfaceView? = null
    private var mSurfaceHolder: SurfaceHolder? = null

    var mPreviewCallback: Camera.PreviewCallback? = null
    var matrix = Matrix()
    val PREVIEW_WIDTH = 640
    val PREVIEW_HEIGHT = 480

    var CameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT

    fun create() {
        mSurfaceHolder = mSurfaceViewFW.holder
        mSurfaceview = mSurfaceViewFW
//        mSurfaceview!!.setOnClickListener {
//            CameraFacing = if (CameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                Camera.CameraInfo.CAMERA_FACING_BACK
//            } else {
//                Camera.CameraInfo.CAMERA_FACING_FRONT
//            }
//            openCamera(CameraFacing)
//        }
        Log.e("anan", "create: ${mSurfaceViewFW == null}")
        mSurfaceViewFW.holder!!.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int,
                width: Int, height: Int
            ) {
                matrix.setScale(width / PREVIEW_HEIGHT.toFloat(), height / PREVIEW_WIDTH.toFloat())
                initCamera()
                Log.e("anan", "surfaceChanged: ")
            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                mCamera = null
                openCamera(CameraFacing)
                Log.e("anan", "surfaceCreated: ")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.e("anan", "surfaceDestroyed: ")
                if (null != mCamera) {
                    mCamera!!.setPreviewCallback(null)
                    mCamera!!.stopPreview()
                    mCamera!!.release()
                    mCamera = null
                }
                mCameraInit = 0
            }
        })

    }

    fun release() {

    }

    fun start() {
        if (mCameraInit == 1 && mCamera == null) {
            openCamera(CameraFacing)
        }
    }

    fun stop() {
        mCamera?.let {
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.release()
            mCamera = null
        }
    }

    private fun openCamera(CameraFacing: Int) {
        if (null != mCamera) {
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.release()
            mCamera = null
        }
        val info = Camera.CameraInfo()
        for (i in 0 until Camera.getNumberOfCameras()) {
            Camera.getCameraInfo(i, info)
            if (info.facing == CameraFacing) {
                try {
                    mCamera = Camera.open(i)
                    mCameraInfo = info
                    mCamera!!.setPreviewCallback(mPreviewCallback)
                } catch (e: RuntimeException) {
                    e.printStackTrace()
                    mCamera = null
                    continue
                }
                break
            }
        }
        try {
            mCamera!!.setPreviewDisplay(mSurfaceHolder)
            initCamera()
        } catch (ex: Exception) {
            mCamera?.let {
                it.release()
                mCamera = null
            }
        }
    }

    private fun initCamera() {
        mCameraInit = 1
        if (null != mCamera) {
            try {
                val parameters = mCamera!!.parameters
                val flashModes = parameters.supportedFlashModes
                if (flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                    parameters.flashMode = Camera.Parameters.FLASH_MODE_OFF
                }
                val previewSizes = mCamera!!.parameters
                    .supportedPreviewSizes
                val pictureSizes = mCamera!!.parameters
                    .supportedPictureSizes
                for (i in previewSizes.indices) {
                    val psize = previewSizes[i]
                }
                parameters.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT)
                var fs: Camera.Size? = null
                for (i in pictureSizes.indices) {
                    val psize = pictureSizes[i]
                    if (fs == null && psize.width >= 1280) fs = psize
                }
                parameters.setPictureSize(fs!!.width, fs.height)
                if (service.resources
                        .configuration.orientation != Configuration.ORIENTATION_LANDSCAPE
                ) {
                    parameters["orientation"] = "portrait"
                    parameters["rotation"] = 90
                    val orientation =
                        if (CameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) 360 - mCameraInfo!!.orientation else mCameraInfo!!.orientation
                    mCamera!!.setDisplayOrientation(orientation)
                } else {
                    parameters["orientation"] = "landscape"
                    mCamera!!.setDisplayOrientation(0)
                }
                if (CameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    if (parameters.supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                        parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
                    } else {
                        parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
                    }
                }
                mCamera!!.parameters = parameters
                mCamera!!.setPreviewCallback(mPreviewCallback)
                mCamera!!.startPreview()
                val csize = mCamera!!.parameters.previewSize
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setPreviewCallback(previewCallback: Camera.PreviewCallback?) {
//        Log.e("anan", "setPreviewCallback: setPreviewCallback ${mCamera == null}")
        mPreviewCallback = previewCallback
        if (mCamera != null) {
            mCamera!!.setPreviewCallback(previewCallback)
        }
    }
}