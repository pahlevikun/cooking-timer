package id.pahlevikun.cookingtimer.data.config.base

object ConfigKey {

    // AppUpdater
    const val FEATURE_UPDATER_ENABLED: String = "feature_updater_enabled"
    const val FEATURE_UPDATER_OPTIONAL_MIN_VERSION: String = "feature_updater_optional_min_version"
    const val FEATURE_UPDATER_MANDATORY_MIN_VERSION: String =
        "feature_updater_mandatory_min_version"
    const val FEATURE_UPDATER_FORCE_UPDATE_ENABLED: String = "feature_updater_force_update"
    const val FEATURE_UPDATER_OPTIONAL_UPDATE_ENABLED: String = "feature_updater_optional_update"
    const val FEATURE_UPDATER_LOCKER_ENABLED: String = "feature_updater_locker_enabled"
    const val FEATURE_UPDATER_SPECIFIC_OS_ENABLED: String = "feature_updater_specific_os_enabled"
    const val FEATURE_UPDATER_SPECIFIC_OS_VERSION: String = "feature_updater_specific_os_version"
    const val FEATURE_UPDATER_IN_APP_ENABLED: String = "feature_updater_in_app_enabled"

    // Generic
    const val FEATURE_ENCRYPTION_ENABLED: String = "feature_encryption_enabled"
}