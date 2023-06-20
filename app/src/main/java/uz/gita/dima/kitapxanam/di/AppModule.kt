package uz.gita.dima.kitapxanam.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.dima.kitapxanam.data.local.BookDatabase
import uz.gita.dima.kitapxanam.data.local.dao.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFireStorage(): StorageReference = Firebase.storage.reference

    @Provides
    @Singleton
    fun provideFireDataBase(): FirebaseFirestore = Firebase.firestore

    private const val NAME_DATABASE = "book_list1.db"
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): BookDatabase {
        return Room.databaseBuilder(
            app,
            BookDatabase::class.java,
            NAME_DATABASE
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(db:BookDatabase): BookDao = db.getBookDao()
}