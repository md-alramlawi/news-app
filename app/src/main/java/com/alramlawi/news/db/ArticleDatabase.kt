/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.db

import android.content.Context
import androidx.room.*
import com.alramlawi.news.models.Article

@TypeConverters(Converters::class)
@Database(entities = [Article::class], version = 4)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}