package com.example.tugasfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasfirebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private val notesList = mutableListOf<Note>()

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Note"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NoteAdapter(notesList) { note -> onNoteClicked(note) }
        binding.rvNoteList.adapter = adapter
        binding.rvNoteList.layoutManager = LinearLayoutManager(this)

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        loadNotes()
    }

    private fun loadNotes() {
        db.collection("notes")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    // Handle error
                    Log.d("MainActivity", "error listening for note",
                        error)
                    return@addSnapshotListener
                }

                notesList.clear()
                value?.documents?.forEach {
                    val note = it.toObject(Note::class.java)
                    if (note != null) {
                        note.id = it.id
                        notesList.add(note)
                    }
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun onNoteClicked(note: Note) {
        val intent = Intent(this, UpdateNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }
}
