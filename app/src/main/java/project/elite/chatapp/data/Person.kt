package project.elite.chatapp.data


import android.os.Parcel
import android.os.Parcelable
import project.elite.chatapp.R


data class Person(
    val id: Int = 0,
    val name: String? = "",
    val icon: Int = R.drawable.person_1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}

val personList = listOf(
    Person(
        1,
        "Pranav",
        R.drawable.person_1
    ),
    Person(
        2,
        "Ayesha",
        R.drawable.person_1
    ),
    Person(
        3,
        "Roshini",
        R.drawable.person_1
    ),
    Person(
        4,
        "Kaushik",
        R.drawable.person_1
    ),
    Person(
        5,
        "Dad",
        R.drawable.person_1
    ),
    Person(
        6,
        "Pranav",
        R.drawable.person_1
    ),
    Person(
        7,
        "Ayesha",
        R.drawable.person_1
    ),
    Person(
        8,
        "Roshini",
        R.drawable.person_1
    ),
    Person(
        9,
        "Kaushik",
        R.drawable.person_1
    ),
    Person(
        10,
        "Dad",
        R.drawable.person_1
    ),
)