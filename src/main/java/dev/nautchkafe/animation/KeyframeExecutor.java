package dev.nautchkafe.animation;

import io.vavr.collection.List;
import org.bukkit.entity.Player;

import java.time.Duration;

/**
 * Functional Interface that defines a method for executing key frames over a specified duration for a player.
 */
@FunctionalInterface
public interface KeyframeExecutor {

    /**
     * Executes a sequence of key frames for the given player with a specified tick delay.
     *
     * @param player The player on whom the key frames should be executed.
     * @param keyframes The list of keyframes to be executed.
     * @param tickDelay The delay between each keyframe execution.
     */
    void execute(final Player player, final List<Keyframe> keyframes, final Duration tickDelay);
}
