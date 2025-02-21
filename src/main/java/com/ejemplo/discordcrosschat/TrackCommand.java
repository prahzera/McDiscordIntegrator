package com.ejemplo.discordcrosschat;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TrackCommand extends ListenerAdapter implements Listener {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<String, ScheduledFuture<?>> trackingTasks = new HashMap<>(); // Mapeo de jugadores a tareas de seguimiento
    private final ItemEmojiMapper itemEmojiMapper; // Instancia de ItemEmojiMapper

    public TrackCommand() {
        this.itemEmojiMapper = new ItemEmojiMapper(); // Inicializa el mapeo de ítems
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("track"))
            return;
    
        OptionMapping playerOption = event.getOption("usuario");
        if (playerOption == null) {
            event.reply("Debes especificar un jugador.").setEphemeral(true).queue();
            return;
        }
    
        String playerName = playerOption.getAsString();
        Player player = Bukkit.getPlayer(playerName);
    
        if (player == null) {
            event.reply("Jugador no encontrado.").setEphemeral(true).queue();
            return;
        }
    
        MessageEmbed initialEmbed = createPlayerEmbed(player);
    
        // Crear botones
        Button stopButton = Button.danger("stop_tracking", "Detener Trackeo");
        Button refreshButton = Button.primary("refresh_tracking", "Actualizar");
    
        // Enviar el mensaje con los botones
        event.replyEmbeds(initialEmbed)
                .addActionRow(stopButton, refreshButton) // Agrega los botones en una fila
                .queue(hook -> {
                    hook.retrieveOriginal().queue(message -> startTracking(message, player));
                });
    }

    private void startTracking(Message message, Player player) {
        Runnable task = () -> {
            if (player.isOnline()) {
                MessageEmbed updatedEmbed = createPlayerEmbed(player);
                message.editMessageEmbeds(updatedEmbed).queue();
            }
        };

        ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        trackingTasks.put(player.getName(), scheduledTask);
    }

    public MessageEmbed createPlayerEmbed(Player player) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(":satellite: Tracking de Jugador")
                .setColor(Color.GREEN)
                .addField("Jugador", player.getName(), true)
                .addField("Coordenadas", String.format("[%d, %d, %d]",
                        player.getLocation().getBlockX(),
                        player.getLocation().getBlockY(),
                        player.getLocation().getBlockZ()), true)
                .addField("Vida", String.format("%.1f :heart:", player.getHealth()), true)
                .addField("Hambre", String.format("%d :hamburger:", player.getFoodLevel()), true)
                .addField("Nivel", String.format("%d :star:", player.getLevel()), true);
    
        PlayerInventory inventory = player.getInventory();
        StringBuilder items = new StringBuilder();
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                String emoji = itemEmojiMapper.getEmojiForItem(item.getType().name());
                items.append(emoji).append(" ").append(item.getAmount()).append("x ").append(item.getType().name()).append("\n");
            }
        }
    
        embed.addField("Inventario", items.toString(), false);
    
        return embed.build();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ScheduledFuture<?> task = trackingTasks.remove(player.getName());
        if (task != null) {
            task.cancel(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScheduledFuture<?> task = trackingTasks.get(player.getName());
        if (task != null) {
            task.cancel(true);
            trackingTasks.remove(player.getName());
        }
        // Aquí podrías reiniciar el seguimiento si es necesario
    }
}