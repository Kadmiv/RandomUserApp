package com.kadmiv.random_user_app.repo.api.models.user.response

import android.os.Parcel
import android.os.Parcelable
import com.kadmiv.random_user_app.base.interfaces.IModel

class User() : IModel, Parcelable {
    var lastName: String = ""
    var firstName: String = ""
    var age: Int = 0
    var large: String = ""
    var medium: String = ""
    var thumbnail: String = ""
    var cell: String = ""
    var phone: String = ""
    var email: String = ""

    constructor(parcel: Parcel) : this() {
        lastName = parcel.readString()!!
        firstName = parcel.readString()!!
        age = parcel.readInt()
        large = parcel.readString()!!
        medium = parcel.readString()!!
        thumbnail = parcel.readString()!!
        cell = parcel.readString()!!
        phone = parcel.readString()!!
        email = parcel.readString()!!
    }

    override fun generateModelId(): Long {
        return System.currentTimeMillis()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lastName)
        parcel.writeString(firstName)
        parcel.writeInt(age)
        parcel.writeString(large)
        parcel.writeString(medium)
        parcel.writeString(thumbnail)
        parcel.writeString(cell)
        parcel.writeString(phone)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}