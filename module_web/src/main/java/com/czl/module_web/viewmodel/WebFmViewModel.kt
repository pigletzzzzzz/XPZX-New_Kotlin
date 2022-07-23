package com.czl.module_web.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import com.czl.lib_base.base.BaseViewModel
import com.czl.lib_base.base.MyApplication
import com.czl.lib_base.binding.command.BindingAction
import com.czl.lib_base.binding.command.BindingCommand
import com.czl.lib_base.bus.event.SingleLiveEvent
import com.czl.lib_base.data.DataRepository

/**
 * @author Alwyn
 * @Date 2020/10/31
 * @Description
 */
class WebFmViewModel(application: MyApplication, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()
    val collectFlag = ObservableField(false)
    val canForwardFlag = ObservableField(false)
    val showWebLinkMenuFlag = ObservableField(false)
    val canGoBackFlag = ObservableField(false)

    class UiChangeEvent {
        val closeEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val collectEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val goForwardEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val showMenuEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val openBrowserEvent: SingleLiveEvent<Void> = SingleLiveEvent()
        val copyCurrentLinkEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    }

    override fun setToolbarRightClick() {
        uc.closeEvent.call()
    }

    val onCollectClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.collectEvent.call()
    })
    val onMenuClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.showMenuEvent.call()
    })

    val onGoForwardClick: View.OnClickListener = View.OnClickListener {
        uc.goForwardEvent.call()
    }

    val onWebLinkFocusCommand: BindingCommand<Boolean> = BindingCommand { focus ->
        showWebLinkMenuFlag.set(focus)
    }

    val copyLinkClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.copyCurrentLinkEvent.call()
        showWebLinkMenuFlag.set(false)
    })

    val openOnBrowserClick: BindingCommand<Void> = BindingCommand(BindingAction {
        uc.openBrowserEvent.call()
        showWebLinkMenuFlag.set(false)
    })


    fun saveBrowseHistory(title: String, link: String) {
    }
}