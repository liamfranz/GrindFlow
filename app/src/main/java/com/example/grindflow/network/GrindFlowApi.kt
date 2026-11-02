
package com.example.grindflow.network

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class AuthMeResponse(
    val authenticated: Boolean,
    val firebaseUserId: String?,
    val email: String?,
    val message: String?
)

object GrindFlowApi {

    private const val BASE_URL =
        "http://10.74.226.72:5231"

    private val client = OkHttpClient()

    private val mainHandler =
        Handler(Looper.getMainLooper())

    fun getCurrentUser(
        idToken: String,
        onResult: (Result<AuthMeResponse>) -> Unit
    ) {

        val request = Request.Builder()
            .url("$BASE_URL/api/auth/me")
            .header("Authorization", "Bearer $idToken")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(
                call: Call,
                e: IOException
            ) {
                mainHandler.post {
                    onResult(Result.failure(e))
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {

                try {
                    response.use {

                        val responseBody =
                            it.body?.string().orEmpty()

                        if (!it.isSuccessful) {
                            throw IOException(
                                "API returned HTTP ${it.code}: $responseBody"
                            )
                        }

                        val json =
                            JSONObject(responseBody)

                        val result = AuthMeResponse(
                            authenticated = json.optBoolean(
                                "authenticated"
                            ),
                            firebaseUserId = json.optString(
                                "firebaseUserId"
                            ).takeIf { value ->
                                value.isNotBlank() && value != "null"
                            },
                            email = json.optString(
                                "email"
                            ).takeIf { value ->
                                value.isNotBlank() && value != "null"
                            },
                            message = json.optString(
                                "message"
                            )
                        )

                        mainHandler.post {
                            onResult(Result.success(result))
                        }
                    }

                } catch (e: Exception) {

                    mainHandler.post {
                        onResult(Result.failure(e))
                    }
                }
            }
        })
    }
}
