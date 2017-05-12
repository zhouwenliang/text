package cn.zhouwl.sleeptime.api

import cn.zhouwl.sleeptime.entity.Sleep
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * @author zhouwenliang(gzzhouwenliang@corp.netease.com)
 * *
 * @usage
 * *
 * @time 2017/5/8 0008
 */
interface SleepApi {
    @GET("post_sleep.php")
    fun sleep(@Query("username") username: String, @Query("sleep_time") sleepTime: Long): Observable<Void>

    @GET("post_oki.php")
    fun oki(@Query("username") username: String, @Query("okiTime") okiTime: Long): Observable<Void>

    @GET("get_sleep.php")
    fun getSleep(@Query("username") username: String): Observable<List<Sleep>>
}