package com.example.edutrack


import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    var name: String? = "",
    var email: String? = "",
    var type:String?=""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "Name" to name,
            "Email" to email,
            "Type" to type
        )
    }
}
