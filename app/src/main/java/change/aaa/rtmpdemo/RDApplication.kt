package change.aaa.rtmpdemo

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory
import com.baidu.navisdk.adapter.IBaiduNaviManager.INaviInitListener
import com.chibatching.kotpref.Kotpref
import java.io.File
import java.io.FileOutputStream


class RDApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initModelFiles()
        Kotpref.init(this)

        SDKInitializer.setAgreePrivacy(this, true)
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.GCJ02);

        //初始化百度地图导航
        BaiduNaviManagerFactory.getBaiduNaviManager().init(
            this, Environment.getExternalStorageDirectory().absolutePath, "xcshz",
            object : INaviInitListener {
                override fun onAuthResult(p0: Int, p1: String?) {
                    Log.v("dydy", "onAuthResult")
                }

                override fun initStart() {
                    Log.v("dydy", "initStart")
                }

                override fun initSuccess() {
                    Log.v("dydy", "initSuccess")
                }

                override fun initFailed(p0: Int) {
                    Log.v("dydy", "initFailed")
                }

            }
        )
    }

    fun initModelFiles() {
        val assetPath = "FaceTracking"
        val sdcardPath = Environment.getExternalStorageDirectory()
            .toString() + File.separator + assetPath
        Log.e("anan", "initModelFiles: $sdcardPath")
        copyFilesFromAssets(this, assetPath, sdcardPath)
    }

    private fun copyFilesFromAssets(context: Context, oldPath: String, newPath: String) {
        try {
            val fileNames = context.assets.list(oldPath)
            if (fileNames!!.isNotEmpty()) {
                // directory
                val file = File(newPath)
                if (!file.mkdir()) {
                    Log.d("mkdir", "can't make folder")
                }
                for (fileName in fileNames) {
                    copyFilesFromAssets(
                        context, "$oldPath/$fileName",
                        "$newPath/$fileName"
                    )
                }
            } else {
                // file
                val `is` = context.assets.open(oldPath)
                val file = File(newPath)
                val fos = FileOutputStream(file)
                val buffer = ByteArray(1024)
                var byteCount: Int
                while (`is`.read(buffer).also { byteCount = it } != -1) {
                    fos.write(buffer, 0, byteCount)
                }
                fos.flush()
                `is`.close()
                fos.close()
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}