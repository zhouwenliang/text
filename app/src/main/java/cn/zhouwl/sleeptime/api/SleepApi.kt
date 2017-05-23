package cn.zhouwl.sleeptime.api

import cn.zhouwl.sleeptime.entity.NoteResult
import cn.zhouwl.sleeptime.entity.Result
import cn.zhouwl.sleeptime.entity.Sleep
import retrofit2.http.*
import rx.Observable

interface SleepApi {
    @GET("post_sleep.php")
    fun sleep(@Query("username") username: String, @Query("sleep_time") sleepTime: Long): Observable<Result>

    @GET("post_oki.php")
    fun oki(@Query("username") username: String, @Query("okiTime") okiTime: Long): Observable<Result>

    @GET("get_sleep.php")
    fun getSleep(@Query("username") username: String): Observable<List<Sleep>>

    @GET("get_note.php")
    fun getNote(@Query("username") username: String): Observable<NoteResult>

    @FormUrlEncoded
    @POST("post_note.php")
    fun postNote(@Field("username") username: String, @Field("content") content: String): Observable<Result>
}
