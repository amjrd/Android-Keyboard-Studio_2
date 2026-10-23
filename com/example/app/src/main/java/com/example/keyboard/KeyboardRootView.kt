package com.example.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KeyboardRootView(
    isUppercase: Boolean,
    onAction: (KeyboardAction) -> Unit
) {
    val row1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p")
    val row2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l")
    val row3 = listOf("z", "x", "c", "v", "b", "n", "m")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD1D5DB))
            .padding(vertical = 6.dp, horizontal = 3.dp)
    ) {
        KeyboardRow(keys = row1, isUppercase = isUppercase, onAction = onAction)
        KeyboardRow(keys = row2, isUppercase = isUppercase, onAction = onAction)

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            KeyboardKeyButton(
                text = if (isUppercase) "⬆" else "⇧",
                modifier = Modifier.weight(1.3f),
                backgroundColor = Color(0xFFA2A9B4)
            ) {
                onAction(KeyboardAction.Shift)
            }

            row3.forEach { char ->
                val displayChar = if (isUppercase) char.uppercase() else char
                KeyboardKeyButton(
                    text = displayChar,
                    modifier = Modifier.weight(1f)
                ) {
                    onAction(KeyboardAction.Character(char))
                }
            }

            KeyboardKeyButton(
                text = "⌫",
                modifier = Modifier.weight(1.3f),
                backgroundColor = Color(0xFFA2A9B4)
            ) {
                onAction(KeyboardAction.Delete)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            KeyboardKeyButton(
                text = "123",
                modifier = Modifier.weight(1.2f),
                backgroundColor = Color(0xFFA2A9B4)
            ) {}

            KeyboardKeyButton(
                text = "space",
                modifier = Modifier.weight(4.5f),
                backgroundColor = Color.White
            ) {
                onAction(KeyboardAction.Space)
            }

            KeyboardKeyButton(
                text = "return",
                modifier = Modifier.weight(1.5f),
                backgroundColor = Color(0xFFA2A9B4)
            ) {
                onAction(KeyboardAction.Done)
            }
        }
    }
}

@Composable
fun KeyboardRow(
    keys: List<String>,
    isUppercase: Boolean,
    onAction: (KeyboardAction) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        keys.forEach { char ->
            val displayChar = if (isUppercase) char.uppercase() else char
            KeyboardKeyButton(
                text = displayChar,
                modifier = Modifier.weight(1f)
            ) {
                onAction(KeyboardAction.Character(char))
            }
        }
    }
}

@Composable
fun KeyboardKeyButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .height(48.dp)
            .background(backgroundColor, shape = RoundedCornerShape(5.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}
