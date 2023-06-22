package uz.gita.dima.kitapxanam.data.repositoryimpl

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import uz.gita.dima.kitapxanam.data.model.BookData
import uz.gita.dima.kitapxanam.domain.repository.SavedBooksRepository
import java.io.File
import javax.inject.Inject

class SavedBooksRepositoryImpl @Inject constructor() : SavedBooksRepository {
    private val db: FirebaseFirestore = Firebase.firestore

    override  fun getSavedBooks(context: Context): Flow<Result<List<BookData>>> = flow {
        emit(getList(context))
    }

    private suspend fun getList(context: Context): Result<List<BookData>> {
        try {
            val list = arrayListOf<BookData>()
            val firstCollection = db.collection("MEKTEP").get().await()
            firstCollection.forEach { firstCollectionData ->

                val subCollection = firstCollectionData.reference.collection("sabaqliqlar")
                    .get()
                    .await()

                subCollection.forEach { subCollectionData ->

                    Log.d("TTT","Item -> ${subCollectionData.data}")

                    val book = File(context.filesDir, subCollectionData.get("reference").toString() + ".pdf")
                    Log.d("RRR", "File book -> $book")
                    if (book.exists()) {
                        val temp = subCollectionData.toObject(BookData::class.java)
                        list.add(temp)
                    }
                }
            }
            return Result.success(list)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}