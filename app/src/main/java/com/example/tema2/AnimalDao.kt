package com.example.tema2

import androidx.room.*

@Dao
interface AnimalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(animal: Animal)

    @Query("SELECT * FROM animals")
    fun getAllAnimals(): List<Animal>

    @Update
    fun update(animal: Animal)

    @Delete
    fun delete(animal: Animal)
}
