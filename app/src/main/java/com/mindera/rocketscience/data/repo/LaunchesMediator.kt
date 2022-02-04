package com.mindera.rocketscience.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mindera.rocketscience.data.local.SpaceXDb
import com.mindera.rocketscience.data.local.entity.LaunchesEntity
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class LaunchesMediator(private val remoteSource: SpaceXApi, private val localSource: SpaceXDb):
    RemoteMediator<Int, LaunchesEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LaunchesEntity>
    ): MediatorResult {
        try
        {
            val response = remoteSource.getAlllaunches()
            localSource.withTransaction {
                if(loadType == LoadType.REFRESH){
                    localSource.launchesDao().clearAll()
                }
                localSource.launchesDao().insertAll(response.map { dto -> dto.toEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}