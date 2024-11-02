package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.*;
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

    public CountdownAnimation(final KeyframeAnimationMessageConfig messageConfig) {
        this.messageConfig = messageConfig;
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
    @Override
    public void display(final Player player, final Duration tickDelay, final KeyframeRenderer renderer) {
        Try.run(() -> {
            final List<Keyframe> keyframes = frames(messageConfig.numberOfFrames()).get();
            KeyframeAnimationDispatcher.of(player, keyframes, renderer, tickDelay).dispatch();
        }).onFailure(e -> KeyframeLogger.logInfo("> Error in Countdown Animation" + e.getMessage()));
    }
}
