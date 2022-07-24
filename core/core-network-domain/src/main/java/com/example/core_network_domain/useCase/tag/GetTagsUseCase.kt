package com.example.core_network_domain.useCase.tag

import com.example.core_model.api.tag.Tag
import com.example.core_network_domain.repository.TagRepository
import com.example.core_network_domain.response.BaseApiResponse
import com.example.core_network_domain.response.Result
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
):BaseApiResponse() {
    suspend operator fun invoke(page:Int):Result<Tag> {
        return safeApiCall { tagRepository.getTags(page = page) }
    }
}