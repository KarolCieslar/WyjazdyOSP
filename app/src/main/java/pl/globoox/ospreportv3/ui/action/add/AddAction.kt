package pl.globoox.ospreportv3.ui.action.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import pl.globoox.ospreportv3.R
import pl.globoox.ospreportv3.databinding.FragmentAddActionBinding
import pl.globoox.ospreportv3.viewmodel.AddActionViewModel


class AddAction {

    data class Item(val id: Long, val item: Any, val itemType: ItemType) {

    }

    enum class ItemType {
        CAR, FIREMAN, EQUIPMENT, SEPARATOR
    }
}