package uz.gita.dima.kitapxanam.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.kitapxanam.data.repositoryimpl.BookRepositoryImpl
import uz.gita.dima.kitapxanam.domain.repository.BookRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindBookRepository(impl: BookRepositoryImpl): BookRepository
}