package com.myarulin.newstesttask.db

import androidx.room.TypeConverter

class Convertors {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}