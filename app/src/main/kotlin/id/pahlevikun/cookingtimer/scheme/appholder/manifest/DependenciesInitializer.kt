package id.pahlevikun.cookingtimer.scheme.appholder.manifest

import android.app.Application
import id.pahlevikun.cookingtimer.di.application.ApplicationComponent
import id.pahlevikun.cookingtimer.di.application.ApplicationModule
import id.pahlevikun.cookingtimer.di.application.DaggerApplicationComponent
import id.pahlevikun.cookingtimer.di.domain.DaggerDomainComponent
import id.pahlevikun.cookingtimer.di.domain.DomainComponent
import id.pahlevikun.cookingtimer.di.presentation.DaggerPresentationComponent
import id.pahlevikun.cookingtimer.di.presentation.PresentationComponent

class DependenciesInitializer(override val application: Application) :
    ApplicationManifest {

    private val _applicationComponent: ApplicationComponent =
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(application))
            .build()

    private val _domainComponent: DomainComponent =
        DaggerDomainComponent
            .builder()
            .component(_applicationComponent)
            .build()

    private val _presentationComponent: PresentationComponent =
        DaggerPresentationComponent
            .builder()
            .component(_domainComponent)
            .build()

    override val applicationComponent: ApplicationComponent
        get() = _applicationComponent
    override val domainComponent: DomainComponent
        get() = _domainComponent
    override val presentationComponent: PresentationComponent
        get() = _presentationComponent
}