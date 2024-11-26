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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.keyranovack.spellbook.data.Spell

@Composable
fun DamageInfo(
    spell: Spell?
) {
    if (spell?.damage != null) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colors.surface)
                    .height(2.dp)
            )

            SpellDetail(
                spell.damage.damage_type.name,
                Icons.Rounded.LocalFireDepartment
            )

            spell.damage.damage_at_slot_level.forEach { (level, dice) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 8.dp)
                            .size(18.dp)
                            .background(MaterialTheme.colors.secondary, CircleShape)
                            .padding(1.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            level,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSecondary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(dice, style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}