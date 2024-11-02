package dev.nautchkafe.animation;

import io.vavr.Function1;

/**
 * A record that represents a Keyframe with a title and subtitle message.
 */
public record Keyframe(String titleMessage, String subtitleMessage) {

    /**
     * Modifies the title of this Keyframe using the provided titleModifier function.
     *
     * @param titleModifier The function to apply to the title message.
     * @return a new {@code Keyframe} instance with modified title and the same subtitle.
     */
    Keyframe withTitle(final Function1<String, String> titleModifier) {
        return new Keyframe(titleModifier.apply(titleMessage), subtitleMessage);
    }

    /**
     * Modifies the subtitle of this Keyframe using the provided subtitleModifier function.
     *
     * @param subtitleModifier The function to apply to the subtitle message.
     * @return a new {@code Keyframe} instance with the same title and modified subtitle.
     */
    Keyframe withSubtitle(final Function1<String, String> subtitleModifier) {
        return new Keyframe(titleMessage, subtitleModifier.apply(subtitleMessage));
    }
}
