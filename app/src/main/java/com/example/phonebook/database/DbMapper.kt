package com.example.phonebook.database

import com.example.phonebook.domain.model.ColorModel
import com.example.phonebook.domain.model.NEW_PHONE_ID
import com.example.phonebook.domain.model.PhoneModel
import com.example.phonebook.domain.model.TagModel

class DbMapper {
    // Create list of NoteModels by pairing each note with a color
    fun mapNotes(
        phoneDbModels: List<PhoneDbModel>,
        colorDbModels: Map<Long, ColorDbModel>,
        tagDbModels: Map<Long, TagDbModel>
    ): List<PhoneModel> = phoneDbModels.map {
        val colorDbModel = colorDbModels[it.colorId]
            ?: throw RuntimeException("Color for colorId: ${it.colorId} was not found. Make sure that all colors are passed to this method")
        val tagDbModel = tagDbModels[it.tagId]
            ?: throw RuntimeException("Tag for tagId: ${it.tagId} was not found. Make sure that all tags are passed to this method")
        mapNote(it, colorDbModel, tagDbModel)
    }

    // convert NoteDbModel to NoteModel
    fun mapNote(phoneDbModel: PhoneDbModel, colorDbModel: ColorDbModel, tagDbModel: TagDbModel): PhoneModel {
        val color = mapColor(colorDbModel)
        val tag = mapTag(tagDbModel)
        val isCheckedOff = with(phoneDbModel) { if (canBeCheckedOff) isCheckedOff else null }
        return with(phoneDbModel) { PhoneModel(id, title, content, isCheckedOff, color, tag) }
    }

    // convert list of ColorDdModels to list of ColorModels
    fun mapColors(colorDbModels: List<ColorDbModel>): List<ColorModel> =
        colorDbModels.map { mapColor(it) }


    // convert ColorDbModel to ColorModel
    fun mapColor(colorDbModel: ColorDbModel): ColorModel =
        with(colorDbModel) { ColorModel(id, name, hex) }

    fun mapTags(tagDbModels: List<TagDbModel>): List<TagModel> =
        tagDbModels.map { mapTag(it) }
    fun mapTag(tagDbModel: TagDbModel): TagModel =
        with(tagDbModel) { TagModel(id, name) }

    // convert NoteModel back to NoteDbModel
    fun mapDbNote(note: PhoneModel): PhoneDbModel =
        with(note) {
            val canBeCheckedOff = isCheckedOff != null
            val isCheckedOff = isCheckedOff ?: false
            if (id == NEW_PHONE_ID)
                PhoneDbModel(
                    title = title,
                    content = content,
                    canBeCheckedOff = canBeCheckedOff,
                    isCheckedOff = isCheckedOff,
                    colorId = color.id,
                    isInTrash = false,
                    tagId = tag.id
                )
            else
                PhoneDbModel(id, title, content, canBeCheckedOff, isCheckedOff, color.id, false, tag.id)
        }
}