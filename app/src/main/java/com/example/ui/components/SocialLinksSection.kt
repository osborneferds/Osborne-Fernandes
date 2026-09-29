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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VioletPrimary

@Composable
fun SocialLinksSection(
    twitterUrl: String,
    linkedinUrl: String,
    githubUrl: String,
    email: String,
    onLinkClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .testTag("social_links_section")
    ) {
        Text(
            text = "Connect Across Networks",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialIconButton(
                label = "LinkedIn",
                icon = Icons.Filled.Language,
                tint = Color(0xFF0077B5),
                testTag = "social_btn_linkedin",
                onClick = { onLinkClick(linkedinUrl) }
            )

            SocialIconButton(
                label = "Twitter",
                icon = Icons.Filled.Share,
                tint = Color(0xFF1DA1F2),
                testTag = "social_btn_twitter",
                onClick = { onLinkClick(twitterUrl) }
            )

            SocialIconButton(
                label = "GitHub",
                icon = Icons.Filled.Language,
                tint = Color(0xFF333333),
                testTag = "social_btn_github",
                onClick = { onLinkClick(githubUrl) }
            )

            SocialIconButton(
                label = "Email",
                icon = Icons.Filled.Email,
                tint = Color(0xFFEA4335),
                testTag = "social_btn_email",
                onClick = { onLinkClick("mailto:$email") }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Verified footer matching website
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Osborneferds",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Filled.Verified,
                contentDescription = "Verified badge",
                tint = VioletPrimary,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "DigitalCard",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = VioletPrimary
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Mobile Edition • Tap any button to connect directly",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun SocialIconButton(
    label: String,
    icon: ImageVector,
    tint: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        modifier = Modifier
            .size(48.dp)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
