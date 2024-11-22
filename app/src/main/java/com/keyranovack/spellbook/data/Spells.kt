package com.keyranovack.spellbook.data

data class Spells(
    val count: Int,
    val results: List<SpellResult>
)

data class SpellResult(
    val index: String,
    val level: Int,
    val name: String,
    val url: String
)