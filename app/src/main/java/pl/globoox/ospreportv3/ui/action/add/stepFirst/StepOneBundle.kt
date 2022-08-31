package pl.globoox.ospreportv3.ui.action.add.stepFirst

import java.io.Serializable

data class StepOneBundle(
    var outDate: String? = "",
    var outTime: String? = "",
    var inDate: String? = "",
    var inTime: String? = "",
    var location: String? = "",
    var reportNumber: String? = "",
    var description: String? = ""
) : Serializable