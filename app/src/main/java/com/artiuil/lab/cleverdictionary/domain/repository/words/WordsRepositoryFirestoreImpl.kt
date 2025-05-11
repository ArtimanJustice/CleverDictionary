package com.artiuil.lab.cleverdictionary.domain.repository.words

import com.artiuil.lab.cleverdictionary.domain.dto.words.WordsSetDto
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordEntity
import com.artiuil.lab.cleverdictionary.domain.entity.words.WordsSetEntity
import com.artiuil.lab.cleverdictionary.domain.mappers.toDomain
import com.artiuil.lab.cleverdictionary.domain.mappers.toDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordsRepositoryFirestoreImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
) : WordsRepository {

    companion object {
        const val USERS_COLLECTION = "users"
        const val SETS_COLLECTION = "sets"
        const val ID_FIELD = "id"
        const val WORDS_FIELD = "words"
    }

    private fun getUserSetsCollection(): CollectionReference {
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        return firestore.collection(USERS_COLLECTION)
            .document(uid)
            .collection(SETS_COLLECTION)
    }

    override suspend fun getSet(id: String): WordsSetEntity? {
        return try {
            val dto = getUserSetsCollection()
                .document(id)
                .get()
                .await()
                .toObject(WordsSetDto::class.java)
            dto?.toDomain()
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getAllSets(): List<WordsSetEntity> {
        return try {
            getUserSetsCollection()
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(WordsSetDto::class.java)?.toDomain() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteSet(id: String) {
        getUserSetsCollection()
            .document(id)
            .delete()
            .await()
    }

    override suspend fun createSet(wordsSet: WordsSetEntity) {
        val currentTime = System.currentTimeMillis()
        val setToSave = if (wordsSet.createdAt == 0L) {
            wordsSet.copy(createdAt = currentTime, lastOpenedAt = currentTime)
        } else {
            wordsSet
        }
        val dto = setToSave.toDto()
        val documentRef = getUserSetsCollection().add(dto).await()
        val generatedId = documentRef.id
        getUserSetsCollection().document(generatedId)
            .update(ID_FIELD, generatedId)
            .await()
    }

    override suspend fun addWordToSet(setId: Int, word: WordEntity) {
        val wordDto = word.toDto()
        getUserSetsCollection()
            .document(setId.toString())
            .update(WORDS_FIELD, FieldValue.arrayUnion(wordDto))
            .await()
    }

    override suspend fun editSet(wordsSet: WordsSetEntity) {
        val dto = wordsSet.toDto()
        getUserSetsCollection()
            .document(wordsSet.id)
            .set(dto, SetOptions.merge())
            .await()
    }

    override fun observeAllSets(): Flow<List<WordsSetEntity>> = callbackFlow {
        val registration = getUserSetsCollection().addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
            } else if (snapshot != null) {
                val sets = snapshot.documents.mapNotNull {
                    it.toObject(WordsSetDto::class.java)?.toDomain()
                }
                trySend(sets).isSuccess
            }
        }
        awaitClose { registration.remove() }
    }
}