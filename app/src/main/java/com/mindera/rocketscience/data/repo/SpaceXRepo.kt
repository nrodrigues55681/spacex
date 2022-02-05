package com.mindera.rocketscience.data.repo

import androidx.paging.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mindera.rocketscience.data.local.SpaceXDb
import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.toDomain
import com.mindera.rocketscience.data.toEntity
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.domain.LaunchSuccessFilter
import com.mindera.rocketscience.domain.Launches
import com.mindera.rocketscience.ui.utils.FilterData
import com.mindera.rocketscience.utils.DEFAULT_PAGE_SIZE
import com.mindera.rocketscience.utils.Result
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SpaceXRepo @Inject constructor(private val remoteSource: SpaceXApi, private val localSource: SpaceXDb) {

    fun letCompanyInfoFlow(): Flow<Result<CompanyInfo>> = flow {
        emit(Result.loading<CompanyInfo>())
        val localCompanyInfoEntity = localSource.companyInfoDao().getCompanyInfo()
        val localCompanyInfo: CompanyInfo? = localCompanyInfoEntity?.toDomain()
        localCompanyInfo?.let {
            emit(Result.success<CompanyInfo>(localCompanyInfo))
        }

        try {
            val response = remoteSource.getCompanyInfo()
            if (response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    localSource.companyInfoDao().insert(body.toEntity())
                    val remoteCompanyInfo = body.toDomain()
                    if(remoteCompanyInfo != localCompanyInfo){
                        emit(Result.success<CompanyInfo>(remoteCompanyInfo))
                    }
                } else if(localCompanyInfo == null) {
                    emit(Result.error<CompanyInfo>(message = ""))
                }
            }
        } catch(ex: Exception) {
            if(localCompanyInfo == null) {
                emit(Result.error<CompanyInfo>(message = ""))
            }
        }
    }.catch {
        emit(Result.error<CompanyInfo>(message = it.message))
    }

    @OptIn(ExperimentalPagingApi::class)
    fun letLaunchesFlow(filterData: FilterData): Flow<PagingData<Launches>> {
        val pagingSourceFactory = {
            localSource.launchesDao().getLaunchesPager(filterQuery(filterData = filterData))
        }

        return Pager(
            remoteMediator = LaunchesMediator(remoteSource = remoteSource, localSource = localSource),
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory).flow
            .map { pd -> pd.map { be -> be.toDomain() }}
            .catch { emit(PagingData.from(listOf())) }
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    private fun filterQuery(filterData: FilterData): SimpleSQLiteQuery{
        var queryString = "SELECT * FROM launches"
        val launchSuccessFilterEnable = filterData.launchSuccessFilter != LaunchSuccessFilter.ALL
        if(launchSuccessFilterEnable){
            queryString = queryString.plus(" WHERE " +
                    "launchSuccess = ${if(filterData.launchSuccessFilter == LaunchSuccessFilter.SUCCESS) 1 else 0}")
        }
        if(filterData.filterByDate){
            val andOrWhere = if (launchSuccessFilterEnable) "AND" else "WHERE"
            queryString = queryString.plus(" $andOrWhere launchYear >= ${filterData.minYear} " +
                    "AND launchYear <= ${filterData.maxYear}")
        }
        queryString = queryString.plus(" ORDER BY missionName ${filterData.sorting.name}")
        return SimpleSQLiteQuery(queryString)
    }
}