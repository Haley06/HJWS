package change.aaa.rtmpdemo.xfct

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import change.aaa.rtmpdemo.R
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class XfctActivity : AppCompatActivity() {
    private val showWindows: Button by lazy { findViewById<Button>(R.id.show_windows) }
    private val testBtn: Button by lazy { findViewById<Button>(R.id.test_btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xfct)
        requirePermissionsWithPermissionCheck()
        startService(Intent(this@XfctActivity, DetectorService::class.java))

        showWindows.setOnClickListener {
            LiveEventBus.get(DetectorService.CHANNEL_SET_WINDOWS_VISIBLE).post(true)
        }

        testBtn.setOnClickListener {
            LiveEventBus.get(DetectorService.CHANNEL_SET_WINDOWS_VISIBLE).post(false)

        }


        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show()
            startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                ), 0
            )
        }
    }

    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
    )
    fun requirePermissions() {

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}