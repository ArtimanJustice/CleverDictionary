package com.artiuil.lab.cleverdictionary.domain.repository.words

sealed class LibraryError {
    object UnknownError : LibraryError()
    object NetworkError : LibraryError()
}

sealed class EditSetError {
    object InvalidSetId : EditSetError()
    object SetNotFound : EditSetError()
    object EditSetFailure : EditSetError()
}
