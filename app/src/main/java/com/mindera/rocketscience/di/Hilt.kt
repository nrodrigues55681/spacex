package com.mindera.rocketscience.di

import android.app.Application
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.network.interceptor.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(application: Application)
            = ConnectivityInterceptor(application)

    @Provides
    @Singleton
    fun provideCodewarsApi(connectivityInterceptor: ConnectivityInterceptor)
            = SpaceXApi.invoke(connectivityInterceptor)
}