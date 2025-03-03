// AchievementListener.java
package com.ejemplo.discordintegrator;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class AchievementListener implements Listener {
    private final TextChannel eventsChannel;
    private final List<String> VALID_CATEGORIES = Arrays.asList("story", "nether", "end", "adventure", "husbandry");

    public AchievementListener(TextChannel eventsChannel) {
        this.eventsChannel = eventsChannel;
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        String[] parts = event.getAdvancement().getKey().getKey().split("/");
        String category = parts[0];

        if (!VALID_CATEGORIES.contains(category))
            return; // Ignorar recetas

        String advancementName = formatAdvancementName(parts[parts.length - 1]);
        String emoji = getEmojiByCategory(category);

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(getColorByCategory(category))
                .setDescription(String.format(
                        "%s **%s** ha obtenido el logro: **%s**",
                        emoji, event.getPlayer().getName(), advancementName));

        eventsChannel.sendMessageEmbeds(embed.build()).queue();
    }

    private String formatAdvancementName(String key) {
        return Arrays.stream(key.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    // Método getEmojiByCategory (AchievementListener.java)
    private String getEmojiByCategory(String category) {
        switch (category) {
            case "story":
                return ":book:";
            case "nether":
                return ":fire:";
            case "end":
                return ":dragon:";
            case "adventure":
                return ":crossed_swords:";
            case "husbandry":
                return ":wheat:";
            default:
                return ":star:";
        }
    }

    // Método getColorByCategory (AchievementListener.java)
    private Color getColorByCategory(String category) {
        switch (category) {
            case "story":
                return Color.CYAN;
            case "nether":
                return Color.RED;
            case "end":
                return Color.MAGENTA;
            case "adventure":
                return Color.GREEN;
            case "husbandry":
                return Color.ORANGE;
            default:
                return Color.WHITE;
        }
    }
}