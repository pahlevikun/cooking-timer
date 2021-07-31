package id.pahlevikun.cookingtimer.domain.entity

class AppUpdaterConfig(
    val isEnabled: Boolean,
    val isAppLocked: Boolean,
    val isOptionalUpdateEnabled: Boolean,
    val shouldForceUpdate: Boolean,
    val shouldUseInAppUpdate: Boolean
)