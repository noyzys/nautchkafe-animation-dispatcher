package dev.nautchkafe.animation;

import io.vavr.Lazy;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

/**
 * KeyframeAnimationTask is a final class that extends BukkitRunnable to handle the animation sequence for a player.
 * This class controls the animation state, schedule, and rendering logic specific to keyframe animations.
 */
final class KeyframeAnimationTask extends BukkitRunnable {

    private final Player player;
    private KeyframeAnimationState animationState;
    private final KeyframeRenderer renderer;
    private final KeyframeAnimationScheduler scheduler;

    private final Lazy<List<Keyframe>> lazyKeyframes;

    private KeyframeAnimationTask(final Player player, final KeyframeAnimationState animationState,
                                  final KeyframeRenderer renderer, final KeyframeAnimationScheduler scheduler,
                                  final Lazy<List<Keyframe>> lazyKeyframes) {
        this.player = player;
        this.animationState = animationState;
        this.renderer = renderer;
        this.scheduler = scheduler;
        this.lazyKeyframes = lazyKeyframes;
    }

    /**
     * Factory method to create an instance of KeyframeAnimationTask using an animation configuration.
     *
     * @param player The player for whom the animation is to be created.
     * @param config Configuration containing details about the animation.
     * @param tickDelay Duration between each frame of animation.
     * @return A new instance of KeyframeAnimationTask.
     */
    public static KeyframeAnimationTask create(final Player player, final KeyframeAnimationMessageConfig config, final Duration tickDelay) {
        return createCommon(player, createKeyframes(config), tickDelay);
    }

    /**
     * Factory method to create an instance of KeyframeAnimationTask using a predefined list of keyframes.
     *
     * @param player The player for whom the animation is to be created.
     * @param keyframes List of keyframes that define the animation.
     * @param tickDelay Duration between each frame of animation.
     * @return A new instance of KeyframeAnimationTask.
     */
    public static KeyframeAnimationTask create(final Player player, final List<Keyframe> keyframes, final Duration tickDelay) {
        return createCommon(player, keyframes, tickDelay);
    }

    /**
     * Private helper method used by factory methods to create an instance of KeyframeAnimationTask.
     *
     * @param player The player for whom the animation is being set up.
     * @param keyframes List of keyframes to be animated.
     * @param tickDelay Delay between updating frames of animation.
     * @return A new instance of KeyframeAnimationTask.
     */
    private static KeyframeAnimationTask createCommon(final Player player,
                                                      final List<Keyframe> keyframes,
                                                      final Duration tickDelay) {
        final KeyframeAnimationState state = new KeyframeAnimationState(keyframes);
        final KeyframeAnimationScheduler scheduler = KeyframeAnimationScheduler.create(tickDelay);
        final KeyframeRenderer renderer = KeyframeRenderer.miniMessageRenderer();
        final Lazy<List<Keyframe>> lazyKeyframes = Lazy.of(() -> keyframes);

        return new KeyframeAnimationTask(player, state, renderer, scheduler, lazyKeyframes);
    }

    /**
     * Transforms animation message configurations into a list of keyframes.
     *
     * @param config Configuration from which keyframes are derived.
     * @return List of created keyframes based on configuration.
     */
    private static List<Keyframe> createKeyframes(final KeyframeAnimationMessageConfig config) {
        final String subtitleWithCharacter = config.subtitleMessage() + " " + config.character();
        final int keyframeLimit = Math.min(config.numberOfFrames(), 100);

        return List.range(0, keyframeLimit)
                .map(i -> new Keyframe(config.titleMessage(), subtitleWithCharacter));
    }

    /**
     * Overridden run method that handles frame updates and checks the activity of the animation.
     */
    @Override
    public void run() {
        if (!isAnimationActive()) {
            cancel();
            return;
        }

        updateAnimation();
    }

    /**
     * Checks if the animation is still active based on player's status and animation's completion state.
     *
     * @return true if the animation is active, false otherwise.
     */
    private boolean isAnimationActive() {
        return player.isOnline() && !animationState.isFinished();
    }

    /**
     * Updates the animation by sequencing through the frames, rendering them and modifying internal state accordingly.
     */
    private void updateAnimation() {
        if (scheduler.shouldUpdateFrame()) {
            final List<Keyframe> keyframes = lazyKeyframes.get();

            animationState = new KeyframeAnimationState(keyframes);

            animationState.currentFrame()
                    .peek(keyframe -> Try.run(() -> renderer.render(player, keyframe, scheduler.tickDelay()))
                            .onFailure(e -> {
                                cancel();
                            }));

            animationState = animationState.nextFrame();
            scheduler.withUpdatedFrameTime();
        }
    }
}