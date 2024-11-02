package dev.nautchkafe.animation;

import dev.nautchkafe.animation.impl.CountdownAnimation;
import dev.nautchkafe.animation.impl.CustomCharacterAnimation;
import dev.nautchkafe.animation.impl.LoadingProgressAnimation;
import dev.nautchkafe.animation.impl.SpinningSlashAnimation;
import dev.nautchkafe.animation.impl.WobbleTextAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;

public final class KeyframeAnimationPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final KeyframeAnimationMessageConfig config = new KeyframeAnimationMessageConfig(
                "Title Message",
                "Subtitle Message",
                "",
                5
        );

        KeyframeAnimationDispatcher.of(player, config,
                KeyframeRenderer.miniMessageRenderer(),
                Duration.ofSeconds(2)
        );

        // Countdown animation (reversed)
        final KeyframeAnimation countdownAnimation = new CountdownAnimation(config);
        countdownAnimation.display(player, Duration.ofMillis(200), KeyframeRenderer.miniMessageRenderer());

        // Loading animation
        final KeyframeAnimation loadingBarAnimation = new LoadingProgressAnimation(config);
        loadingBarAnimation.display(player, Duration.ofMillis(200), KeyframeRenderer.miniMessageRenderer());

        // Spinning animation
        final KeyframeAnimation spinningSlashAnimation = new SpinningSlashAnimation(config);
        spinningSlashAnimation.display(player, Duration.ofMillis(200), KeyframeRenderer.miniMessageRenderer());

        // Wobble animation
        final KeyframeAnimation wobbleTextAnimation = new WobbleTextAnimation(config);
        wobbleTextAnimation.display(player, Duration.ofMillis(200), KeyframeRenderer.miniMessageRenderer());

        // Custom animation
        final KeyframeAnimation customCharAnimation = new CustomCharacterAnimation(config);
        customCharAnimation.display(player, Duration.ofMillis(200), KeyframeRenderer.miniMessageRenderer());
    }
}


