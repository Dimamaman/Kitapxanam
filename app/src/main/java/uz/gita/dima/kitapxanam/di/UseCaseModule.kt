package uz.gita.dima.kitapxanam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCase
import uz.gita.dima.kitapxanam.domain.usecase.BookUseCaseImpl
import uz.gita.dima.kitapxanam.domain.usecase.SavedBooksUseCase
import uz.gita.dima.kitapxanam.domain.usecase.SavedBooksUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindUseCase(impl: BookUseCaseImpl): BookUseCase

    @[Binds Singleton]
    fun getSavedBooksUseCase(impl: SavedBooksUseCaseImpl): SavedBooksUseCase
}