package id.pahlevikun.cookingtimer.domain.usecase

import id.pahlevikun.cookingtimer.common.base.usecase.BaseUseCase
import id.pahlevikun.cookingtimer.common.extension.removeNonNumeric
import id.pahlevikun.cookingtimer.data.config.ConfigGateway
import id.pahlevikun.cookingtimer.domain.entity.AppUpdaterConfig
import id.pahlevikun.cookingtimer.scheme.appsetting.AppSettingProvider
import javax.inject.Inject

interface GetAppUpdaterConfigUseCase : BaseUseCase<Unit, AppUpdaterConfig>

class GetAppUpdaterConfigUseCaseImpl @Inject constructor(
    private val configGateway: ConfigGateway,
    private val appSettingProvider: AppSettingProvider,
) : GetAppUpdaterConfigUseCase {

    override fun execute(parameters: Unit): AppUpdaterConfig {
        val optional = configGateway.getUpdaterOptionalVersion()
        val force = configGateway.getUpdaterMandatoryVersion()
        val forceEnabled = configGateway.isUpdaterForceUpdateEnabled()
        val optionalEnabled = configGateway.isUpdaterOptionalUpdateEnabled()
        val enabled = configGateway.isUpdaterEnabled()
        val locked = configGateway.isUpdaterLockerEnabled()
        val inAppUpdateEnabled = configGateway.isInAppUpdaterEnabled()
        val optionalVersion = optional.removeNonNumeric().toIntOrNull() ?: 0
        val forceVersion = force.removeNonNumeric().toIntOrNull() ?: 0
        val currentVersion = appSettingProvider.environment.appVersion
            .removeNonNumeric()
            .toIntOrNull() ?: 0

        return AppUpdaterConfig(
            isEnabled = enabled,
            isAppLocked = locked,
            shouldForceUpdate = forceEnabled && (currentVersion <= forceVersion),
            isOptionalUpdateEnabled = optionalEnabled && (currentVersion <= optionalVersion),
            shouldUseInAppUpdate = inAppUpdateEnabled
        )
    }
}