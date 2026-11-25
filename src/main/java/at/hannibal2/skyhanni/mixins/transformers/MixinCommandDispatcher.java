package at.hannibal2.skyhanni.mixins.transformers;

import at.hannibal2.skyhanni.events.chat.TabCompletionEvent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Mixin(value = CommandDispatcher.class, remap = false)
public abstract class MixinCommandDispatcher<S> {

    @Inject(method = "getCompletionSuggestions(Lcom/mojang/brigadier/ParseResults;I)Ljava/util/concurrent/CompletableFuture;", at = @At(value = "INVOKE", target = "Ljava/lang/String;toLowerCase(Ljava/util/Locale;)Ljava/lang/String;"), cancellable = true)
    public void getCompletionSuggestions(ParseResults<S> parse, int cursor, CallbackInfoReturnable<CompletableFuture<Suggestions>> cir, @Local(ordinal = 1) int start, @Local(ordinal = 0) String fullInput, @Local(ordinal = 1) String beforeCursor) {
        if (!beforeCursor.contains(" ")) return;
        Suggestions suggestions = buildFromEvent(start, fullInput, beforeCursor, new ArrayList<>());
        if (suggestions == null) return;

        cir.setReturnValue(CompletableFuture.completedFuture(suggestions));
    }

    @ModifyReturnValue(method = "getCompletionSuggestions(Lcom/mojang/brigadier/ParseResults;I)Ljava/util/concurrent/CompletableFuture;", at = @At(value = "RETURN"))
    public CompletableFuture<Suggestions> getCompletionSuggestionsWIthExisting(CompletableFuture<Suggestions> original, @Local(ordinal = 1) int start, @Local(ordinal = 0) String fullInput, @Local(ordinal = 1) String beforeCursor) {
        if (beforeCursor.contains(" ")) return original;
        return original.thenApply(suggestions -> {
            ArrayList<String> suggestionList = new ArrayList<>(suggestions.getList().stream()
                .map(Suggestion::getText)
                .toList());
            Suggestions newSuggestions = buildFromEvent(start, fullInput, beforeCursor, suggestionList);
            if (newSuggestions == null) {
                return suggestions;
            }
            return newSuggestions;
        });
    }

    @Unique
    private Suggestions buildFromEvent(int start, String fullInput, String beforeCursor, ArrayList<String> existing) {
        TabCompletionEvent tabCompletionEvent = TabCompletionEvent.fromInput(fullInput, beforeCursor.length(), existing);
        tabCompletionEvent.post();
        return tabCompletionEvent.buildSuggestions(start);
    }
}
