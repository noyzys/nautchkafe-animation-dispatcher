package dev.nautchkafe.animation;

import org.bukkit.entity.Player;

import java.time.Duration;

/**
 * Represents a keyframe animation which can be displayed to a player.
 * This interface provides methods to retrieve keyframes of the animation
 * and to display the animation using a specified renderer.
 */
public interface KeyframeAnimation extends KeyframeSource {

    /**
     * Displays the animation to a specified player using a given renderer.
     *
     * @param player the player to whom the animation is to be displayed
     * @param tickDelay the duration of delay between each tick
     */
    void display(final Player player, final Duration tickDelay);
}
