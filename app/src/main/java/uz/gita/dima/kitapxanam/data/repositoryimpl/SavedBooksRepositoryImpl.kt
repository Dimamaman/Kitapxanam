package uz.gita.dima.kitapxanam.data.repositoryimpl

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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
                    val book = File(context.filesDir,  subCollectionData.get("reference") as String + ".pdf")
                    if (book.exists()) {
                        Log.d("TTT", "File book -> TRUE")
                        val temp = BookData(
                            date = subCollectionData.get("date") as String,
                            description = subCollectionData.get("description") as String,
                            id = subCollectionData.get("id") as Long,
                            imageUrl = subCollectionData.get("imageUrl") as String,
                            isActive = subCollectionData.get("isActive") as Boolean,
                            klass = subCollectionData.get("klass") as String,
                            name = subCollectionData.get("name") as String,
                            pdfUrl = subCollectionData.get("pdfUrl") as String,
                            reference = subCollectionData.get("reference") as String,
                            year = subCollectionData.get("year") as String
                        )
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