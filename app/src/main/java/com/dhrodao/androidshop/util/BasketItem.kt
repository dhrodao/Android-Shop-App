package com.dhrodao.androidshop.util

import android.os.Parcel
import android.os.Parcelable

// Needs to inherit from parcelable in order to persist views when screen changes
data class BasketItem(val item: String?, val icon: Int, val quantity: Int, val pricePerItem: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(item)
        parcel.writeInt(icon)
        parcel.writeInt(quantity)
        parcel.writeDouble(pricePerItem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BasketItem> {
        override fun createFromParcel(parcel: Parcel): BasketItem {
            return BasketItem(parcel)
        }

        override fun newArray(size: Int): Array<BasketItem?> {
            return arrayOfNulls(size)
        }
    }
}