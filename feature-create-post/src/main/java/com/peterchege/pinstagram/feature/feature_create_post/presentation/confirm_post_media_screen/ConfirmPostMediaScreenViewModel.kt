package com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post_media_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConfirmPostMediaScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
):ViewModel() {


    init {
        savedStateHandle.get<String>("assets")?.let{
            Log.e("ASSETS",it)
        }
    }
}