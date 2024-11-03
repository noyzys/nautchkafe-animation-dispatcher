package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.Keyframe;
import dev.nautchkafe.animation.KeyframeAnimation;
import dev.nautchkafe.animation.KeyframeAnimationDispatcher;
import dev.nautchkafe.animation.KeyframeAnimationMessageConfig;
import dev.nautchkafe.animation.KeyframeAnimationPlugin;
import dev.nautchkafe.animation.KeyframeLogger;
import dev.nautchkafe.animation.KeyframeRenderer;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Provides animation for counting down from a specified number of cycles to 0,
 * displaying each keyframe to the given player at the defined tick intervals.
 */
public final class CountdownAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;
    private final KeyframeAnimationPlugin plugin;

    public CountdownAnimation(final KeyframeAnimationMessageConfig messageConfig,
                              final KeyframeAnimationPlugin plugin) {
        this.messageConfig = messageConfig;
        this.plugin = plugin;
    }

    /**
     * Generates a supplier that, when invoked, returns a list of keyframes
     * for a countdown from a given number of cycles down to 0.
     *
     * @param cycles the number of cycles to countdown from
     * @return a supplier returning the list of keyframes for the countdown
     */
    @Override
    public Supplier<List<Keyframe>> frames(final int cycles) {
        return () -> List.rangeClosed(0, cycles)
                .reverse()
                .map(i -> new Keyframe(
                        messageConfig.titleMessage(),
                        messageConfig.subtitleMessage() + " " + i
                ));
    }

    /**
     * Displays the countdown animation to the specified player, rendering each keyframe
     * using the given renderer with specified delay between keyframes.
     *
     * @param player the player to whom the animation will be displayed
     * @param tickDelay the delay between each keyframe display
     * @param renderer the renderer for displaying each keyframe
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