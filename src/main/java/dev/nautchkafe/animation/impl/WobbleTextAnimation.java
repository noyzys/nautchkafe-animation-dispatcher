package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.*;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Represents an animation of a wobble text effect.
 */
public final class WobbleTextAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;
    private final KeyframeAnimationPlugin plugin;

    public WobbleTextAnimation(final KeyframeAnimationMessageConfig messageConfig,
                               final KeyframeAnimationPlugin plugin) {
        this.messageConfig = messageConfig;
        this.plugin = plugin;
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
     * @param player    The player to whom the animation will be displayed.
     * @param tickDelay The delay between each keyframe in the animation.
     * @param renderer  The renderer used to display each keyframe.
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
