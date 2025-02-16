package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AchievementListener implements Listener {

    private final TextChannel eventsChannel;

    public AchievementListener(TextChannel eventsChannel) {
        this.eventsChannel = eventsChannel;
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        String playerName = event.getPlayer().getName();
        String advancement = event.getAdvancement().getKey().toString();
        String message = String.format("**%s** ha conseguido el logro: %s", playerName, advancement);
        eventsChannel.sendMessage(message).queue();
    }
}
