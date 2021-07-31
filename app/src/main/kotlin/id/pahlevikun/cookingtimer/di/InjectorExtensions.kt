package id.pahlevikun.cookingtimer.di

import android.content.Context
import id.pahlevikun.cookingtimer.di.application.ApplicationComponent
import id.pahlevikun.cookingtimer.di.domain.DomainComponent
import id.pahlevikun.cookingtimer.di.presentation.PresentationComponent
import id.pahlevikun.cookingtimer.scheme.appholder.manifest.ApplicationManifest

fun Context.getApplicationComponent(): ApplicationComponent {
    return (applicationContext as ApplicationManifest.Provider)
        .manifest
        .applicationComponent
}

fun Context.getDomainComponent(): DomainComponent {
    return (applicationContext as ApplicationManifest.Provider)
        .manifest
        .domainComponent
}

fun Context.getPresentationComponent(): PresentationComponent {
    return (applicationContext as ApplicationManifest.Provider)
        .manifest
        .presentationComponent
}
