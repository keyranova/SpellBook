package com.keyranovack.spellbook.data

data class Spell(
    val attack_type: String,
    val casting_time: String,
    val classes: List<External>,
    val components: List<String>,
    val concentration: Boolean,
    val damage: Damage?,
    val desc: List<String>,
    val duration: String,
    val higher_level: List<String>,
    val index: String,
    val level: Int,
    val material: String,
    val name: String,
    val range: String,
    val ritual: Boolean,
    val school: External,
    val subclasses: List<External>,
    val url: String
)

data class Damage(
    val damage_at_slot_level: HashMap<String, String>,
    val damage_type: External
)

data class External(
    val index: String,
    val name: String,
    val url: String
)