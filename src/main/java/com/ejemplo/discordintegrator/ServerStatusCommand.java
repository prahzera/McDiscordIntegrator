package com.ejemplo.discordintegrator;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Executors;
import java.util.Map;
import java.util.HashMap;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ServerStatusCommand extends ListenerAdapter {

    private static final Map<String, ScheduledFuture<?>> updateTasks = new HashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private MessageEmbed createStatusEmbed() {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(":globe_with_meridians: Estado del Servidor")
                .setColor(Color.BLUE)
                .setTimestamp(new Date().toInstant());
        
        // Información general del servidor
        embed.addField(":desktop: Versión del Servidor", 
                Bukkit.getVersion() + "\n" + Bukkit.getBukkitVersion(), 
                true);
        
        embed.addField(":clock1: Tiempo de Ejecución", 
                formatUptime(ManagementFactory.getRuntimeMXBean().getUptime()), 
                true);
        
        // Información de memoria
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed() / (1024 * 1024);
        long maxMemory = heapMemoryUsage.getMax() / (1024 * 1024);
        
        embed.addField(":bar_chart: Memoria", 
                usedMemory + "MB / " + maxMemory + "MB", 
                true);
        
        // Información de jugadores
        embed.addField(":busts_in_silhouette: Jugadores Online",
                Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
                true);
        
        // Información de TPS (Ticks Por Segundo)
        double[] tps;
        try {
            // Intentamos obtener TPS usando reflexión ya que no es accesible directamente
            Object serverInstance = getServerInstance();
            if (serverInstance != null) {
                tps = (double[]) serverInstance.getClass().getField("recentTps").get(serverInstance);
                embed.addField(":zap: TPS (Rendimiento)", 
                        String.format("%.2f, %.2f, %.2f", tps[0], tps[1], tps[2]), 
                        true);
            } else {
                embed.addField(":zap: TPS (Rendimiento)", 
                        "No disponible", 
                        true);
            }
        } catch (Exception e) {
            embed.addField(":zap: TPS (Rendimiento)", 
                    "No disponible", 
                    true);
        }
        
        // Información de plugins
        StringBuilder pluginsInfo = new StringBuilder();
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();
        pluginsInfo.append("Total: ").append(plugins.length).append("\n");
        
        int enabledCount = 0;
        for (Plugin plugin : plugins) {
            if (plugin.isEnabled()) {
                enabledCount++;
            }
        }
        
        pluginsInfo.append("Activos: ").append(enabledCount).append("\n");
        pluginsInfo.append("Inactivos: ").append(plugins.length - enabledCount);
        
        embed.addField(":electric_plug: Plugins", pluginsInfo.toString(), true);
        
        // Información de mundos
        StringBuilder worldsInfo = new StringBuilder();
        Bukkit.getWorlds().forEach(world -> {
            worldsInfo.append(world.getName())
                    .append(" (").append(world.getLoadedChunks().length).append(" chunks)\n");
        });
        
        embed.addField(":earth_americas: Mundos", worldsInfo.toString(), false);

        // Información de jugadores (como antes)
        Bukkit.getOnlinePlayers().forEach(player -> {
            String timePlayed = formatTime(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 60);
            String location = String.format(
                    "%s [%d, %d, %d]",
                    player.getWorld().getName(),
                    player.getLocation().getBlockX(),
                    player.getLocation().getBlockY(),
                    player.getLocation().getBlockZ());

            embed.addField(
                    ":bust_in_silhouette: " + player.getName(),
                    "```yaml\n" +
                            "Tiempo jugado: " + timePlayed + "\n" +
                            "Ubicación: " + location + "\n" +
                            "Ping: " + player.getPing() + "ms\n" +
                            "Modo de juego: " + player.getGameMode() + "```",
                    false);
        });

        return embed.build();
    }

    private void startUpdating(Message message) {
        String messageId = message.getId();
        stopUpdating(messageId);

        Runnable updateTask = () -> {
            message.editMessageEmbeds(createStatusEmbed()).queue(null, error -> {
                stopUpdating(messageId);
            });
        };

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(updateTask, 1, 1, TimeUnit.SECONDS);
        updateTasks.put(messageId, future);
    }

    private void stopUpdating(String messageId) {
        ScheduledFuture<?> task = updateTasks.remove(messageId);
        if (task != null) {
            task.cancel(false);
        }
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("status"))
            return;

        event.replyEmbeds(createStatusEmbed()).queue(message -> {
            message.retrieveOriginal().queue(originalMessage -> {
                startUpdating(originalMessage);
            });
        });
    }

    private String formatTime(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(minutes);
        long days = TimeUnit.HOURS.toDays(hours);
        return String.format("%dd %02dh %02dm", days, hours % 24, minutes % 60);
    }
    
    private String formatUptime(long uptimeMillis) {
        long uptimeDays = TimeUnit.MILLISECONDS.toDays(uptimeMillis);
        long uptimeHours = TimeUnit.MILLISECONDS.toHours(uptimeMillis) % 24;
        long uptimeMinutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMillis) % 60;
        
        return String.format("%dd %02dh %02dm", uptimeDays, uptimeHours, uptimeMinutes);
    }
    
    private Object getServerInstance() {
        try {
            // Intentamos obtener la instancia del servidor para acceder a TPS
            Class<?> craftServerClass = Bukkit.getServer().getClass();
            Object craftServer = craftServerClass.cast(Bukkit.getServer());
            Object serverInstance = craftServerClass.getMethod("getServer").invoke(craftServer);
            return serverInstance;
        } catch (Exception e) {
            return null;
        }
    }
}
