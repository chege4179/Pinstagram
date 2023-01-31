package com.peterchege.pinstagram.feature.feature_profile.presentation.user_profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) :ViewModel(){


    init {
        savedStateHandle.get<String>("userId")?.let {

        }
    }



}