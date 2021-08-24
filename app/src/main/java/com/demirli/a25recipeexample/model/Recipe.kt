package com.demirli.a25recipeexample.model

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    var title: String?,
    var photoUriForPicasso: String?,
    var bitMapUri: Uri?,
    var ingredients: String?,
    var instructions: String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(photoUriForPicasso)
        parcel.writeParcelable(bitMapUri, flags)
        parcel.writeString(ingredients)
        parcel.writeString(instructions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}
