package cn.zhouwl.sleeptime.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author zhouwenliang(gzzhouwenliang@corp.netease.com)
 * *
 * @usage
 * *
 * @time 2017/5/8 0008
 */
object RxService {
    private val BASETESTURL = "http://119.23.244.52"
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASETESTURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> createApi(clazz: Class<T>): T {

        return retrofit.create(clazz)
    }

}//construct
