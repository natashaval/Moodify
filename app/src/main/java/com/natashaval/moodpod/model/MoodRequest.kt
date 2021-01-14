package com.natashaval.moodpod.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by natasha.santoso on 13/01/21.
 */
@Parcelize
data class MoodRequest (
    var mood: String,
    var message: String,
    var date: Date
): Parcelable