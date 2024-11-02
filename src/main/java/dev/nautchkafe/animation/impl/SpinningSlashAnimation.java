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

import io.vavr.control.Try;

/**
 * Represents an animation of a spinning slash effect.
 */
public final class SpinningSlashAnimation implements KeyframeAnimation {

    private final KeyframeAnimationMessageConfig messageConfig;

    public SpinningSlashAnimation(final KeyframeAnimationMessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    /**
     * Provides a supplier for generating a list of keyframes based on the number of cycles.
     *
     * @param cycles The number of cycles the animation should run.
     * @return A Supplier that when invoked, returns a list of keyframes for the animation.
     */
    @Override
    public Supplier<List<Keyframe>> frames(final int cycles) {
        return () -> List.range(0, cycles)
                .map(i -> createKeyframe(i % AnimationCharacter.SPINNING_CHARACTERS.size()));
    }

    /**
     * Creates a keyframe for the animation at a given index.
     *
     * @param characterIndex The index of the character in the list of spinning characters.
     * @return A newly created Keyframe with a character and title message.
     */
    private Keyframe createKeyframe(final int characterIndex) {
        final String character = AnimationCharacter.SPINNING_CHARACTERS.get(characterIndex);

        return new Keyframe(messageConfig.titleMessage(), character);
    }

    /**
     * Displays the animation to a player with the specified parameters.
     *
     * @param player The player to whom the animation will be displayed.
     * @param tickDelay The duration between each frame of the animation.
     * @param renderer The renderer used to display each frame of the animation.
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

