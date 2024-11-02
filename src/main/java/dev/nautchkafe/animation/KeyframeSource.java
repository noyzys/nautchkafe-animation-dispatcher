package dev.nautchkafe.animation;

import io.vavr.collection.List;

import java.util.function.Supplier;

/**
 * Represents a source of keyframes.
 * <p>
 * This functional interface is intended to be implemented by any class
 * that can provide a list of {@code Keyframe} objects.
 * </p>
 *
 * <p>
 * The implementing class should define the logic for obtaining or generating
 * the list of keyframes that it can provide.
 * </p>
 *
 * <p><strong>Example Usage:</strong></p>
 * <pre>
 * {@code
 * KeyframeSource keyframeSource = () -> {
 * // Logic to generate or retrieve keyframes
 * return List.of(new Keyframe(...));
 * };
 * List<Keyframe> keyframes = keyframeSource.frames();
 * }
 * </pre>
 *
 * @see Keyframe
 */
@FunctionalInterface
interface KeyframeSource {

    /**
     * Retrieves the keyframes of the animation for a given number of cycles.
     *
     * @param cycles the number of animation cycles
     * @return a Supplier that provides a List of Keyframes
     */
    Supplier<List<Keyframe>> frames(final int cycles);
}
