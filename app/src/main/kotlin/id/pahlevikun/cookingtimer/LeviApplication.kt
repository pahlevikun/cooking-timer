package id.pahlevikun.cookingtimer

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import id.pahlevikun.cookingtimer.common.JotterListener
import id.pahlevikun.cookingtimer.common.helper.LocalizationHelper
import id.pahlevikun.cookingtimer.common.helper.Logger
import id.pahlevikun.cookingtimer.scheme.appholder.manifest.ApplicationManifest
import id.pahlevikun.cookingtimer.scheme.appholder.manifest.DependenciesInitializer
import id.pahlevikun.cookingtimer.scheme.appsetting.AppEnvironment
import id.pahlevikun.cookingtimer.scheme.appsetting.AppSettingProvider
import id.pahlevikun.cookingtimer.scheme.appsetting.LanguageSetting
import id.pahlevikun.jotter.Jotter
import id.pahlevikun.jotter.event.ActivityEvent
import id.pahlevikun.jotter.event.FragmentEvent
import net.danlew.android.joda.JodaTimeAndroid

class LeviApplication :
    MultiDexApplication(),
    AppSettingProvider,
    ApplicationManifest.Provider {

    override val environment: AppEnvironment
        get() = AppEnvironment(
            appName = BuildConfig.APP_NAME,
            appVersion = BuildConfig.VERSION_NAME,
            applicationId = BuildConfig.APPLICATION_ID,
            appVersionCode = BuildConfig.VERSION_CODE,
            isDebuggable = BuildConfig.DEBUG,
            buildType = AppEnvironment.BuildType.parse(BuildConfig.BUILD_TYPE),
            languageSetting = LocalizationHelper.getLanguage(applicationContext)
        )

    override lateinit var manifest: ApplicationManifest

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        manifest = DependenciesInitializer(this).also {
            it.applicationComponent.inject(this)
        }

        initJodaTime()
        initStetho()
        initLogging()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalizationHelper.applyLanguageContext(base))
        MultiDex.install(this)
    }

    override fun getCountrySetting(): LanguageSetting {
        return environment.languageSetting
    }

    private fun initLogging() {
        Logger.logEnabled = environment.isDebuggable
        Logger.debug("APPLICATION_ID >>> ${environment.applicationId}")

        Jotter
            .Builder(this)
            .setLogEnable(environment.isDebuggable)
            .setActivityEventFilter(
                listOf(
                    ActivityEvent.CREATE,
                    ActivityEvent.DESTROY,
                    ActivityEvent.RESUME,
                    ActivityEvent.PAUSE
                )
            )
            .setFragmentEventFilter(
                listOf(
                    FragmentEvent.CREATE,
                    FragmentEvent.DESTROY,
                    FragmentEvent.ATTACH,
                    FragmentEvent.DETACH
                )
            )
            .setJotterListener(JotterListener)
            .build()
            .startListening()
    }

    private fun initJodaTime() {
        JodaTimeAndroid.init(this)
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

}