package com.mindera.rocketscience.data.repo

import com.mindera.rocketscience.data.network.SpaceXApi
import javax.inject.Inject

class SpaceXRepo @Inject constructor(private val remoteSource: SpaceXApi) {

}