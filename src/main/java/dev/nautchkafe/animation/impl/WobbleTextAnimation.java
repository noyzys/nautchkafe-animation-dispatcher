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
 * Represents an animation of a wobble text effect.
 */
public final class WobbleTextAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;

    public WobbleTextAnimation(final KeyframeAnimationMessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    /**
     * Generates a supplier that provides a list of keyframes for the animation based on the number of cycles.
     *
     * @param cycles The number of cycles the animation should run, influencing the total number of keyframes.
     * @return A supplier of a list containing consecutive keyframes for the wobble animation.
     */
    @Override
    public Supplier<List<Keyframe>> frames(final int cycles) {
        return () -> List.range(0, cycles)
                .map(this::createKeyframe);
    }

    /**
     * Creates a keyframe with a wobble effect around the subtitle message based on the current counter.
     *
     * @param i The index used to determine the positioning of the wobble effect.
     * @return A new Keyframe with wobbled subtitle based on the index.
     */
    private Keyframe createKeyframe(final Integer i) {
        final String wobble = (i % 2 == 0)
                ? messageConfig.subtitleMessage() + " "
                : " " + messageConfig.subtitleMessage();

        return new Keyframe(messageConfig.titleMessage(), wobble);
    }

    /**
     * Display the animation to a player with specified timing and using a given renderer.
     *
     * @param player The player to whom the animation will be displayed.
     * @param tickDelay The delay between each keyframe in the animation.
     * @param renderer The renderer used to display each keyframe.
     */
    @Override
    public void display(final Player player, final Duration tickDelay, final KeyframeRenderer renderer) {
        final List<Keyframe> keyframes = frames(messageConfig.numberOfFrames()).get();
        KeyframeAnimationDispatcher.of(player, keyframes, renderer, tickDelay).dispatch();
    }
}

