package id.pahlevikun.cookingtimer.scheme.appholder.manifest

import android.app.Application
import id.pahlevikun.cookingtimer.di.application.ApplicationComponent
import id.pahlevikun.cookingtimer.di.domain.DomainComponent
import id.pahlevikun.cookingtimer.di.presentation.PresentationComponent

interface ApplicationManifest {

    val application: Application
    val applicationComponent: ApplicationComponent
    val domainComponent: DomainComponent
    val presentationComponent: PresentationComponent

    interface Provider {
        val manifest: ApplicationManifest
    }
}

