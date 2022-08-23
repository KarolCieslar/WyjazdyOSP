package pl.globoox.ospreportv3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fireman_table")
data class Fireman(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String?,
    val lastName: String?
)