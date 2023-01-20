package com.peterchege.pinstagram.feature.feature_comments.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CommentScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) :ViewModel(){

    init {
        savedStateHandle.get<String>("postId")?.let {
            Log.e("Comments Screen","Post Id: ${it}")
        }

    }

}