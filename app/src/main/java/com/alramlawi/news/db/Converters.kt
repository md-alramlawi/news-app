/*
Created By : Mohammed Alramlawi at July, 01, 2021
 */
package com.alramlawi.news.db

import androidx.room.TypeConverter
import com.alramlawi.news.models.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source{
        return Source(name, name)
    }
}