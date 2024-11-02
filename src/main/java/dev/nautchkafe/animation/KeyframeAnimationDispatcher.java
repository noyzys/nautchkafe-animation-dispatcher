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
    private final KeyframeRenderer renderer;
    private final Duration tickDelay;
    private KeyframeAnimationPlugin plugin;

    private KeyframeAnimationDispatcher(final Player player, final List<Keyframe> keyframes, final KeyframeRenderer renderer,
                                        final Duration tickDelay) {
        this.player = player;
        this.keyframes = keyframes;
        this.renderer = renderer;
        this.tickDelay = tickDelay;
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
                                                 final KeyframeRenderer renderer, final Duration tickDelay) {
        List<Keyframe> keyframes = createKeyframes(config);
        return new KeyframeAnimationDispatcher(player, keyframes, renderer, tickDelay);
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
                                                 final KeyframeRenderer renderer, final Duration tickDelay) {
        return new KeyframeAnimationDispatcher(player, keyframes, renderer, tickDelay);
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
        animationTask.runTaskTimer(plugin, 0, tickDelay.toMillis() / 50);
    }
}
