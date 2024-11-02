package dev.nautchkafe.animation;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

import java.time.Duration;

/**
 * This functional interface is designed for rendering keyframes.
 */
@FunctionalInterface
public interface KeyframeRenderer {

    /**
     * Render a keyframe to an audience with a specified delay.
     *
     * @param audience The audience to which the keyframe will be rendered.
     * @param keyframe The keyframe to be rendered.
     * @param tickDelay The delay between ticks.
     */
    void render(final Audience audience, final Keyframe keyframe, final Duration tickDelay);

    /**
     * Provides a default implementation of KeyframeRenderer that uses MiniMessage formatting.
     *
     * @return A MiniMessage based KeyframeRenderer implementation.
     */
    static KeyframeRenderer miniMessageRenderer() {
        return (audience, frame, tickDelay) -> {
            final MiniMessage miniMessage = MiniMessage.miniMessage();
            final Component title = miniMessage.deserialize(frame.titleMessage());
            final Component subtitle = miniMessage.deserialize(frame.subtitleMessage());

            final Duration fadeIn = Duration.ofMillis(500);
            final Duration fadeOut = Duration.ofMillis(500);
            final Title.Times titleFlowTime = Title.Times.times(fadeIn, tickDelay, fadeOut);
            audience.showTitle(Title.title(title, subtitle, titleFlowTime));
        };
    }
}
