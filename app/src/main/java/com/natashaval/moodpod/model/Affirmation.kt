package com.natashaval.moodpod.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by natasha.santoso on 14/01/21.
 */
@Parcelize
data class Affirmation(
    @SerializedName("affirmation")
    val affirmation: String
): Parcelable