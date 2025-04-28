package com.neotelemetrixgdscunand.kamekapp.presentation.ui.util

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> LifecycleOwner.collectChannelWhenStarted(
    channelFlow: Flow<T>, onCollect: suspend (T) -> Unit
) {
    this.lifecycleScope.launch {
        this@collectChannelWhenStarted.lifecycle.repeatOnLifecycle(
            Lifecycle.State.STARTED
        ) {
            withContext(Dispatchers.Main.immediate) {
                channelFlow.collect(onCollect)
            }
        }
    }
}



