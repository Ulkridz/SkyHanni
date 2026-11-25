package at.hannibal2.skyhanni.events.chat

import at.hannibal2.skyhanni.api.event.SkyHanniEvent
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder

class TabCompletionEvent(
    val leftOfCursor: String,
    val fullText: String,
    private val originalCompletions: List<String>,
) : SkyHanniEvent() {
    val lastWord = leftOfCursor.substringAfterLast(' ')
    private val additionalSuggestions = mutableSetOf<String>()

    fun addSuggestion(suggestion: String) {
        if (!suggestion.startsWith(lastWord, ignoreCase = true)) return
        val adjustedSuggestion = suggestion.removePrefix("/")
        additionalSuggestions.add(adjustedSuggestion)
    }

    fun addSuggestions(suggestions: Iterable<String>) {
        suggestions.forEach(this::addSuggestion)
    }

    fun addSuggestionUnchecked(suggestion: String) {
        val adjustedSuggestion = suggestion.removePrefix("/")
        additionalSuggestions.add(adjustedSuggestion)
    }

    fun addSuggestionsUnchecked(suggestions: Iterable<String>) {
        suggestions.forEach(this::addSuggestionUnchecked)
    }

    val command = if (leftOfCursor.startsWith("/"))
        leftOfCursor.substring(1).substringBefore(" ").lowercase()
    else ""

    fun isCommand(commandName: String): Boolean {
        return commandName.equals(command, ignoreCase = true)
    }

    fun intoSuggestionArray(): Array<String>? {
        if (additionalSuggestions.isEmpty()) return null
        return (originalCompletions + additionalSuggestions).toTypedArray()
    }

    fun buildSuggestions(start: Int): Suggestions? {
        val suggestions = intoSuggestionArray() ?: return null
        // Hypixel commands can be unknown to the client dispatcher. Its parse start
        // then points to the command itself, even when completing an argument.
        val replacementStart = maxOf(start, leftOfCursor.lastIndexOf(' ') + 1)
        val builder = SuggestionsBuilder(leftOfCursor, replacementStart)
        suggestions.forEach { builder.suggest(it) }
        return builder.build()
    }

    companion object {
        @JvmStatic
        fun fromInput(input: String, cursor: Int, originalCompletions: List<String>): TabCompletionEvent =
            TabCompletionEvent(input.substring(0, cursor), input, originalCompletions)
    }
}
