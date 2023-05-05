package com.example.phonebook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.phonebook.domain.model.PhoneModel
import com.example.phonebook.util.fromHex

@ExperimentalMaterialApi
@Composable
fun Phone(
    modifier: Modifier = Modifier,
    contact: PhoneModel,
    onPhoneClick: (PhoneModel) -> Unit = {},
    onPhoneCheckedChange: (PhoneModel) -> Unit = {},
    isSelected: Boolean
) {
    val background = if (isSelected)
        Color.LightGray
    else
        MaterialTheme.colors.surface

    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = background
    ) {

        ListItem(
            text = { Text(text = contact.title, maxLines = 1) },
            secondaryText = {
                Text(text = contact.content + " Tag: " + contact.tag.name, maxLines = 1)
            },
            icon = {
                PhoneColor(
                    color = Color.fromHex(contact.color.hex),
                    size = 40.dp,
                    border = 1.dp
                )
            },
            trailing = {
                if (contact.isCheckedOff != null) {
                    Checkbox(
                        checked = contact.isCheckedOff,
                        onCheckedChange = { isChecked ->
                            val newPhone = contact.copy(isCheckedOff = isChecked)
                            onPhoneCheckedChange.invoke(newPhone)
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            modifier = Modifier.clickable {
                onPhoneClick.invoke(contact)
            }
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun PhonePreview() {
    Phone(contact = PhoneModel(1, "Phone 1", "Content 1", false), isSelected = true)
}