package com.antonprokopov.albumsfeed.usecase

import com.antonprokopov.albumsfeed.data.api.ApiService
import com.antonprokopov.albumsfeed.data.models.AlbumDto
import com.antonprokopov.albumsfeed.data.models.PhotoDto
import com.antonprokopov.albumsfeed.data.models.UserDto
import com.antonprokopov.core.data.Resource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumsUseCaseTest {

    companion object {
        private const val EXPECTED_NUMBER_OF_FLOW_EMISSIONS = 2
        private const val EXPECTED_NUMBER_OF_RESULT_ELEMENTS = 5
    }

    @Mock
    lateinit var apiService: ApiService

    @Before
    fun initMockCalls() {
        runBlocking {
            Mockito.`when`(apiService.getAlbums()).thenReturn(getAlbumsTestData())
            Mockito.`when`(apiService.getPhotos()).thenReturn(getPhotosTestData())
            Mockito.`when`(apiService.getUsers()).thenReturn(getUsersTestData())
        }
    }

    @Test
    fun execute() {
        val useCase = AlbumsUseCase(apiService)

        runBlocking {
            val resultList = useCase.execute().toList()

            assertTrue(resultList.size == EXPECTED_NUMBER_OF_FLOW_EMISSIONS)

            assertTrue(resultList[0] is Resource.Loading)
            assertTrue(resultList[1] is Resource.Success)

            assertTrue(resultList[1].data?.size == EXPECTED_NUMBER_OF_RESULT_ELEMENTS)
        }
    }

    private fun getAlbumsTestData(): List<AlbumDto> = mutableListOf<AlbumDto>().apply {
        add(AlbumDto(userId = 1, id = 1, title = "quidem molestiae enim"))
        add(AlbumDto(userId = 1, id = 2, title = "sunt qui excepturi placeat culpa"))
        add(AlbumDto(userId = 2, id = 11, title = "quam nostrum impedit mollitia quod et dolor"))
        add(AlbumDto(userId = 2, id = 12, title = "consequatur autem doloribus natus consectetur"))
        add(AlbumDto(userId = 4, id = 1, title = "cumque voluptatibus rerum architecto blanditiis"))
    }

    private fun getUsersTestData(): List<UserDto> = mutableListOf<UserDto>().apply {
        add(UserDto(id = 1, username = "Bret"))
        add(UserDto(id = 2, username = "Antonette"))
        add(UserDto(id = 4, username = "Karianne"))
    }

    private fun getPhotosTestData(): List<PhotoDto> = mutableListOf<PhotoDto>().apply {
        add(PhotoDto(albumId = 1, id = 1, thumbnailUrl = "url_1"))
        add(PhotoDto(albumId = 1, id = 2, thumbnailUrl = "url_2"))
        add(PhotoDto(albumId = 1, id = 3, thumbnailUrl = "url_3"))
        add(PhotoDto(albumId = 2, id = 4, thumbnailUrl = "url_4"))
        add(PhotoDto(albumId = 2, id = 5, thumbnailUrl = "url_5"))
        add(PhotoDto(albumId = 3, id = 7, thumbnailUrl = "url_6"))
    }
}