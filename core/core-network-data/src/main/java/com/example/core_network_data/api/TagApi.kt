package com.example.core_network_data.api

import com.example.core_model.api.tag.Tag
import com.example.core_model.api.tag.TagInfo
import com.example.core_network_data.common.ConstantsUrl.RAWQ_KEY
import com.example.core_network_data.common.ConstantsUrl.TAGS_URL
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TagApi {

    /**
     * @param page A page number within the paginated result set.
     * @param pageSize Number of results to return per page.
     */
    @GET(TAGS_URL)
    suspend fun getTags(
        @Query("key") key:String = RAWQ_KEY,
        @Query("page") page:Int,
        @Query("page_size") pageSize:Int = 20
    ): Response<Tag>

    @GET("$TAGS_URL/{id}")
    fun getTagById(
        @Path("id") id:Int,
        @Query("key") key:String = RAWQ_KEY
    ):Single<TagInfo>
}