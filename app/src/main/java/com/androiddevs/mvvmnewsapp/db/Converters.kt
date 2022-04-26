package com.androiddevs.mvvmnewsapp.db

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name

    }
    @TypeConverter
    fun outSource(name: String):Source{
        return Source(name, name)

    }

}