package change.aaa.rtmpdemo.webview

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import change.aaa.rtmpdemo.R
import change.aaa.rtmpdemo.StageActivity
import wendu.dsbridge.DWebView
import java.util.*

class WebViewFragment : Fragment {
    private lateinit var root: View
    private val TAG = "WebViewFragment"
    private lateinit var dWebView: DWebView
    private var url = ""

    constructor(url: String) : super() {
        val bundle = Bundle()
        bundle.putString(KEY_URL, url)
        arguments = bundle
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_webview, container, false)
        initDWebView()
        return root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDWebView() {


        dWebView = root.findViewById(R.id.dwebview)

        val mWebSettings: WebSettings = dWebView.getSettings()
        mWebSettings.javaScriptCanOpenWindowsAutomatically =
            true //设置js可以直接打开窗口，如window.open()，默认为false

        mWebSettings.javaScriptEnabled = true //是否允许JavaScript脚本运行，默认为false。设置true时，会提醒可能造成XSS漏洞

        mWebSettings.setSupportZoom(true) //是否可以缩放，默认true

        mWebSettings.builtInZoomControls = true //是否显示缩放按钮，默认false

        mWebSettings.useWideViewPort = true //设置此属性，可任意比例缩放。大视图模式

        mWebSettings.loadWithOverviewMode = true //和setUseWideViewPort(true)一起解决网页自适应问题

        mWebSettings.setAppCacheEnabled(true) //是否使用缓存

        mWebSettings.domStorageEnabled = true //开启本地DOM存储

        mWebSettings.loadsImagesAutomatically = true // 加载图片

        mWebSettings.mediaPlaybackRequiresUserGesture = false


        dWebView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && dWebView.canGoBack()) {
                    //表示按返回键时的操作
                    dWebView.goBack()
                    return@OnKeyListener true
                }
            }
            false
        })
        dWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    view.loadUrl(url)
                    dWebView.stopLoading()
                    return true
                }
                return false
            }


        }
        dWebView.addJavascriptObject(JsApi(getStageActivity()), "")
        dWebView.webChromeClient = object : WebChromeClient() {


            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }


        dWebView.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e(TAG, "onPageFinished: success")
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (error != null) {
                    Log.e(TAG, "onReceivedError: ${error.description}")
                }
            }
        }
    }

    fun refresh() {
        dWebView.reload()
    }

    override fun onResume() {
        super.onResume()
        url = arguments?.getString(KEY_URL) ?: ""
        if (url == "") {
            getStageActivity().toast("参数不全")
            dWebView.visibility = View.INVISIBLE
            return
        }
        dWebView.clearCache(true)
        dWebView.clearHistory()

        val webSettings = dWebView.settings

        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        WebView.setWebContentsDebuggingEnabled(true)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            webSettings.safeBrowsingEnabled = false
        }

//        dWebView.settings = webSettings


        dWebView.loadUrl(url)
        Log.e("TAG", "onResume:$url")
        dWebView.postDelayed(Runnable { dWebView.clearHistory() }, 3000)
    }

    fun getStageActivity(): StageActivity {
        return activity as StageActivity

    }

    companion object {
        const val KEY_URL = "url"

    }

}