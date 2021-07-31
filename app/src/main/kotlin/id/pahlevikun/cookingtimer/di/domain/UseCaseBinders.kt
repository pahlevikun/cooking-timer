package id.pahlevikun.cookingtimer.di.domain

import dagger.Binds
import dagger.Module
import id.pahlevikun.cookingtimer.domain.usecase.GetAppUpdaterConfigUseCase
import id.pahlevikun.cookingtimer.domain.usecase.GetAppUpdaterConfigUseCaseImpl

@Module
abstract class UseCaseBinders {

    @DomainScope
    @Binds
    abstract fun bindGetAppUpdaterConfigUseCase(useCase: GetAppUpdaterConfigUseCaseImpl): GetAppUpdaterConfigUseCase
}