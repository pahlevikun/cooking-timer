package id.pahlevikun.cookingtimer.common.base.usecase

interface BaseUseCase<in P, out R> {
    fun execute(parameters: P): R
}