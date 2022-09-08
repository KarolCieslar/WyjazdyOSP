package pl.globoox.ospreportv3.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import pl.globoox.ospreportv3.MainActivity
import pl.globoox.ospreportv3.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


fun EditText.checkIsNullAndSetError(errorText: String) : Boolean {
    if (this.text.isNullOrBlank()) {
        this.error = errorText
        return true
    }
    return false
}

fun Fragment.showSnackBar(text: String) {
    (activity as MainActivity?)!!.showSnackBar(text)
}

fun convertToLocalDateTime(text: String) : LocalDateTime {
    val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
    return LocalDateTime.parse(text, dateFormatterHelper)
}

fun durationFormatter(outDate: LocalDateTime, inDate: LocalDateTime): String {

    var tempDateTime = LocalDateTime.from(outDate)

    val hours = tempDateTime.until(inDate, ChronoUnit.HOURS)
    tempDateTime = tempDateTime.plusHours(hours)
    val minutes = tempDateTime.until(inDate, ChronoUnit.MINUTES)

    return "${hours}g ${minutes}m"
}

fun buildSpannableText(context: Context, stringResource: Int, text: String): SpannableString {
    val string = context.resources.getString(stringResource)
    val s = SpannableString("$string $text")
    s.setSpan(
        ForegroundColorSpan(ResourcesCompat.getColor(context.resources, R.color.black, null)),
        0, string.length,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )
    return s
}

fun buildSpannableTextWithoutColor(context: Context, stringResource: Int, text: String): SpannableString {
    val string = context.resources.getString(stringResource)
    return SpannableString("$string$text")
}