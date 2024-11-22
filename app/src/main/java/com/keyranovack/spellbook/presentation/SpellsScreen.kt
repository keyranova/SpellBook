package com.keyranovack.spellbook.presentation

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.keyranovack.spellbook.R

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun SpellsScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery
    val spells by viewModel.spells
    val favorites by remember { viewModel.favorites }

    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.SingleButton
        )
    )

    val voiceLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->
            // This is where you process the intent and extract the speech text from the intent.
            activityResult.data?.let { data ->
                val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                viewModel.searchSpells(results?.get(0) ?: "")
            }
        }

    val voiceIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            stringResource(R.string.voice_search)
        )
    }

    ScreenScaffold(scrollState = columnState) {
        ScalingLazyColumn(
            columnState = columnState
        ) {
            item {
                ResponsiveListHeader(contentPadding = firstItemPadding()) {
                    Text(text = stringResource(R.string.spell_book))
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                        Button(
                            modifier = Modifier
                                .size(40.dp),
                            onClick = {
                                voiceLauncher.launch(voiceIntent)
                            }
                        ) {
                            Icon(Icons.Rounded.Mic, stringResource(R.string.voice_search))
                        }
                    }

                    if (spells.isNotEmpty()) {
                        Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                            Button(
                                modifier = Modifier
                                    .size(40.dp),
                                onClick = {
                                    viewModel.clearResults()
                                }
                            ) {
                                Icon(Icons.Rounded.Clear, stringResource(R.string.clear_results))
                            }
                        }
                    }
                }
            }

            if (spells.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.search_results),
                        style = MaterialTheme.typography.title3
                    )
                }

                items(spells, key = { "spell-${it.index}" }) {
                    Chip(
                        it.name,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate("spells/${it.index}")
                        }
                    )
                }
            }

            if (favorites.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.favorites),
                        style = MaterialTheme.typography.title3
                    )
                }

                items(favorites, key = { "favorite-${it.index}" }) {
                    Chip(
                        it.name,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate("spells/${it.index}")
                        }
                    )
                }
            }
        }
    }
}