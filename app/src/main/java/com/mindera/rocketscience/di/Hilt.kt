package com.mindera.rocketscience.di

import android.app.Application
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.network.interceptor.ConnectivityInterceptor
import com.mindera.rocketscience.data.repo.SpaceXRepo
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
    fun provideSpaceXApi(connectivityInterceptor: ConnectivityInterceptor)
            = SpaceXApi.invoke(connectivityInterceptor)
}

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Singleton
    @Provides
    fun provideSpaceXRepo(remoteSource: SpaceXApi)
            = SpaceXRepo(remoteSource = remoteSource)
}