package com.furkanaskin.app.podpocket.ui.settings

import android.app.Application
import androidx.databinding.ObservableField
import com.furkanaskin.app.podpocket.Podpocket
import com.furkanaskin.app.podpocket.core.BaseViewModel

/**
 * Created by Furkan on 16.04.2019
 */

class SettingsViewModel(app: Application) : BaseViewModel(app) {
    var progressBarView: ObservableField<Boolean> = ObservableField(false)

    init {
        (app as? Podpocket)?.component?.inject(this)
    }

}