package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final TextChannel eventsChannel;

    public DeathListener(TextChannel eventsChannel) {
        this.eventsChannel = eventsChannel;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        Location loc = victim.getLocation();
        String locationStr = String.format("X: %d, Y: %d, Z: %d", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        String message;
        if (killer != null) {
            message = String.format("**%s** fue eliminado por **%s** en [%s]",
                    victim.getName(), killer.getName(), locationStr);
        } else {
            String deathMsg = (event.getDeathMessage() != null) ? event.getDeathMessage() : "sin motivo";
            message = String.format("**%s** ha muerto (%s) en [%s]",
                    victim.getName(), deathMsg, locationStr);
        }
        eventsChannel.sendMessage(message).queue();
    }
}
