package change.aaa.rtmpdemo.util

import com.auth0.android.jwt.JWT

object JWTUtils {
    fun getUserSub(jwtEncoded: String): String {
        val jwt = JWT(jwtEncoded)
        return jwt.getClaim("sub").asString()?:"-1"
    }
}