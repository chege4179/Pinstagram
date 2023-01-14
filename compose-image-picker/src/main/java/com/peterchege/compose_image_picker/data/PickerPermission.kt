/*
 * Copyright 2023 PInstagram
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.compose_image_picker.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.peterchege.compose_image_picker.constant.goSetting

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PickerPermission(
    permission: String,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val permissionState = rememberPermissionState(permission) {
        permissionRequested = true
    }

    if (permissionState.status.isGranted) {
        content()
    } else {
        if (!permissionRequested && !permissionState.status.shouldShowRationale) {
            SideEffect {
                permissionState.launchPermissionRequest()
            }
        } else if (permissionRequested && permissionState.status.shouldShowRationale) {
            SideEffect {
                permissionState.launchPermissionRequest()
            }
        } else {
            context.goSetting()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PickerPermissions(
    permissions: List<String>,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val permissionState = rememberMultiplePermissionsState(permissions) {
        permissionRequested = true
    }

    if (permissionState.allPermissionsGranted) {
        content()
    } else {
        if (!permissionRequested && !permissionState.shouldShowRationale) {
            SideEffect {
                permissionState.launchMultiplePermissionRequest()
            }
        } else if (permissionRequested && permissionState.shouldShowRationale) {
            SideEffect {
                permissionState.launchMultiplePermissionRequest()
            }
        } else {
            context.goSetting()
        }
    }
}
