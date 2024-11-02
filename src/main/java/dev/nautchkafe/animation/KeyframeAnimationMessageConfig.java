package dev.nautchkafe.animation;

/**
 * Represents a configuration for a keyframe animation message which includes
 * the title, subtitle, character involved, and the number of frames.
 *
 * @param titleMessage The main message to be displayed in the keyframe animation.
 * @param subtitleMessage The secondary message to be displayed in the keyframe animation.
 * @param character The character involved in the keyframe animation.
 * @param numberOfFrames The number of frames that the animation consists of.
 */
public record KeyframeAnimationMessageConfig(
        String titleMessage,
        String subtitleMessage,
        String character,
        int numberOfFrames) {
}
