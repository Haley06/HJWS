package change.aaa.rtmpdemo.util

import android.util.Log
import change.aaa.rtmpdemo.data.UserModel
import change.aaa.rtmpdemo.pfld.DataTest
import change.aaa.rtmpdemo.pfld.FatigueFeatureItemRes
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object FatiRequest {
    val client = OkHttpClient()
    val JSON = "application/json; charset=utf-8".toMediaType()

    fun send(res: FatigueFeatureItemRes) {
        res.timestamp = res.timestamp / 1000L
        var tem = DataTest("1231231", 1.1f, res.timestamp)
        val body = RequestBody.create(JSON, Gson().toJson(tem))
        val request = Request.Builder()
            .url("http://49.232.78.210:8089/app/fatigue/record")
            .header("token", UserModel.token)
            .post(body)
            .build()

        RDExecutor.runOnNetworkThread {
            try {
                val r = client.newCall(request)
                r.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("anan", "onFailure: ${request.url} ")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.e("anan", "response: ${response.body?.string()}")
                    }

                })
            } catch (ignored: Exception) {
            }
        }

        sendAlcohol(res.timestamp)
        sendMood(res.emotion_res, res.timestamp)
        sendLookAhead(res.timestamp)
    }

    private fun sendAlcohol(timestamp: Long) {
        val s = "{\"time\":$timestamp}"
        val body = RequestBody.create(JSON, s)
        val request = Request.Builder()
            .url("http://49.232.78.210:8089/app/fatigue/alcohol")
            .header("token", UserModel.token)
            .post(body)
            .build()

        Log.e("anan", "sendAlcohol:$s ")
        RDExecutor.runOnNetworkThread {
            try {
                val r = client.newCall(request)
                r.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("anan", "onFailure: ${request.url} ")
                    }

                    override fun onResponse(call: Call, response: Response) {

//                        Log.e("anan", "onResponse: ${Gson().toJson(call.request().body)}")
                        Log.e("anan", "response: ${response.body?.string()}")
                    }

                })
            } catch (ignored: Exception) {
            }
        }
    }

    private fun sendMood(mood: Int, timestamp: Long) {
//        val body = RequestBody.create(JSON, Gson().toJson(timestamp))

        val s = "{\"time\":$timestamp, \"feeling\":$mood}"
        val body = RequestBody.create(JSON, s)
        val request = Request.Builder()
            .url("http://49.232.78.210:8089/app/fatigue/mood")
            .header("token", UserModel.token)
            .post(body)
            .build()

        Log.e("anan", "sendMood: $s")
        RDExecutor.runOnNetworkThread {
            try {
                val r = client.newCall(request)
                r.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("anan", "onFailure: ${request.url} ")
                    }

                    override fun onResponse(call: Call, response: Response) {
//                        Log.e("anan", "onResponse: ${call.request().body?.toString()}")
                        Log.e("anan", "response: ${response.body?.string()}")
                    }

                })
            } catch (ignored: Exception) {
            }
        }
    }

    private fun sendLookAhead(timestamp: Long) {
        val s = "{\"time\":$timestamp}"
        val body = RequestBody.create(JSON, s)
        val request = Request.Builder()
            .url("http://49.232.78.210:8089/app/fatigue/lookAhead")
            .header("token", UserModel.token)
            .post(body)
            .build()
        Log.e("anan", "sendLookAhead: $s")
        RDExecutor.runOnNetworkThread {
            try {
                val r = client.newCall(request)
                r.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("anan", "onFailure: ${request.url} ")
                    }

                    override fun onResponse(call: Call, response: Response) {
//                        Log.e("anan", "onResponse: ${call.request().body?.()}")
                        Log.e("anan", "response: ${response.body?.string()}")
                    }

                })
            } catch (ignored: Exception) {
            }
        }
    }
}