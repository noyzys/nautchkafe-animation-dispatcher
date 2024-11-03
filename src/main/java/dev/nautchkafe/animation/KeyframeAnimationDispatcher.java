package dev.nautchkafe.animation;

import io.vavr.collection.List;
import org.bukkit.entity.Player;

import java.time.Duration;

/**
 * A class to manage and dispatch keyframe animations for a player.
 */
public final class KeyframeAnimationDispatcher {

    private final Player player;
    private final List<Keyframe> keyframes;
    private final Duration tickDelay;
    private final KeyframeAnimationPlugin plugin;

    private KeyframeAnimationDispatcher(final Player player, final List<Keyframe> keyframes, final KeyframeRenderer renderer,
                                        final Duration tickDelay, final KeyframeAnimationPlugin plugin) {
        this.player = player;
        this.keyframes = keyframes;
        this.tickDelay = tickDelay;
        this.plugin = plugin;
    }

    /**
     * Factory method to create an instance of KeyframeAnimationDispatcher with configuration.
     *
     * @param player The player who will experience the animation.
     * @param config The configuration object containing animation setup.
     * @param renderer The renderer for keyframe effects.
     * @param tickDelay The fixed delay between keyframe animations.
     * @return A new KeyframeAnimationDispatcher instance.
     */
    public static KeyframeAnimationDispatcher of(final Player player, final KeyframeAnimationMessageConfig config,
                                                 final KeyframeRenderer renderer, final Duration tickDelay,
                                                 final KeyframeAnimationPlugin plugin) {
        final List<Keyframe> keyframes = config.customKeyframes();
        return new KeyframeAnimationDispatcher(player, keyframes, renderer, tickDelay, plugin);
    }

    /**
     * Factory method to create an instance of KeyframeAnimationDispatcher with a given list of keyframes.
     *
     * @param player The player who will experience the animation.
     * @param keyframes The list of pre-created keyframes.
     * @param renderer The renderer for keyframe effects.
     * @param tickDelay The fixed delay between keyframe animations.
     * @return A new KeyframeAnimationDispatcher instance.
     */
    public static KeyframeAnimationDispatcher of(final Player player, final List<Keyframe> keyframes,
                                                 final KeyframeRenderer renderer, final Duration tickDelay,
                                                 final KeyframeAnimationPlugin plugin) {
        return new KeyframeAnimationDispatcher(player, keyframes, renderer, tickDelay, plugin);
    }

    /**
     * Creates a list of keyframes based on the given configuration.
     *
     * @param config Configuration containing the parameters for keyframe generation.
     * @return A list of generated keyframes.
     */
    private static List<Keyframe> createKeyframes(final KeyframeAnimationMessageConfig config) {
        return List.range(0, config.numberOfFrames())
                .map(i -> new Keyframe(config.titleMessage(), config.subtitleMessage() + config.character()));
    }

    /**
     * Dispatches the keyframe animation task to execute based on the defined sequence and timing.
     */
    public void dispatch() {
        final KeyframeAnimationTask animationTask = KeyframeAnimationTask.create(player, keyframes, tickDelay);
        animationTask.runTaskTimerAsynchronously(plugin, 0, tickDelay.toMillis() / 50);
    }
}
