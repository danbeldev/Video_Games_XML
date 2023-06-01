package com.madproject.core_database_data.database.room.userDatabase.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.madproject.core_database_data.database.room.userDatabase.UserDatabase
import com.madproject.core_database_data.database.utils.createFavoriteVideoGameDTO
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoriteVideoGameDAOTest:TestCase() {

    private lateinit var dao:FavoriteVideoGameDAO
    private lateinit var db:UserDatabase

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            context,
            UserDatabase::class.java
        ).build()

        dao = db.favoriteVideoGameDAO()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun upsertFavoriteVideoGameItem() = runTest {
        val videoGame = createFavoriteVideoGameDTO()

        dao.upsertItem(videoGame)
        val result = dao.getItem(videoGame.id)

        Assert.assertEquals(videoGame.id,result?.id)
    }

    @Test
    fun getFavoriteVideoGameItems() = runTest {
        val videoGame = createFavoriteVideoGameDTO()

        dao.upsertItem(videoGame)
        dao.getItems().onEach { videoGames ->
            Assert.assertTrue(videoGames.any { item -> item.id == videoGame.id })
        }
    }

    @Test
    fun getFavoriteVideoGameItemsSearch() = runTest {
        val search = "test_video_game"
        val videoGame= createFavoriteVideoGameDTO()

        dao.upsertItem(videoGame)
        dao.getItems(search = search).onEach { videoGames ->
            Assert.assertTrue(videoGames.any { item -> item.name.contains(search) })
        }
    }

    @Test
    fun deleteFavoriteVideoGameItem() = runTest {
        val videoGame = createFavoriteVideoGameDTO()

        dao.upsertItem(videoGame)
        val resultUpsertVideoGameItem = dao.getItem(id = videoGame.id)
        dao.deleteItem(id = videoGame.id)
        val resultDeleteVideoGameItem = dao.getItem(id = videoGame.id)

        Assert.assertEquals(resultUpsertVideoGameItem?.id,videoGame.id)
        Assert.assertNull(resultDeleteVideoGameItem)
    }

    @Test
    fun getCountFavoriteVideoGame() = runTest {

        val videoGame = createFavoriteVideoGameDTO()

        dao.upsertItem(videoGame)
        dao.upsertItem(videoGame)

        dao.getCount().onEach { count ->
            Assert.assertEquals(2,count)
        }
    }

    @Test
    fun clearFavoriteVideoGame() = runTest {
        val videoGame = createFavoriteVideoGameDTO()

        dao.upsertItem(videoGame)
        dao.upsertItem(videoGame)

        dao.clear()

        dao.getItems().onEach { videoGames ->
            Assert.assertTrue(videoGames.isEmpty())
        }
    }
}