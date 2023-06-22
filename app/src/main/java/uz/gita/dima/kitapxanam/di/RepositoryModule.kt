package uz.gita.dima.kitapxanam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.kitapxanam.data.repositoryimpl.BookRepositoryImpl
import uz.gita.dima.kitapxanam.data.repositoryimpl.SavedBooksRepositoryImpl
import uz.gita.dima.kitapxanam.domain.repository.BookRepository
import uz.gita.dima.kitapxanam.domain.repository.SavedBooksRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindBookRepository(impl: BookRepositoryImpl): BookRepository


    @[Binds Singleton]
    fun getSavedBooksRepository(impl: SavedBooksRepositoryImpl): SavedBooksRepository
}