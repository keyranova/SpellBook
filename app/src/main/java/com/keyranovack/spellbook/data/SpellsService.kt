package com.keyranovack.spellbook.data

import retrofit2.http.GET
import retrofit2.http.Path

interface SpellsService {
    @GET("spells")
    suspend fun getSpells(): Spells

    @GET("spells/{index}")
    suspend fun getSpell(@Path("index") index: String): Spell
}