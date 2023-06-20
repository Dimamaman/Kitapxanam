package uz.gita.dima.kitapxanam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindUseCase(impl: BookUseCaseImpl): BookUseCase
}