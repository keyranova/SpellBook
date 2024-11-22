package com.keyranovack.spellbook.data

class SpellsRepository {
    private val spellsService = RetrofitInstance.spellsService
    suspend fun getSpells(): Spells {
        return spellsService.getSpells()
    }

    suspend fun getSpell(index: String): Spell {
        return spellsService.getSpell(index)
    }
}