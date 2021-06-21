package com.example.desafio_android_rodrigo_dias_soares.netWork

import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.MARVEL_PRIVATE_KEY
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.MARVEL_PUBLIC_KEY
import com.example.desafio_android_rodrigo_dias_soares.model.api.entity.MARVEL_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class MarvelClient {

    private var marvelApi: MarvelApi = getService()

    companion object {
        private fun getService(): MarvelApi {

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", MARVEL_PUBLIC_KEY)
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", md5("$ts$MARVEL_PRIVATE_KEY$MARVEL_PUBLIC_KEY"))
                    .build()

                val requestBuider = original.newBuilder().url(url)
                val request = requestBuider.build()
                chain.proceed(request)
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(MARVEL_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
            return retrofit.create(MarvelApi::class.java)
        }

        fun loadCharacters(page: Int) = getService().allCharacters(page * 20)

        fun getComics(heroId:Int) = getService().comics(heroId)

        private fun md5(input:String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray()))
                .toString(16).padStart(32, '0')
        }
    }
}