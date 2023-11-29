package com.example.tugasfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasfirebase.databinding.ActivityAddNoteBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val description = binding.edtDesc.text.toString()

            val note = Note(title = title, description = description)
            // Add the Note object to the "notes" collection in Firebase
            db.collection("notes")
                .add(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    // Handle failure
                    Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
