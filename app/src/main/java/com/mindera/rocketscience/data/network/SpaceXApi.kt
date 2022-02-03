package com.mindera.rocketscience.data.network

import com.mindera.rocketscience.data.network.interceptor.ConnectivityInterceptor
import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.data.network.models.LaunchesDto
import com.mindera.rocketscience.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface SpaceXApi {

    @GET("info")
    suspend fun getCompanyInfo(): Response<CompanyInfoDto>

    @GET("launches")
    suspend fun getAlllaunches(): Response<List<LaunchesDto>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : SpaceXApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SpaceXApi::class.java)
        }
    }

}