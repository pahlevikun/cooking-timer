package id.pahlevikun.cookingtimer.service

import id.pahlevikun.cookingtimer.domain.usecase.GetAppUpdaterConfigUseCase


class AppUpdaterService(private val useCase: GetAppUpdaterConfigUseCase) {

    fun start(
        onOptionalUpdate: (() -> Unit),
        onForceUpdate: (() -> Unit),
        onMaintainMode: (() -> Unit)
    ) {
        val data = useCase.execute(Unit)
        when {
            data.isAppLocked -> onMaintainMode.invoke()
            data.shouldForceUpdate -> onForceUpdate.invoke()
            data.isOptionalUpdateEnabled -> onOptionalUpdate.invoke()
        }
    }

}