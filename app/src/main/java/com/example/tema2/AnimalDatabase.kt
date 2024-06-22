package com.example.tema2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Animal::class], version = 1)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
}
