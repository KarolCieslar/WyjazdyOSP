package pl.kcieslar.wyjazdyosp.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import pl.kcieslar.wyjazdyosp.MainActivity
import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.views.HelpDialogStringRes
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
    if (activity is MainActivity) {
        (activity as MainActivity).showSnackBar(text)
    }
}

fun Fragment.setHelpDialogString(helpDialogStringRes: HelpDialogStringRes) {
    if (activity is MainActivity) {
        (activity as MainActivity).setHelpDialogString(helpDialogStringRes)
    }
}

fun convertStringToLocalDateTime(stringDate: String) : LocalDateTime {
    return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT))
}

fun durationFormatter(outDate: String, inDate: String): String {
    val outDateLocal = convertStringToLocalDateTime(outDate)
    val inDateLocal = convertStringToLocalDateTime(inDate)
    var tempDateTime = LocalDateTime.from(outDateLocal)

    val hours = tempDateTime.until(inDateLocal, ChronoUnit.HOURS)
    tempDateTime = tempDateTime.plusHours(hours)
    val minutes = tempDateTime.until(inDateLocal, ChronoUnit.MINUTES)

    return "${hours}g ${minutes}m"
}

fun calculateActionHours(outDate: String, inDate: String): Long {
    val outDateLocal = convertStringToLocalDateTime(outDate)
    val inDateLocal = convertStringToLocalDateTime(inDate)
    var tempDateTime = LocalDateTime.from(outDateLocal)

    var hours = tempDateTime.until(inDateLocal, ChronoUnit.HOURS)
    tempDateTime = tempDateTime.plusHours(hours)
    val minutes = tempDateTime.until(inDateLocal, ChronoUnit.MINUTES)
    if (minutes > 0) hours++
    return hours
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

fun View.setHorizontalMargin(margin: Int) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(resources.getDimensionPixelSize(margin), this.topMargin, resources.getDimensionPixelSize(margin), this.bottomMargin)
    }
}

fun <T> mergeList(first: List<T>, second: List<T>): List<T> {
    val list: MutableList<T> = ArrayList(first)
    list.addAll(second)
    return list
}