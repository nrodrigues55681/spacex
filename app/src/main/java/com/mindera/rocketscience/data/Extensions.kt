package com.mindera.rocketscience.data

import com.mindera.rocketscience.data.network.models.CompanyInfoDto
import com.mindera.rocketscience.domain.CompanyInfo

fun CompanyInfoDto.toDomain(): CompanyInfo = CompanyInfo(
    name = name ?: "", founder = founder ?: "", employees = employees ?: 0,
    founded = founded ?: 0, launchSites = launchSites ?: 0, valuation = valuation ?: 0)
