package com.keyranovack.spellbook.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.keyranovack.spellbook.data.AppDataStore
import com.keyranovack.spellbook.data.Spell
import com.keyranovack.spellbook.data.SpellResult
import com.keyranovack.spellbook.data.Spells
import com.keyranovack.spellbook.data.SpellsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.apache.commons.text.similarity.FuzzyScore
import java.util.Locale

class MainViewModel(
    private val appDataStore: AppDataStore
) : ViewModel() {
    private val userSettingsFlow = appDataStore.userSettingsFlow
    private val repository = SpellsRepository()

    val searchQuery = mutableStateOf("")
    private val _spells = MutableStateFlow(Spells(
        count = 0,
        results = emptyList()
    ))
    val spells = mutableStateOf<List<SpellResult>>(emptyList())
    val spell = mutableStateOf<Spell?>(null)
    val favorites = mutableStateOf<List<SpellResult>>(emptyList())

    init {
        fetchSpells()
    }

    private fun fetchSpells() {
        viewModelScope.launch {
            try {
                val response = repository.getSpells()
                _spells.value = response
                getFavorites()
            } catch (e: Exception) {
                Log.e("SpellsAPI", "failed to load spells", e)
            }
        }
    }

    fun searchSpells(query: String) {
        if (searchQuery.value == query) return

        searchQuery.value = query
        spells.value = emptyList()

        val foundSpells = mutableListOf<SpellResult>()

        viewModelScope.launch {
            _spells.collect {
                it.results.forEach { spell ->
                    if (matches(spell.name, query)) {
                        foundSpells.add(spell)
                    }
                }
            }
        }

        spells.value = foundSpells
    }

    fun clearResults() {
        spells.value = emptyList()
    }

    fun fetchSpell(index: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSpell(index)
                spell.value = response
            } catch (e: Exception) {
                Log.e("SpellsAPI", "failed to load spell", e)
            }
        }
    }

    private fun getFavorites() {
        val favoritesList = mutableListOf<SpellResult>()

        viewModelScope.launch {
            favoritesList.clear()
            userSettingsFlow.collectLatest { settings ->
                _spells.collect {
                    it.results.forEach { spell ->
                        if (settings.favorites.contains(spell.index)) {
                            favoritesList.add(spell)
                        }
                    }

                    favorites.value = favoritesList
                }
            }
        }
    }

    fun addToFavorites(spell: Spell) {
        viewModelScope.launch {
            val favorites = userSettingsFlow.first().favorites.toMutableSet()
            favorites.add(spell.index)

            appDataStore.updateFavorites(favorites)
            getFavorites()
        }
    }

    fun removeFromFavorites(spell: Spell) {
        viewModelScope.launch {
            val favorites = userSettingsFlow.first().favorites.toMutableSet()
            favorites.remove(spell.index)

            appDataStore.updateFavorites(favorites)
            getFavorites()
        }
    }

    fun isFavorite(spell: Spell?): Boolean {
        if (spell == null) return false
        return favorites.value.any {
            it.index == spell.index
        }
    }

    private fun matches(label: String, query: String): Boolean {
        if (label.contains(query)) return true
        val fuzzyScore = FuzzyScore(Locale.getDefault())
        return fuzzyScore.fuzzyScore(label, query) >= query.length * 1.5
    }
}

class MainViewModelFactory(
    private val appDataStore: AppDataStore
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(appDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}