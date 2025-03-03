package com.ejemplo.discordintegrator;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        String originalMessage = event.getMessage();
        String dimension = getDimension(player.getWorld().getEnvironment());
        ChatColor dimensionColor = getDimensionColor(player.getWorld().getEnvironment());

        // Formatear para Minecraft
        String minecraftPrefix = dimensionColor + "[" + dimension + "] " + ChatColor.RESET;
        event.setFormat(minecraftPrefix + "%s: " + ChatColor.WHITE + "%s"); // %s = jugador, %s = mensaje

        // Formatear para Discord (sin códigos de color)
        String discordMessage = String.format(
                "**[%s]** **%s**: %s", // Nombres en negrita
                dimension,
                player.getName(),
                originalMessage);
        chatChannel.sendMessage(discordMessage).queue();
    }

    private String getDimension(World.Environment environment) {
        switch (environment) {
            case NETHER:
                return "Nether";
            case THE_END:
                return "End";
            default:
                return "Overworld";
        }
    }

    private ChatColor getDimensionColor(World.Environment environment) {
        switch (environment) {
            case NETHER:
                return ChatColor.RED;
            case THE_END:
                return ChatColor.LIGHT_PURPLE;
            default:
                return ChatColor.GREEN;
        }
    }
}