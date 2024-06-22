package com.example.tema2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var editTextAnimalName: EditText
    private lateinit var editTextContinent: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerViewAnimals: RecyclerView
    private lateinit var adapter: AnimalAdapter
    private lateinit var animalList: MutableList<Animal>
    private lateinit var db: AnimalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextAnimalName = findViewById(R.id.nameOfAnAnimal)
        editTextContinent = findViewById(R.id.continent)
        buttonAdd = findViewById(R.id.add)
        recyclerViewAnimals = findViewById(R.id.listOfAnimals)

        db = Room.databaseBuilder(applicationContext, AnimalDatabase::class.java, "animal-database").build()

        GlobalScope.launch {
            animalList = db.animalDao().getAllAnimals().toMutableList()

            runOnUiThread {
                recyclerViewAnimals.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = AnimalAdapter(animalList) { animal ->
                    GlobalScope.launch {
                        db.animalDao().delete(animal)
                        runOnUiThread {
                            animalList.remove(animal)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                recyclerViewAnimals.adapter = adapter
            }
        }

        buttonAdd.setOnClickListener {
            val name = editTextAnimalName.text.toString().trim()
            val continent = editTextContinent.text.toString().trim()

            if (name.isEmpty() || continent.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch {
                var animal = animalList.find { it.name.equals(name, ignoreCase = true) }
                if (animal != null) {
                    animal.continent = continent
                    db.animalDao().update(animal)
                } else {
                    animal = Animal(name = name, continent = continent)
                    db.animalDao().insert(animal)
                    animalList.add(animal)
                }

                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
