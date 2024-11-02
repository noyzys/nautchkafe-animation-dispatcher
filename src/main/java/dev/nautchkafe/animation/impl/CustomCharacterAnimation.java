package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.Keyframe;
import dev.nautchkafe.animation.KeyframeAnimation;
import dev.nautchkafe.animation.KeyframeAnimationDispatcher;
import dev.nautchkafe.animation.KeyframeAnimationMessageConfig;
import dev.nautchkafe.animation.KeyframeRenderer;
import io.vavr.collection.List;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Represents a custom animation for characters with keyframes.
 * This class implements the {@link KeyframeAnimation} interface to provide a specific animation strategy.
 */
public final class CustomCharacterAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;

    public CustomCharacterAnimation(final KeyframeAnimationMessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    /**
     * Provides a supplier that generates a list of keyframes based on the number of specified cycles.
     * Each keyframe will contain a repeated characteristic animation based on the cycle index.
     *
     * @param cycles the number of animation cycles.
     * @return a supplier for the list of keyframes.
     */
    @Override
    public Supplier<List<Keyframe>> frames(final int cycles) {
        return () -> List.range(0, cycles)
                .map(i -> new Keyframe(messageConfig.titleMessage(),
                        messageConfig.subtitleMessage() + " " + messageConfig.character().repeat(i + 1)));
    }

    /**
     * Displays the animation to a player with a specified tick delay between keyframes.
     *
     * @param player the player to whom the animation will be displayed.
     * @param tickDelay the duration of delay between the display of each keyframe.
     * @param renderer the renderer that displays the keyframe to the player.
     */
    @Override
    public void display(final Player player, final Duration tickDelay, final KeyframeRenderer renderer) {
        KeyframeAnimationDispatcher.of(player, messageConfig, renderer, tickDelay).dispatch();
    }
}
