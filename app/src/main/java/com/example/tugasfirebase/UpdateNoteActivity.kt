package com.example.tugasfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasfirebase.databinding.ActivityUpdateNoteBinding
import com.google.firebase.firestore.FirebaseFirestore

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        note = intent.getSerializableExtra("note") as Note

        binding.edtTitle.setText(note.title)
        binding.edtDesc.setText(note.description)

        binding.btnSave.setOnClickListener {
            // Get the new title and description from the EditText fields
            val newTitle = binding.edtTitle.text.toString()
            val newDescription = binding.edtDesc.text.toString()

            // Update the note object with the new title and description
            note.title = newTitle
            note.description = newDescription

            // Update the note in the "notes" collection in Firebase
            db.collection("notes")
                .document(note.id)
                .set(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete This Note?")
        builder.setMessage("Are you sure you want to delete this note?")

        builder.setPositiveButton("Yes") { _, _ ->
            deleteNote()
        }

        builder.setNegativeButton("No") { _, _ ->

        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteNote() {
        // Delete the note from the "notes" collection in Firebase
        db.collection("notes")
            .document(note.id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                // Handle failure
                Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show()
            }
    }
}
