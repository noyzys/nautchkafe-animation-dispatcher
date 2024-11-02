package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.*;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * The {@code LoadingProgressAnimation} class is responsible for managing the loading progress
 * animation using keyframes.
 *
 * <p>This class implements {@code KeyframeAnimation} interface, primarily focused on generating
 * and displaying a sequence of keyframes to represent an animation for loading progress. The class
 * makes use of {@code AnimationCharacter.LOADING_CHARACTERS} to form the animated sequence.
 *
 * <p>Instances of this class are immutable once they are created as the {@code messageConfig}
 * is final and used to determine the title message and number of frames to be animated.
 */
public final class LoadingProgressAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;

    public LoadingProgressAnimation(final KeyframeAnimationMessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    /**
     * Generates a sequence of keyframes for the animation based on the specified number of cycles.
     *
     * @param cycles the number of cycles to generate keyframes for
     * @return a {@code Supplier} that supplies a list of keyframes representing the animation
     */
    @Override
    public Supplier<List<Keyframe>> frames(final int cycles) {
        return () -> List.range(0, cycles)
                .map(i -> createKeyframe(i % AnimationCharacter.LOADING_CHARACTERS.size()));
    }

    /**
     * Creates a keyframe for the animation at the specified character index.
     *
     * @param characterIndex the index of the character to use from the loading characters pool
     * @return a {@code Keyframe} object that includes the title message and the respective
     * animated character
     */
    private Keyframe createKeyframe(final int characterIndex) {
        final String character = AnimationCharacter.LOADING_CHARACTERS.get(characterIndex);

        return new Keyframe(messageConfig.titleMessage(), character);
    }

    /**
     * Displays the animation by dispatching the keyframes to the specified player.
     *
     * <p>This method handles any {@link Throwable} during the frame dispatch process
     * and prints the stack trace to standard error.
     *
     * @param player the player to whom the animation frames are to be displayed
     * @param tickDelay the delay between each tick/frame of the animation
     * @param renderer the renderer utilized for rendering each keyframe
     */
    @Override
    public void display(final Player player, final Duration tickDelay, final KeyframeRenderer renderer) {
        Try.run(() -> {
            final List<Keyframe> keyframes = frames(messageConfig.numberOfFrames()).get();
            KeyframeAnimationDispatcher.of(player, keyframes, renderer, tickDelay).dispatch();
        }).onFailure(throwable -> {
            throwable.printStackTrace();
        });
    }
}