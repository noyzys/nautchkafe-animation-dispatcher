package dev.nautchkafe.animation;

import io.vavr.collection.List;

import java.util.Objects;

/**
 * Represents a configuration for a keyframe animation message which includes
 * the title, subtitle, character involved, and the number of frames.
 */
public final class KeyframeAnimationMessageConfig {

    private final String titleMessage;
    private final String subtitleMessage;
    private final String character;
    private final int numberOfFrames;
    private final List<Keyframe> customKeyframes;

    /**
     * @param titleMessage    The main message to be displayed in the keyframe animation.
     * @param subtitleMessage The secondary message to be displayed in the keyframe animation.
     * @param character       The character involved in the keyframe animation.
     * @param numberOfFrames  The number of frames that the animation consists of.
     */
    public KeyframeAnimationMessageConfig(final String titleMessage, final String subtitleMessage, final String character,
                                          final int numberOfFrames, final List<Keyframe> customKeyframes) {
        this.titleMessage = titleMessage;
        this.subtitleMessage = subtitleMessage;
        this.character = character;
        this.numberOfFrames = numberOfFrames;
        this.customKeyframes = customKeyframes;
    }

    public KeyframeAnimationMessageConfig(final String titleMessage, final String subtitleMessage, final String character,
                                          final int numberOfFrames) {
        this(titleMessage, subtitleMessage, character, numberOfFrames, List.empty());
    }

    /**
     * Fetches title message for the keyframes.
     * @return title message as a String.
     */
    public String titleMessage() {
        return titleMessage;
    }

    /**
     * Fetches subtitle message for the keyframes.
     * @return Subtitle message as a String.
     */
    public String subtitleMessage() {
        return subtitleMessage;
    }

    /**
     * Fetches character for the keyframes.
     * @return character as a String.
     */
    public String character() {
        return character;
    }

    /**
     * Returns the number of frames.
     *
     * @return the number of frames as an integer.
     */
    public int numberOfFrames() {
        return numberOfFrames;
    }

    /**
     * Returns the appropriate list of keyframes, either a custom list if non-empty, or a generated default list.
     * @return A list of {@link Keyframe} objects.
     */
    public List<Keyframe> customKeyframes() {
        return customKeyframes.isEmpty() ? generateDefaultKeyframes() : customKeyframes;
    }

    /**
     * Generates a default list of keyframes.
     * @return A list of generated {@link Keyframe} objects with default settings.
     */
    private List<Keyframe> generateDefaultKeyframes() {
        final String subTitleWithCharacter = subtitleMessage() + " " + character();
        return List.range(0, numberOfFrames)
                .map(i -> new Keyframe(titleMessage, subTitleWithCharacter));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        var that = (KeyframeAnimationMessageConfig) obj;
        return Objects.equals(this.titleMessage, that.titleMessage) &&
                Objects.equals(this.subtitleMessage, that.subtitleMessage) &&
                Objects.equals(this.character, that.character) &&
                this.numberOfFrames == that.numberOfFrames &&
                Objects.equals(this.customKeyframes, that.customKeyframes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titleMessage, subtitleMessage, character, numberOfFrames, customKeyframes);
    }

    @Override
    public String toString() {
        return "KeyframeAnimationMessageConfig[" +
                "titleMessage=" + titleMessage + ", " +
                "subtitleMessage=" + subtitleMessage + ", " +
                "character=" + character + ", " +
                "numberOfFrames=" + numberOfFrames + ", " +
                "customKeyframes=" + customKeyframes + ']';
    }

}
