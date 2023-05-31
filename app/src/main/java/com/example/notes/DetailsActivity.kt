package com.example.notes

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.databinding.ActivityDetailsBinding
import java.lang.Exception

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var database : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        database = this.openOrCreateDatabase("Notes", MODE_PRIVATE, null)

        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")) {
            binding.titleText.setText("")
            binding.descriptionText.setText("")
            binding.button.visibility = View.VISIBLE

        } else {
            binding.button.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id", 1)

            val cursor = database.rawQuery("SELECT * FROM notes WHERE id = ?", arrayOf(selectedId.toString()))

            val titleIx = cursor.getColumnIndex("title")
            val descriptionIx = cursor.getColumnIndex("description")

            while (cursor.moveToNext()) {
                binding.titleText.setText(cursor.getString(titleIx))
                binding.descriptionText.setText((cursor.getString(descriptionIx)))
            }
            cursor.close()
        }
    }

    fun saveButtonClicked(view : View) {
        val title = binding.titleText.text.toString()
        val description = binding.descriptionText.text.toString()

        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS notes (id INTEGER PRIMARY KEY, title VARCHAR, description VARCHAR)")
            val sqlString = "INSERT INTO notes (title, description) VALUES (?, ?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1, title)
            statement.bindString(2, description)
            statement.execute()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val intent = Intent(this@DetailsActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}