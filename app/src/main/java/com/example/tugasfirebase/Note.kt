package com.example.tugasfirebase

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class Note(
    @set: Exclude @get:Exclude var id: String = "",
    var title : String = "",
    var description : String = ""
) : Serializable
