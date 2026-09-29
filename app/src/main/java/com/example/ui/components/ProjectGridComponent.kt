package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PortfolioProject
import com.example.data.model.defaultPortfolioProjects
import com.example.ui.theme.VioletDark
import com.example.ui.theme.VioletPrimary

@Composable
fun ProjectGridComponent(
    projects: List<PortfolioProject> = defaultPortfolioProjects(),
    onProjectClick: (PortfolioProject) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = remember(projects) {
        listOf("All") + projects.map { it.category }.distinct()
    }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }

    val filteredProjects = remember(selectedCategoryIndex, projects) {
        if (selectedCategoryIndex == 0) {
            projects
        } else {
            val selectedCat = categories[selectedCategoryIndex]
            projects.filter { it.category == selectedCat }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("portfolio_grid_container")
    ) {
        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEachIndexed { index, category ->
                val isSelected = selectedCategoryIndex == index
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategoryIndex = index },
                    label = {
                        Text(
                            text = category,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VioletPrimary,
                        selectedLabelColor = Color.White,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = null,
                    modifier = Modifier.testTag("filter_chip_$category")
                )
            }
        }

        // Responsive Grid layout adapting to container width constraints
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val availableWidth = maxWidth
            val columns = when {
                availableWidth < 360.dp -> 1
                availableWidth < 620.dp -> 2
                else -> 3
            }

            val chunked = filteredProjects.chunked(columns)

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                chunked.forEach { rowProjects ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowProjects.forEach { project ->
                            ProjectHoverCard(
                                project = project,
                                onClick = { onProjectClick(project) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowProjects.size < columns) {
                            for (i in 0 until (columns - rowProjects.size)) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectHoverCard(
    project: PortfolioProject,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isHovered by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Smooth hover animations
    val scaleAnim by animateFloatAsState(
        targetValue = if (isHovered) 1.035f else 1.0f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "card_scale"
    )

    val imageZoomAnim by animateFloatAsState(
        targetValue = if (isHovered) 1.08f else 1.0f,
        animationSpec = tween(durationMillis = 300),
        label = "image_zoom"
    )

    val elevationAnim by animateDpAsState(
        targetValue = if (isHovered) 8.dp else 2.dp,
        animationSpec = tween(durationMillis = 250),
        label = "card_elevation"
    )

    val borderColorAnim by animateColorAsState(
        targetValue = if (isHovered) VioletPrimary else Color.Transparent,
        animationSpec = tween(durationMillis = 250),
        label = "border_color"
    )

    val borderWidthAnim by animateDpAsState(
        targetValue = if (isHovered) 2.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "border_width"
    )

    val scrimAlphaAnim by animateFloatAsState(
        targetValue = if (isHovered) 0.78f else 0.52f,
        animationSpec = tween(durationMillis = 250),
        label = "scrim_alpha"
    )

    val explorePillAlpha by animateFloatAsState(
        targetValue = if (isHovered) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "pill_alpha"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(borderWidthAnim, borderColorAnim),
        elevation = CardDefaults.cardElevation(defaultElevation = elevationAnim),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .scale(scaleAnim)
            .hoverable(interactionSource = interactionSource)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter, PointerEventType.Move -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            }
            .clickable(onClick = onClick)
            .testTag("portfolio_project_card_${project.id}")
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image Box with dynamic aspect ratio and hover overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Image(
                    painter = painterResource(id = project.imageRes),
                    contentDescription = project.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(imageZoomAnim)
                )

                // Dark gradient scrim for legibility & hover depth
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = scrimAlphaAnim * 0.4f),
                                    Color.Black.copy(alpha = scrimAlphaAnim)
                                )
                            )
                        )
                )

                // Category Tag on Top Left
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.55f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = project.category,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Interactive Quick View pill revealing on hover
                if (explorePillAlpha > 0.01f) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = VioletPrimary.copy(alpha = 0.95f),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .alpha(explorePillAlpha)
                            .scale(0.85f + (explorePillAlpha * 0.15f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "View Details",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Explore",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Metric Badge on Bottom Right
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = VioletDark.copy(alpha = 0.85f),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = project.keyMetrics,
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Card Caption
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = project.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = project.shortDescription,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Tech stack string
                Text(
                    text = project.techStack.joinToString(" • "),
                    fontSize = 10.sp,
                    color = VioletPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
