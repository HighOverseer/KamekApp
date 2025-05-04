package com.neotelemetrixgdscunand.kamekapp.data

import com.neotelemetrixgdscunand.kamekapp.data.remote.ApiService
import com.neotelemetrixgdscunand.kamekapp.data.utils.fetchFromNetwork
import com.neotelemetrixgdscunand.kamekapp.domain.common.DataError
import com.neotelemetrixgdscunand.kamekapp.domain.data.NewsRepository
import com.neotelemetrixgdscunand.kamekapp.domain.model.NewsItem
import com.neotelemetrixgdscunand.kamekapp.domain.model.NewsType
import javax.inject.Inject
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.common.RootNetworkError
import com.neotelemetrixgdscunand.kamekapp.domain.model.NewsDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper:DataMapper
):NewsRepository {

    override suspend fun getNewsItems(query:String, newsType: NewsType): Result<List<NewsItem>, DataError.NetworkError> {
        return fetchFromNetwork {
            val response = apiService.getNewsItems(query, newsType.id)
            coroutineContext.ensureActive()
            val newsItemsDto = response.data ?: emptyList()

            val newsItem = newsItemsDto.mapNotNull {
                mapper.mapNewsItemToDomain(it)
            }

            coroutineContext.ensureActive()

            Result.Success(newsItem)
        }
    }

    override suspend fun getNewsItemsPreviews(newsType: NewsType): Result<List<NewsItem>, DataError.NetworkError> {
        return when(val result = getNewsItems(newsType = newsType)){
            is Result.Success -> Result.Success(result.data.take(5))
            is Result.Error -> result
        }
    }

    override suspend fun getNewsById(newsId: Int, newsType: NewsType): Result<NewsDetails, DataError.NetworkError> {
        return fetchFromNetwork {
            val response = apiService.getNewsById(newsId, newsType.id)
            coroutineContext.ensureActive()
            val newsDetailsDto = response.data ?: return@fetchFromNetwork Result.Error(RootNetworkError.UNEXPECTED_ERROR)
            val newsDetails = withContext(Dispatchers.Default){
                mapper.mapNewsDetailsToDomain(newsDetailsDto)
            }  ?: return@fetchFromNetwork Result.Error(RootNetworkError.UNEXPECTED_ERROR)

            coroutineContext.ensureActive()

            return@fetchFromNetwork Result.Success(newsDetails)
        }
    }
}