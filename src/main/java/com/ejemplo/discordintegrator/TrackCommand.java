package com.ejemplo.discordintegrator;

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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TrackCommand extends ListenerAdapter implements Listener {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // Mapeo de jugadores a tareas de seguimiento y mensajes de tracking.
    private static final Map<String, ScheduledFuture<?>> trackingTasks = new HashMap<>();
    private static final Map<String, Message> trackingMessages = new HashMap<>();

    public TrackCommand() {
        // Constructor sin inicialización adicional.
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("track"))
            return;

        // Defer reply para evitar timeout.
        event.deferReply().queue();

        OptionMapping playerOption = event.getOption("usuario");
        if (playerOption == null) {
            event.getHook().editOriginal("Debes especificar un jugador.").queue();
            return;
        }

        String playerName = playerOption.getAsString();
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            event.getHook().editOriginal("Jugador no encontrado.").queue();
            return;
        }

        boolean isAlreadyTracking = isPlayerTracked(player.getName());
        MessageEmbed initialEmbed = createPlayerEmbed(player, isAlreadyTracking);

        // Crear botones según el estado actual.
        Button actionButton = isAlreadyTracking ?
                Button.danger("stop_tracking", "Detener Trackeo") :
                Button.success("start_tracking", "Iniciar Trackeo");
        Button kickButton = Button.danger("kick_player", "Expulsar Jugador");

        event.getHook().editOriginalEmbeds(initialEmbed)
                .setActionRow(actionButton, kickButton)
                .queue(message -> {
                    // Guardar referencia al mensaje.
                    trackingMessages.put(player.getName(), message);
                    // Si ya se estaba trackeando, reiniciamos el tracking.
                    if (isAlreadyTracking) {
                        stopTracking(player.getName());
                        startTracking(message, player);
                    }
                });
    }

    // Comprueba si un jugador ya está siendo trackeado.
    public static boolean isPlayerTracked(String playerName) {
        return trackingTasks.containsKey(playerName);
    }

    // Inicia el tracking del jugador y actualiza el mensaje cada segundo.
    public static void startTracking(Message message, Player player) {
        stopTracking(player.getName());
        Runnable task = () -> {
            if (player.isOnline()) {
                MessageEmbed updatedEmbed = new TrackCommand().createPlayerEmbed(player, true);
                Button stopButton = Button.danger("stop_tracking", "Detener Trackeo");
                Button kickButton = Button.danger("kick_player", "Expulsar Jugador");
                message.editMessageEmbeds(updatedEmbed)
                        .setActionRow(stopButton, kickButton)
                        .queue();
            } else {
                stopTracking(player.getName());
            }
        };
        ScheduledFuture<?> scheduledTask = scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        trackingTasks.put(player.getName(), scheduledTask);
        trackingMessages.put(player.getName(), message);
    }

    // Detiene el tracking del jugador y actualiza el mensaje con botones para iniciar.
    public static void stopTracking(String playerName) {
        ScheduledFuture<?> task = trackingTasks.remove(playerName);
        if (task != null) {
            task.cancel(true);
            Message message = trackingMessages.get(playerName);
            Player player = Bukkit.getPlayer(playerName);
            if (message != null && player != null) {
                MessageEmbed updatedEmbed = new TrackCommand().createPlayerEmbed(player, false);
                Button startButton = Button.success("start_tracking", "Iniciar Trackeo");
                Button kickButton = Button.danger("kick_player", "Expulsar Jugador");
                message.editMessageEmbeds(updatedEmbed)
                        .setActionRow(startButton, kickButton)
                        .queue();
            }
        }
    }

    // Crea el embed con la información del jugador y utiliza los emojis personalizados.
    public MessageEmbed createPlayerEmbed(Player player, boolean isTracking) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("🛰️ Tracking de Jugador")
                .setColor(isTracking ? Color.GREEN : Color.RED)
                .addField("Jugador", player.getName(), true)
                .addField("Estado", isTracking ? "Rastreando 🟢" : "Inactivo 🔴", true)
                .addField("UUID", player.getUniqueId().toString(), true)
                .addField("Mundo", player.getWorld().getName(), true)
                .addField("Coordenadas", String.format("[%d, %d, %d]",
                        player.getLocation().getBlockX(),
                        player.getLocation().getBlockY(),
                        player.getLocation().getBlockZ()), true)
                .addField("Dirección", getCardinalDirection(player), true)
                .addField("Vida", String.format("%.1f ❤️", player.getHealth()), true)
                .addField("Hambre", String.format("%d 🍔", player.getFoodLevel()), true)
                .addField("Nivel", String.format("%d ⭐", player.getLevel()), true)
                .addField("XP", String.format("%.1f%%", player.getExp() * 100), true)
                .addField("Ping", player.getPing() + "ms", true)
                .addField("Gamemode", player.getGameMode().toString(), true)
                .addField("IP", player.getAddress().getAddress().getHostAddress(), true)
                .addField("Tiempo jugado", formatPlayTime(player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE)), true)
                .addField("Primera conexión", player.getFirstPlayed() > 0 ?
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date(player.getFirstPlayed())) :
                        "Desconocido", true);

        // Efectos de poción.
        if (!player.getActivePotionEffects().isEmpty()) {
            StringBuilder effects = new StringBuilder();
            player.getActivePotionEffects().forEach(effect -> {
                effects.append(effect.getType().getKeyOrThrow().getKey())
                        .append(" (")
                        .append(effect.getAmplifier() + 1)
                        .append(") - ")
                        .append(formatDuration(effect.getDuration()))
                        .append("\n");
            });
            embed.addField("Efectos activos", effects.toString(), false);
        }

        // Usar el emoji map del plugin para formatear ítems y armadura.
        DiscordIntegrator plugin = DiscordIntegrator.getInstance();

        // Inventario.
        PlayerInventory inventory = player.getInventory();
        StringBuilder items = new StringBuilder();
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                String itemName = item.getType().name().toLowerCase();
                String emojiId = plugin.getEmojiMap().get(itemName);
                String formattedEmoji = emojiId != null ?
                        String.format("<:%s:%s>", itemName, emojiId) :
                        String.format(":%s:", itemName);
                items.append(formattedEmoji).append(" ")
                        .append(item.getAmount()).append("x ")
                        .append(item.getType().name()).append("\n");
            }
        }
        embed.addField("Inventario", items.length() > 0 ? items.toString() : "Vacío", false);

        // Armadura.
        StringBuilder armor = new StringBuilder();
        ItemStack[] armorContents = inventory.getArmorContents();
        String[] armorSlots = {"Botas", "Pantalones", "Pechera", "Casco"};
        for (int i = 0; i < armorContents.length; i++) {
            ItemStack item = armorContents[i];
            if (item != null && !item.getType().name().equals("AIR")) {
                String armorItemName = item.getType().name().toLowerCase();
                String emojiId = plugin.getEmojiMap().get(armorItemName);
                String formattedEmoji = emojiId != null ?
                        String.format("<:%s:%s>", armorItemName, emojiId) :
                        String.format(":%s:", armorItemName);
                armor.append(formattedEmoji).append(" ")
                        .append(armorSlots[i]).append(": ")
                        .append(item.getType().name()).append("\n");
            }
        }
        if (armor.length() > 0) {
            embed.addField("Armadura", armor.toString(), false);
        }

        return embed.build();
    }

    // Método auxiliar para obtener la dirección cardinal.
    private String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) rotation += 360.0;
        if (0 <= rotation && rotation < 22.5) return "Norte";
        else if (22.5 <= rotation && rotation < 67.5) return "Noreste";
        else if (67.5 <= rotation && rotation < 112.5) return "Este";
        else if (112.5 <= rotation && rotation < 157.5) return "Sureste";
        else if (157.5 <= rotation && rotation < 202.5) return "Sur";
        else if (202.5 <= rotation && rotation < 247.5) return "Suroeste";
        else if (247.5 <= rotation && rotation < 292.5) return "Oeste";
        else if (292.5 <= rotation && rotation < 337.5) return "Noroeste";
        else return "Norte";
    }

    // Método auxiliar para formatear el tiempo jugado.
    private String formatPlayTime(int ticks) {
        int totalSeconds = ticks / 20;
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    // Método auxiliar para formatear la duración de efectos.
    private String formatDuration(int ticks) {
        int totalSeconds = ticks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        stopTracking(player.getName());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Message message = trackingMessages.get(player.getName());
        if (message != null) {
            MessageEmbed updatedEmbed = createPlayerEmbed(player, false);
            Button startButton = Button.success("start_tracking", "Iniciar Trackeo");
            Button kickButton = Button.danger("kick_player", "Expulsar Jugador");
            message.editMessageEmbeds(updatedEmbed)
                    .setActionRow(startButton, kickButton)
                    .queue();
        }
    }
}
