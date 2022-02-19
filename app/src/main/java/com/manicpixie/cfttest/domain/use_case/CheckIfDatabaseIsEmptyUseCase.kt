package com.manicpixie.cfttest.domain.use_case


import com.manicpixie.cfttest.domain.repository.CFTRepository
import javax.inject.Inject

class CheckIfDatabaseIsEmptyUseCase @Inject constructor(
    private val repository: CFTRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.numberOfEntries() == 0
    }
}