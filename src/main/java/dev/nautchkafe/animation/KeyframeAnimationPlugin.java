package dev.nautchkafe.animation;

import dev.nautchkafe.animation.impl.CountdownAnimation;
import dev.nautchkafe.animation.impl.CustomCharacterAnimation;
import dev.nautchkafe.animation.impl.LoadingProgressAnimation;
import dev.nautchkafe.animation.impl.SpinningSlashAnimation;
import dev.nautchkafe.animation.impl.WobbleTextAnimation;
import io.vavr.collection.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public final class KeyframeAnimationPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        final KeyframeAnimationMessageConfig config = produceStandardConfig(player);
        produceCustomKeyframeConfig(player);

        // Countdown animation (reversed)
        final KeyframeAnimation countdownAnimation = new CountdownAnimation(config, this);
        countdownAnimation.display(player, Duration.ofMillis(200));

        // Loading animation
        final KeyframeAnimation loadingBarAnimation = new LoadingProgressAnimation(config, this);
        loadingBarAnimation.display(player, Duration.ofMillis(200));

        // Spinning animation and use cache for expired animations
        final KeyframeMapper mapper = new KeyframeMapper();
        final KeyframeAnimation spinningSlashAnimation = new SpinningSlashAnimation(config, this, mapper);
        spinningSlashAnimation.display(player, Duration.ofMillis(200));

        // Wobble animation
        final KeyframeAnimation wobbleTextAnimation = new WobbleTextAnimation(config, this);
        wobbleTextAnimation.display(player, Duration.ofMillis(200));

        // Custom animation
        final KeyframeAnimation customCharAnimation = new CustomCharacterAnimation(config, this);
        customCharAnimation.display(player, Duration.ofMillis(200));;
    }

    private void produceCustomKeyframeConfig(final Player player) {
        // custom keyframes animation
        final List<Keyframe> customKeyFrames = List.of(
                new Keyframe("A", "A"),
                new Keyframe("B", "B"),
                new Keyframe("C", "C"),
                new Keyframe("S", "s"),
                new Keyframe("U", "u"),
                new Keyframe("S", "s")
        );

        final KeyframeAnimationMessageConfig customKeyframesConfig = new KeyframeAnimationMessageConfig(
                "Start Title",
                "Start subtitle",
                "",
                10,
                customKeyFrames
        );

        final KeyframeAnimationDispatcher customDispatcher = KeyframeAnimationDispatcher.of(player, customKeyframesConfig,
                KeyframeRenderer.miniMessageRenderer(),
                Duration.ofSeconds(20), this
        );

        customDispatcher.dispatch();
    }

    private KeyframeAnimationMessageConfig produceStandardConfig(final Player player) {
        final KeyframeAnimationMessageConfig config = new KeyframeAnimationMessageConfig(
                "Title Message",
                "Subtitle Message",
                "",
                5
        );

        final KeyframeAnimationDispatcher instantDispatcher = KeyframeAnimationDispatcher.of(player, config,
                KeyframeRenderer.miniMessageRenderer(),
                Duration.ofSeconds(10), this
        );

        instantDispatcher.dispatch();
        return config;
    }
}


