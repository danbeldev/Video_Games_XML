package com.madproject.core_network_domain.useCase.tag

import com.madproject.core_model.api.tag.Tag
import com.madproject.core_network_domain.repository.TagRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
):BaseApiResponse() {
    suspend operator fun invoke(page:Int):Result<Tag> {
        return safeApiCall { tagRepository.getTags(page = page) }
    }
}