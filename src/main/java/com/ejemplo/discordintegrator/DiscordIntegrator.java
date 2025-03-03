package com.ejemplo.discordintegrator;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DiscordIntegrator extends JavaPlugin {

    private JDA jda;
    private TextChannel chatChannel;
    private TextChannel eventsChannel;
    private Map<String, String> emojiMap; // Mapea el nombre del emoji a su ID
    private String botToken; // Almacenamos el token para usarlo en las peticiones HTTP

    private static DiscordIntegrator instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        emojiMap = new HashMap<>();

        getLogger().info("\u001B[36m+===========================================+\u001B[0m");
        getLogger().info("\u001B[36m|\u001B[0m       \u001B[32mDiscordIntegrator\u001B[0m - \u001B[33mStarting...\u001B[0m     \u001B[36m|\u001B[0m");
        getLogger().info("\u001B[36m+-------------------------------------------+\u001B[0m");

        String token = getConfig().getString("discord-token");
        String chatChannelId = getConfig().getString("discord-chat-channel-id");
        String eventsChannelId = getConfig().getString("discord-events-channel-id");
        this.botToken = token; // Guardamos el token en un campo

        if (token == null || token.trim().isEmpty() ||
            chatChannelId == null || chatChannelId.trim().isEmpty() ||
            eventsChannelId == null || eventsChannelId.trim().isEmpty()) {
            getLogger().severe("\u001B[31m[X] Token o IDs de canales no configurados en config.yml\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            getLogger().info("\u001B[33m[*] Conectando a Discord...\u001B[0m");
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .build();
            jda.awaitReady();
            getLogger().info("\u001B[32m[✓] Conexión con Discord establecida!\u001B[0m");

            // Cargar los emojis de la aplicación usando el endpoint REST
            loadEmojis();
        } catch (Exception e) {
            getLogger().severe("\u001B[31m[X] Error conectando a Discord: " + e.getMessage() + "\u001B[0m");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("\u001B[33m[*] Buscando canales de Discord...\u001B[0m");
        chatChannel = jda.getTextChannelById(chatChannelId);
        eventsChannel = jda.getTextChannelById(eventsChannelId);

        if (chatChannel == null || eventsChannel == null) {
            getLogger().severe("\u001B[31m[X] Canales de Discord especificados en config.yml no encontrados.\u001B[0m");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        getLogger().info("\u001B[32m[✓] Canales de Discord encontrados!\u001B[0m");

        getLogger().info("\u001B[33m[*] Registrando listeners...\u001B[0m");
        jda.addEventListener(new DiscordListener(this));
        jda.addEventListener(new ServerStatusCommand());
        jda.addEventListener(new TrackCommand());
        jda.addEventListener(new ButtonListener());
        jda.addEventListener(new ModalListener());

        // Registro de listeners de Bukkit
        TrackCommand trackCommand = new TrackCommand();
        getServer().getPluginManager().registerEvents(new GameChatListener(chatChannel), this);
        getServer().getPluginManager().registerEvents(new DeathListener(eventsChannel), this);
        getServer().getPluginManager().registerEvents(new AchievementListener(eventsChannel), this);
        getServer().getPluginManager().registerEvents(trackCommand, this);
        getLogger().info("\u001B[32m[✓] Listeners registrados correctamente!\u001B[0m");

        getLogger().info("\u001B[33m[*] Configurando comandos de Discord...\u001B[0m");
        jda.upsertCommand("status", "Muestra el estado del servidor").queue();
        jda.upsertCommand("track", "Trackea a un jugador")
                .addOption(OptionType.STRING, "usuario", "Nombre del jugador a trackear", true)
                .queue();
        getLogger().info("\u001B[32m[✓] Comandos de Discord configurados!\u001B[0m");

        getLogger().info("\u001B[36m+===========================================+\u001B[0m");
        getLogger().info("\u001B[36m|\u001B[0m       \u001B[32mDiscordIntegrator\u001B[0m - \u001B[32mEnabled!\u001B[0m        \u001B[36m|\u001B[0m");
        getLogger().info("\u001B[36m+===========================================+\u001B[0m");
    }

    @Override
    public void onDisable() {
        getLogger().info("\u001B[36m+===========================================+\u001B[0m");
        getLogger().info("\u001B[36m|\u001B[0m     \u001B[32mDiscordIntegrator\u001B[0m - \u001B[31mApagando...\u001B[0m  \u001B[36m|\u001B[0m");
        getLogger().info("\u001B[36m+-------------------------------------------+\u001B[0m");

        if (jda != null) {
            getLogger().info("\u001B[33m[*] Cerrando conexión con Discord...\u001B[0m");
            jda.shutdown();
            getLogger().info("\u001B[32m[✓] Conexión cerrada correctamente!\u001B[0m");
        }

        getLogger().info("\u001B[36m+===========================================+\u001B[0m");
        getLogger().info("\u001B[36m|\u001B[0m      \u001B[32mDiscordIntegrator\u001B[0m - \u001B[31mDisabled!\u001B[0m        \u001B[36m|\u001B[0m");
        getLogger().info("\u001B[36m+===========================================+\u001B[0m");
    }

    // Nuevo método para cargar los emojis de la aplicación usando java.net.http.HttpClient
    private void loadEmojis() {
        getLogger().info("\u001B[33m[*] Cargando emojis de la aplicación...\u001B[0m");
        String url = "https://discord.com/api/v10/applications/" + jda.getSelfUser().getId() + "/emojis";
        
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bot " + botToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getLogger().severe("\u001B[31m[X] Error al cargar emojis de la aplicación: " + e.getMessage() + "\u001B[0m");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    try {
                        JSONObject json = new JSONObject(body);
                        JSONArray emojisArray = json.getJSONArray("items");
                        int count = 0;
                        for (int i = 0; i < emojisArray.length(); i++) {
                            JSONObject emojiJson = emojisArray.getJSONObject(i);
                            String emojiName = emojiJson.getString("name").toLowerCase();
                            String emojiId = emojiJson.getString("id");
                            emojiMap.put(emojiName, emojiId);
                            count++;
                        }
                        getLogger().info("\u001B[32m[✓] Se han cargado " + count + " emojis de la aplicación!\u001B[0m");
                    } catch (Exception e) {
                        getLogger().severe("\u001B[31m[X] Error parseando emojis: " + e.getMessage() + "\u001B[0m");
                    }
                } else {
                    getLogger().severe("\u001B[31m[X] Error al cargar emojis. Código: " + response.code() + "\u001B[0m");
                }
                response.close();
            }
        });
    }

    // Devuelve el emoji formateado para usar en los mensajes.
    public String getEmojiFormatted(String emojiName) {
        String emojiId = emojiMap.get(emojiName.toLowerCase());
        if (emojiId != null) {
            return "<:" + emojiName.toLowerCase() + ":" + emojiId + ">";
        }
        return ":" + emojiName.toLowerCase() + ":";
    }

    public TextChannel getChatChannel() {
        return chatChannel;
    }

    public JDA getJDA() {
        return jda;
    }

    public static DiscordIntegrator getInstance() {
        return instance;
    }

    public Map<String, String> getEmojiMap() {
        return emojiMap;
    }
}
