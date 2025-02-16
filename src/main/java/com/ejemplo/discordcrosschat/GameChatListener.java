package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GameChatListener implements Listener {

    private final TextChannel chatChannel;

    public GameChatListener(TextChannel chatChannel) {
        this.chatChannel = chatChannel;

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        // Formatea el mensaje (puedes ajustar el formato a tu gusto)
        String formattedMessage = String.format("**%s**: %s", playerName, message);
        chatChannel.sendMessage(formattedMessage).queue();
    }
}
