package dev.nautchkafe.animation.impl;

import io.vavr.collection.List;

/**
 * AnimationCharacter is a utility class that provides predefined sets of characters
 * for loading and spinning animations in a console application.
 */
public final class AnimationCharacter {

    private AnimationCharacter() {
    }

    /**
     * LOADING_CHARACTERS represents sequences of dots used for simulating a loading animation.
     */
    public static final List<String> LOADING_CHARACTERS = List.of(".", "..", "...");

    /**
     * SPINNING_CHARACTERS represents sequences of characters used for simulating a spinning animation.
     */
    public static final List<String> SPINNING_CHARACTERS = List.of("|", "/", "-", "\\");
}
