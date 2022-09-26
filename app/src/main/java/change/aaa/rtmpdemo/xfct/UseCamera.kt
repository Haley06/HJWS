package change.aaa.rtmpdemo.xfct

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.annotation.NonNull

class UseCamera(
    private val service: DetectorService,
    private val textureView: TextureView
) {


    private var cameraId = ""
    private lateinit var imageDimension: Size
    private var cameraDevice: CameraDevice? = null
    private lateinit var cameraCaptureSessions: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private var mBackgroundHandler: Handler? = null
    private var mBackgroundThread: HandlerThread? = null

    fun create() {
        textureView.surfaceTextureListener = textureListener
    }

    fun release() {

    }

    fun stop() {
        stopBackgroundThread()
        closeCamera()

    }

    fun start() {
        startBackgroundThread()
        openCamera()
    }

    private val textureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            openCamera()
        }
        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture,width: Int,height: Int) {}
        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {return false}
        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            //This is called when the camera is open
            Log.e("dydy", "onOpened")
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice!!.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice!!.close()
        }
    }


    @SuppressLint("MissingPermission")
    private fun openCamera() {
        val manager = service.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = manager.cameraIdList[0]
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            imageDimension = map!!.getOutputSizes(SurfaceTexture::class.java)[0]
            manager.openCamera(cameraId, stateCallback, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun closeCamera() {
        if (null != cameraDevice) {
            cameraDevice?.close()
            cameraDevice = null
        }
    }

    private fun createCameraPreview() {
        try {
            val texture = textureView.surfaceTexture!!
            texture.setDefaultBufferSize(imageDimension.width, imageDimension.height)
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(@NonNull cameraCaptureSession: CameraCaptureSession) {
                        //The camera is already closed
                        if (null == cameraDevice) {
                            return
                        }
                        // When the session is ready, we start displaying the preview.
                        cameraCaptureSessions = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(@NonNull cameraCaptureSession: CameraCaptureSession) {
                        Log.v("dydy", "onConfigureFailed")
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun updatePreview() {
        if (null == cameraDevice) {
            Log.e("dydy", "updatePreview error, return")
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions.setRepeatingRequest(
                captureRequestBuilder.build(), null, mBackgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("Camera Background")
        mBackgroundThread!!.start()
        mBackgroundHandler = Handler(mBackgroundThread!!.looper)

    }

    private fun stopBackgroundThread() {
        mBackgroundThread!!.quitSafely()
        try {
            mBackgroundThread!!.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}