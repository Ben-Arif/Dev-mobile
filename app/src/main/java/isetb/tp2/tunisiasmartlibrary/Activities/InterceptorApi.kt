package isetb.tp2.tunisiasmartlibrary.Activities

import android.content.Context
import android.content.Context.MODE_PRIVATE
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class InterceptorApi(context: Context) {


    private val token: String = context.getSharedPreferences("prefs", MODE_PRIVATE)
        .getString("token", "").toString()



    private val interceptor= Interceptor {
            chain ->
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization","Bearer $token")
            .build()
        chain.proceed(request)

    }
    val client= OkHttpClient.Builder()
        .addInterceptor (interceptor)
        .build()


}