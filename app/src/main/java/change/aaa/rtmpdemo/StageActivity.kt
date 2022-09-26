package change.aaa.rtmpdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jeremyliao.liveeventbus.LiveEventBus
import change.aaa.rtmpdemo.ble.HcBleManager
import change.aaa.rtmpdemo.data.UserModel
import change.aaa.rtmpdemo.navi.PoiSearchFragment
import change.aaa.rtmpdemo.pfld.PfldFragmentNewStyle
import change.aaa.rtmpdemo.webview.H5Url
import change.aaa.rtmpdemo.webview.WebViewFragment
import change.aaa.rtmpdemo.xfct.DetectorService
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class StageActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    private lateinit var currentFullOverlay: Fragment

    var isInFullScreenLogin = false

    private val profileFragment = WebViewFragment(H5Url.PROFILE)

    private val sheetFragment = WebViewFragment(H5Url.SHEET)

    private val pfldFragment = PfldFragmentNewStyle()

    var currentContentOverlay: Fragment = pfldFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        startService(Intent(this, DetectorService::class.java))

        setContentView(R.layout.activity_stage)

        requirePermissionsWithPermissionCheck()

        HcBleManager.init(this)

        initBottomBarEvents()

        //首页 OverLay
        addContentOverlay(pfldFragment)
        // TODO: login
        //if (!BuildConfig.DEBUG) {
            checkLogin()
        //}
    }

    private fun checkLogin() {
        if (UserModel.token == "") {
            isInFullScreenLogin = true
//            addFullOverlay(WebViewFragment(H5Url.LOGIN))
            addFullOverlay(WebViewFragment(H5Url.LOGIN))
        }
    }

    //个人信息与历史记录
    fun addContentOverlay(fragment: Fragment) {
        currentContentOverlay = fragment
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.content_overlay, fragment)
        transition.commit()
    }

    fun removeContentOverlay() {
        val transition = supportFragmentManager.beginTransaction()
        transition.remove(currentContentOverlay)
        transition.commit()

    }

    //全屏覆盖 用于登录界面等Solid页面
    fun addFullOverlay(fragment: Fragment) {
        currentFullOverlay = fragment
        val transition = supportFragmentManager.beginTransaction()
        transition.add(R.id.full_overlay, fragment)
        transition.commit()
    }

    fun removeFullOverlay() {
        val transition = supportFragmentManager.beginTransaction()
        transition.remove(currentFullOverlay)
        transition.commit()
    }

    private fun initBottomBarEvents() {
        bottomNav = findViewById(R.id.bottomNav)
//        bottomNav
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_pfld -> {
//                    addContentOverlay(PfldFragment())
                    addContentOverlay(pfldFragment)
                    true
                }
                R.id.action_poi -> {
                    addContentOverlay(PoiSearchFragment())
                    true
                }
                R.id.action_sheet -> {
                    addContentOverlay(sheetFragment)
                    true
                }
                R.id.action_profile -> {
                    addContentOverlay(profileFragment)
                    true
                }
//                R.id.action_history -> {
//                    addContentOverlay(PfldFragment())
//                    removeContentOverlay()

//                    val intent = Intent(this, FaceTrackerActivity::class.java)
//                    startActivity(intent)

//                    addContentOverlay(WebViewFragment(H5Url.PROFILE))
//                    true
//                }
                else -> {
                    true
                }
            }

        }
    }

    fun refreshProfile() {
        removeContentOverlay()
        addContentOverlay(profileFragment)
    }

    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.BLUETOOTH,
        Manifest.permission.BLUETOOTH_ADMIN,
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

    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        LiveEventBus.get(DetectorService.CHANNEL_SET_WINDOWS_VISIBLE).post(false)
    }
}