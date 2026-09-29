package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CalendarBlue
import com.example.ui.theme.CalendarBlueDark
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.VioletDark
import com.example.ui.theme.VioletPrimary
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.theme.WhatsAppGreenDark

@Composable
fun QuickActionButtons(
    onWebsiteClick: () -> Unit,
    onShareClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onAddContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .testTag("quick_action_buttons_row"),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ActionButton(
            title = "Website",
            icon = Icons.Filled.Language,
            gradientColors = listOf(CalendarBlue, CalendarBlueDark),
            testTag = "action_btn_website",
            onClick = onWebsiteClick,
            modifier = Modifier.weight(1f)
        )

        ActionButton(
            title = "Share",
            icon = Icons.Filled.Share,
            gradientColors = listOf(VioletPrimary, VioletDark),
            testTag = "action_btn_share",
            onClick = onShareClick,
            modifier = Modifier.weight(1f)
        )

        ActionButton(
            title = "WhatsApp",
            icon = Icons.Filled.Chat,
            gradientColors = listOf(WhatsAppGreen, WhatsAppGreenDark),
            testTag = "action_btn_whatsapp",
            onClick = onWhatsAppClick,
            modifier = Modifier.weight(1f)
        )

        ActionButton(
            title = "Contacts",
            icon = Icons.Filled.PersonAdd,
            gradientColors = listOf(IndigoSecondary, VioletDark),
            testTag = "action_btn_contacts",
            onClick = onAddContactClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ActionButton(
    title: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .height(72.dp)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Brush.linearGradient(gradientColors))
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}
