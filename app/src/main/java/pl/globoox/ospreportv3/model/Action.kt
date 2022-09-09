package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.lang.reflect.Type
import java.util.*

@Parcelize
@Entity(tableName = "actions")
data class Action(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val outTime: String,
    val inTime: String,
    val location: String,
    val number: String,
    val description: String? = null,
    val carsInAction: List<CarInAction>
) : Parcelable