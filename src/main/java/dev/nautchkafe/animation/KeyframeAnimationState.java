package dev.nautchkafe.animation;

import io.vavr.collection.List;
import io.vavr.control.Option;

/**
 * Represents the state of a keyframe animation, managing its current
 * keyframe and transitioning through keyframes.
 *
 * <p>This class is immutable and uses the Option pattern to handle
 * potentially absent values without null references.</p>
 */
final class KeyframeAnimationState {

    private final List<Keyframe> keyframes;
    private final Option<Integer> currentIndex;

    KeyframeAnimationState(final List<Keyframe> keyframes) {
        this.keyframes = keyframes;
        this.currentIndex = Option.some(0);
    }

    private KeyframeAnimationState(final List<Keyframe> keyframes, final Option<Integer> currentIndex) {
        this.keyframes = keyframes;
        this.currentIndex = currentIndex;
    }

    /**
     * Gets the current keyframe if the index is within bounds.
     *
     * @return an Option containing the current keyframe if present and
     * the index is valid, or Option.none() otherwise
     */
    Option<Keyframe> currentFrame() {
        return currentIndex.flatMap(i -> i >= 0
                && i < keyframes.size()
                ? Option.of(keyframes.get(i))
                : Option.none());
    }

    /**
     * Advances the animation to the next keyframe if it exists.
     *
     * @return a new KeyframeAnimationState representing the next frame
     */
    KeyframeAnimationState nextFrame() {
        return new KeyframeAnimationState(keyframes, currentIndex.filter(i -> i < keyframes.size() - 1)
                .map(i -> i + 1));
    }

    /**
     * Determines if the animation has completed.
     *
     * @return true if the current index is beyond the last keyframe, false otherwise
     */
    boolean isFinished() {
        return currentIndex.exists(i -> i >= keyframes.size());
    }
}
