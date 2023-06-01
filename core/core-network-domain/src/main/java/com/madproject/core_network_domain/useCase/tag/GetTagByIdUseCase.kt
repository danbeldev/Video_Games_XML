package com.madproject.core_network_domain.useCase.tag

import com.madproject.core_model.api.tag.TagInfo
import com.madproject.core_network_domain.repository.TagRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetTagByIdUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    operator fun invoke(id:Int):Single<TagInfo> = tagRepository.getTagById(id = id)
}