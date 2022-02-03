package com.mindera.rocketscience.ui.spacex

import androidx.lifecycle.ViewModel
import com.mindera.rocketscience.data.repo.SpaceXRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(repo: SpaceXRepo): ViewModel() {

}