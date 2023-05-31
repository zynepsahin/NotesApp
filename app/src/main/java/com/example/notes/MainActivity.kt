package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var notesList : ArrayList<Notes>
    private lateinit var notesAdapter : NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        notesList = ArrayList<Notes>()
        notesAdapter = NotesAdapter(notesList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = notesAdapter

        try {
            val database = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM notes", null)
            val titleNameIx = cursor.getColumnIndex("title")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()) {
                val title = cursor.getString(titleNameIx)
                val id = cursor.getInt(idIx)
                val notes = Notes(title, id)
                notesList.add(notes)
            }

            notesAdapter.notifyDataSetChanged()
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("main")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.new_note_item) {
            val intent = Intent(this@MainActivity, DetailsActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
            return super.onOptionsItemSelected(item)
    }
}