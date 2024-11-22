package com.keyranovack.spellbook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowOutward
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.rounded.Timelapse
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.Button
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.keyranovack.spellbook.R
import com.keyranovack.spellbook.icons.Swords

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun SpellScreen(
    viewModel: MainViewModel,
    index: String
) {
    val spell by viewModel.spell

    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.Text
        )
    )

    val isFavorite by remember {
        derivedStateOf { viewModel.isFavorite(spell) }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchSpell(index)

    }

    ScreenScaffold(scrollState = columnState) {
        if (spell != null) {
            ScalingLazyColumn(
                columnState = columnState
            ) {
                item {
                    ResponsiveListHeader(contentPadding = firstItemPadding()) {
                        Text(text = spell?.name ?: "")
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(spell?.casting_time ?: "", style = MaterialTheme.typography.body2)
                            Text("Level ${spell?.level ?: ""}", style = MaterialTheme.typography.body2)
                        }

                        Button(
                            imageVector = if (isFavorite) Icons.Rounded.Star else Icons.Rounded.StarOutline,
                            contentDescription = stringResource(if (isFavorite) R.string.remove_from_favorites else R.string.add_to_favorites),
                            onClick = {
                                if (isFavorite) {
                                    viewModel.removeFromFavorites(spell!!)
                                } else {
                                    viewModel.addToFavorites(spell!!)
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                            .background(MaterialTheme.colors.surface)
                            .height(2.dp)
                    )
                }

                item {
                    SpellDetail(
                        spell?.range,
                        Icons.Rounded.ArrowOutward,
                    )
                }

                item {
                    SpellDetail(
                        spell?.duration,
                        Icons.Rounded.Timelapse
                    )
                }

                item {
                    SpellDetail(
                        spell?.attack_type,
                        Swords
                    )
                }

                item {
                    Column {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colors.surface)
                                .padding(8.dp)
                        ) {
                            spell?.desc?.forEach { desc ->
                                Text(desc, style = MaterialTheme.typography.body2)
                            }
                        }

                        if (spell?.higher_level?.isNotEmpty() == true) {
                            Column(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colors.surface)
                                    .padding(8.dp)
                            ) {
                                spell?.higher_level?.forEach { level ->
                                    Text(level, style = MaterialTheme.typography.body2)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpellDetail(
    text: String?,
    icon: ImageVector
) {
    if (text != null) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
                    .background(MaterialTheme.colors.secondary, CircleShape)
                    .padding(4.dp)
            ) {
                Icon(
                    icon,
                    null,
                    tint = MaterialTheme.colors.background
                )
            }
            Text(text)
        }
    }
}