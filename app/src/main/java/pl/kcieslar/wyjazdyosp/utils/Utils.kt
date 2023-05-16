package pl.kcieslar.wyjazdyosp.utils

import android.content.Context
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

import pl.kcieslar.wyjazdyosp.R
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import java.util.*

fun getForcesString(context: Context, stringType: ForcesStringType, forcesDataType: ForcesDataType, textVariable: String? = null) : String {
    val stringRes = when (stringType) {
        ForcesStringType.EMPTY_VIEW_MAIN -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_fragment_empty_view_main
                ForcesDataType.FIREMAN -> R.string.fireman_fragment_empty_view_main
                ForcesDataType.EQUIPMENT -> R.string.equipment_fragment_empty_view_main
            }
        }
        ForcesStringType.EMPTY_VIEW_DESCRIPTION -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_fragment_empty_view_description
                ForcesDataType.FIREMAN -> R.string.fireman_fragment_empty_view_description
                ForcesDataType.EQUIPMENT -> R.string.equipment_fragment_empty_view_description
            }
        }
        ForcesStringType.EMPTY_VIEW_BUTTON -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_fragment_empty_view_button
                ForcesDataType.FIREMAN -> R.string.fireman_fragment_empty_view_button
                ForcesDataType.EQUIPMENT -> R.string.equipment_fragment_empty_view_button
            }
        }
        ForcesStringType.ADD_DIALOG_TITLE -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_fragment_add_dialog_title
                ForcesDataType.FIREMAN -> R.string.fireman_fragment_add_dialog_title
                ForcesDataType.EQUIPMENT -> R.string.equipment_fragment_add_dialog_title
            }
        }
        ForcesStringType.REMOVE_FIRCES_DIALOG_DESCRIPTION -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_fragment_remove_dialog_description
                ForcesDataType.FIREMAN -> R.string.fireman_fragment_remove_dialog_description
                ForcesDataType.EQUIPMENT -> R.string.equipment_fragment_remove_dialog_description
            }
        }
        ForcesStringType.EDIT_DIALOG_TITLE -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_fragment_edit_dialog_title
                ForcesDataType.FIREMAN -> R.string.fireman_fragment_edit_dialog_title
                ForcesDataType.EQUIPMENT -> R.string.equipment_fragment_edit_dialog_title
            }
        }
        ForcesStringType.OBJECT_ALREADY_EXIST -> {
            when (forcesDataType) {
                ForcesDataType.CAR -> R.string.car_object_exist
                ForcesDataType.FIREMAN -> R.string.fireman_object_exist
                ForcesDataType.EQUIPMENT -> R.string.equipment_object_exist
            }
        }
    }
    textVariable?.let {
        return context.resources.getString(stringRes, it)
    }
    return context.resources.getString(stringRes)
}

enum class ForcesStringType {
    EMPTY_VIEW_MAIN, EMPTY_VIEW_DESCRIPTION, EMPTY_VIEW_BUTTON, ADD_DIALOG_TITLE, REMOVE_FIRCES_DIALOG_DESCRIPTION, EDIT_DIALOG_TITLE, OBJECT_ALREADY_EXIST;
}