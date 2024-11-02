package dev.nautchkafe.animation;

import io.vavr.control.Option;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/**
 * A final class that schedules keyframe animations with specific delay intervals.
 */
final class KeyframeAnimationScheduler {

    private final Duration tickDelay;
    private Instant lastFrameTime;

    KeyframeAnimationScheduler(final Duration tickDelay) {
        this.tickDelay = tickDelay;
        this.lastFrameTime = Instant.now();
    }

    /**
     * Static factory method to create a new instance of KeyframeAnimationScheduler.
     *
     * @param tickDelay the duration between frames.
     * @return a new instance of KeyframeAnimationScheduler.
     */
    public static KeyframeAnimationScheduler create(final Duration tickDelay) {
        return new KeyframeAnimationScheduler(tickDelay);
    }

    /**
     * Updates the time of the last frame to the current time.
     */
    void withUpdatedFrameTime() {
        this.lastFrameTime = Instant.now();
    }

    /**
     * Determines if the frame should be updated based on the tick delay.
     *
     * @return true if the current time is greater than or equal to the last frame time plus tick delay.
     */
    boolean shouldUpdateFrame() {
        return Option.of(calculateTimeSinceLastFrame())
                .filter(duration -> duration.compareTo(tickDelay) >= 0)
                .isDefined();
    }

    /**
     * Calculates the time elapsed since the last frame update.
     *
     * @return the duration since the last frame update.
     */
    private Duration calculateTimeSinceLastFrame() {
        return Duration.between(lastFrameTime, Instant.now());
    }

    /**
     * Gets the tick delay for frame updates.
     *
     * @return the duration between frames.
     */
    public Duration tickDelay() {
        return tickDelay;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyframeAnimationScheduler that = (KeyframeAnimationScheduler) o;
        return Objects.equals(tickDelay, that.tickDelay) && Objects.equals(lastFrameTime, that.lastFrameTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tickDelay, lastFrameTime);
    }

    @Override
    public String toString() {
        return "KeyframeAnimationScheduler{" +
                "tickDelay=" + tickDelay +
                ", lastFrameTime=" + lastFrameTime +
                '}';
    }
}

