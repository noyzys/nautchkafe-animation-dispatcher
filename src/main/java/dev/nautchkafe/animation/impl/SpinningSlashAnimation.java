package dev.nautchkafe.animation.impl;

import dev.nautchkafe.animation.*;
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
    private final KeyframeAnimationPlugin plugin;
    private final KeyframeMapper mapper;

    public SpinningSlashAnimation(final KeyframeAnimationMessageConfig messageConfig,
                                  final KeyframeAnimationPlugin plugin,
                                  final KeyframeMapper mapper) {
        this.messageConfig = messageConfig;
        this.plugin = plugin;
        this.mapper = mapper;
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
                .map(i -> mapper.findOrCache(i, index ->
                        new Keyframe(messageConfig.titleMessage(), AnimationCharacter.SPINNING_CHARACTERS.get(index))));
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