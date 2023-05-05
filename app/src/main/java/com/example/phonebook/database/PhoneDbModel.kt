package com.example.phonebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhoneDbModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "can_be_checked_off") val canBeCheckedOff: Boolean,
    @ColumnInfo(name = "is_checked_off") val isCheckedOff: Boolean,
    @ColumnInfo(name = "color_id") val colorId: Long,
    @ColumnInfo(name = "in_trash") val isInTrash: Boolean,
    @ColumnInfo(name = "tag_id") val tagId: Long,
) {
    companion object {
        val DEFAULT_PHONES = listOf(
            PhoneDbModel(1, "Adam Smith", "0967845578", false, false, 1, false,1),
            PhoneDbModel(2, "Heal Me", "0847544412", false, false, 2, false,1),
            PhoneDbModel(3, "Pika Juu", "0899745567", false, false, 3, false,1),
            PhoneDbModel(4, "Black Ryu", "0856794423", false, false, 4, false,1),
            PhoneDbModel(5, "Teddy Bird", "0957844562", false, false, 5, false,1),
            PhoneDbModel(6, "Luff Fy", "0891456879", false, false, 6, false,1),
            PhoneDbModel(7, "Jo Jo", "0866321547", false, false, 7, false,1),
            PhoneDbModel(8, "Ric Nickel", "0867942315", false, false, 8, false,1),
            PhoneDbModel(9, "King Aor Je Ran", "0245878962", false, false, 9, false,1),
            PhoneDbModel(10, "Mix Ue", "0615487995", false, false, 10, false,1),
            PhoneDbModel(11, "John Ray", "0248796321", true, false, 11, false,1),
            PhoneDbModel(12, "Tim Black", "0954788756", true, false, 12, false,1)
        )
    }
}
