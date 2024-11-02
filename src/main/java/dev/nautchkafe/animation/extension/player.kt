package dev.nautchkafe.animation.extension

import dev.nautchkafe.animation.Keyframe
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import java.time.Duration

fun Audience.renderKeyframe(frame: Keyframe, tickDelay: Duration) {
    val miniMessage = MiniMessage.miniMessage()

    val title: Component = miniMessage.deserialize(frame.titleMessage())
    val subtitle: Component = miniMessage.deserialize(frame.subtitleMessage())

    val fadeIn = Duration.ofMillis(500)
    val fadeOut = Duration.ofMillis(500)
    val titleFlowTime = Title.Times.times(fadeIn, tickDelay, fadeOut)

    this.showTitle(Title.title(title, subtitle, titleFlowTime))
}