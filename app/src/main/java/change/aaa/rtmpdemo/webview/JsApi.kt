package change.aaa.rtmpdemo.webview

import android.webkit.JavascriptInterface
import change.aaa.rtmpdemo.StageActivity
import change.aaa.rtmpdemo.data.UserModel

class JsApi(private val stageActivity: StageActivity) {
    @JavascriptInterface
    fun getToken(obj: Any): Any {
        val token = UserModel.token
//        Log.v("dydy", "GET TOKEN " + token)
        return token
    }

    @JavascriptInterface
    fun setToken(token: Any): Any {
//        Log.v("dydy", "SET TOKEN $token")
        UserModel.token = token.toString()
        // 登录成功退出界面
        if (stageActivity.isInFullScreenLogin) {
            stageActivity.removeFullOverlay()
            stageActivity.isInFullScreenLogin = false
        }
        return Any()
    }
}