package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.*;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Represents a custom animation for characters with keyframes.
 * This class implements the {@link KeyframeAnimation} interface to provide a specific animation strategy.
 */
public final class CustomCharacterAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;
    private final KeyframeAnimationPlugin plugin;

    public CustomCharacterAnimation(final KeyframeAnimationMessageConfig messageConfig,
                                    final KeyframeAnimationPlugin plugin) {
        this.messageConfig = messageConfig;
        this.plugin = plugin;
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
     * @param player    the player to whom the animation will be displayed.
     * @param tickDelay the duration of delay between the display of each keyframe.
     * @param renderer  the renderer that displays the keyframe to the player.
     */
    private void displayAnimation(Player player, Duration tickDelay, KeyframeRenderer renderer, int cycles) {
        Try.run(() -> {
            final List<Keyframe> keyframes = frames(cycles).get();
            final KeyframeAnimationDispatcher dispatcher = KeyframeAnimationDispatcher.of(player, keyframes, renderer, tickDelay, plugin);

            dispatcher.dispatch();
        }).onFailure(e -> KeyframeLogger.logInfo("> Error in Custom Character Animation: " + e.getMessage()));
    }

    /**
     * Displays the animation by dispatching the keyframes to the specified player.
     +
     * @param player    the player to whom the animation frames are to be displayed
     * @param tickDelay the delay between each tick/frame of the animation
     */
    @Override
    public void display(final Player player, final Duration tickDelay) {
        displayAnimation(player, tickDelay, KeyframeRenderer.miniMessageRenderer(), messageConfig.numberOfFrames());
    }
}