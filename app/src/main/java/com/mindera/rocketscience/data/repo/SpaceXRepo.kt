package com.mindera.rocketscience.data.repo

import com.mindera.rocketscience.data.network.SpaceXApi
import com.mindera.rocketscience.data.toDomain
import com.mindera.rocketscience.domain.CompanyInfo
import com.mindera.rocketscience.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpaceXRepo @Inject constructor(private val remoteSource: SpaceXApi) {

    fun letCompanyInfoFlow(): Flow<Result<CompanyInfo>> = flow {
        emit(Result.loading<CompanyInfo>())
        val response = remoteSource.getCompanyInfo()
        if (response.isSuccessful) {
            val body = response.body()
            if(body != null){
                emit(Result.success<CompanyInfo>(body.toDomain()))
            }
            else {
                emit(Result.error<CompanyInfo>(message = ""))
            }
        }
    }.catch {
        emit(Result.error<CompanyInfo>(message = it.message))
    }
}