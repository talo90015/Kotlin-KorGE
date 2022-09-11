package api

import com.soywiz.korio.dynamic.mapper.Mapper
import com.soywiz.korio.dynamic.serialization.stringifyTyped
import com.soywiz.korio.net.http.Http
import com.soywiz.korio.net.http.HttpClient
import com.soywiz.korio.stream.openAsync
import com.soywiz.korio.serialization.json.Json
import model.UserScore

object UserScoreAPI {
    suspend fun upload(data: UserScore){
        val content = Json.stringifyTyped(data, Mapper)
        val result = HttpClient().requestAsString(
            method = Http.Method.POST,
            url = "http://127.0.0.1:8080/uploadScore",
            content = content.openAsync()
        )
        if (result.success){
            println("更新成功")
        }
    }
    suspend fun getRankList(){
        val result = HttpClient().requestAsString(
            method = Http.Method.GET,
            url = "http://127.0.0.1:8080/getRankList"
        )
        if (result.success){
            println("取得成功: ${result.content}")
        }
    }
}
