package com.ejemplo.discordcrosschat;

import java.util.HashMap;
import java.util.Map;

public class ItemEmojiMapper {

    private final Map<String, String> itemEmojiMap;

    public ItemEmojiMapper() {
        itemEmojiMap = new HashMap<>();
        initializeItemEmojiMap();
    }

    private void initializeItemEmojiMap() {
        // Mapeo de ítems de Minecraft a emojis
        itemEmojiMap.put("DIAMOND_SWORD", "💎⚔️");
        itemEmojiMap.put("IRON_SWORD", "🗡️");
        itemEmojiMap.put("APPLE", "🍎");
        itemEmojiMap.put("GOLDEN_APPLE", "🍏");
        itemEmojiMap.put("DIAMOND_PICKAXE", "⛏️");
        itemEmojiMap.put("IRON_PICKAXE", "⛏️");
        itemEmojiMap.put("BOW", "🏹");
        itemEmojiMap.put("ARROW", "➡️");
        itemEmojiMap.put("SHIELD", "🛡️");
        itemEmojiMap.put("POTION", "🧪");
        itemEmojiMap.put("TNT", "💣");
        itemEmojiMap.put("ENDER_PEARL", "🔮");
        itemEmojiMap.put("ELYTRA", "🪂");
        itemEmojiMap.put("TOTEM_OF_UNDYING", "💖");
        itemEmojiMap.put("DIAMOND", "💎");
        itemEmojiMap.put("GOLD_INGOT", "🟨");
        itemEmojiMap.put("IRON_INGOT", "🟩");
        itemEmojiMap.put("EMERALD", "💚");
        itemEmojiMap.put("COAL", "🪨");
        itemEmojiMap.put("REDSTONE", "🔴");
        itemEmojiMap.put("LAPIS_LAZULI", "🔵");
        itemEmojiMap.put("OBSIDIAN", "🪵");
        itemEmojiMap.put("BOOK", "📖");
        itemEmojiMap.put("ENCHANTED_BOOK", "📚");
        itemEmojiMap.put("FISHING_ROD", "🎣");
        itemEmojiMap.put("COMPASS", "🧭");
        itemEmojiMap.put("CLOCK", "🕒");
        itemEmojiMap.put("MAP", "🗺️");
        itemEmojiMap.put("BED", "🛏️");
        itemEmojiMap.put("CAKE", "🎂");
        itemEmojiMap.put("BUCKET", "🪣");
        itemEmojiMap.put("WATER_BUCKET", "💧");
        itemEmojiMap.put("LAVA_BUCKET", "🌋");
        itemEmojiMap.put("MILK_BUCKET", "🥛");
        itemEmojiMap.put("SNOWBALL", "❄️");
        itemEmojiMap.put("EGG", "🥚");
        itemEmojiMap.put("FEATHER", "🪶");
        itemEmojiMap.put("FLINT", "🔥");
        itemEmojiMap.put("LEATHER", "🧥");
        itemEmojiMap.put("STRING", "🧵");
        itemEmojiMap.put("SPIDER_EYE", "🕷️");
        itemEmojiMap.put("BONE", "🦴");
        itemEmojiMap.put("BLAZE_ROD", "🔥");
        itemEmojiMap.put("GHAST_TEAR", "👻");
        itemEmojiMap.put("NETHER_STAR", "🌟");
        itemEmojiMap.put("ENDER_EYE", "👁️");
        itemEmojiMap.put("DRAGON_EGG", "🥚");
        itemEmojiMap.put("BEACON", "🔦");
        itemEmojiMap.put("ANVIL", "⚒️");
        itemEmojiMap.put("ENCHANTING_TABLE", "✨");
        itemEmojiMap.put("FURNACE", "🔥");
        itemEmojiMap.put("CHEST", "📦");
        itemEmojiMap.put("ENDER_CHEST", "📦");
        itemEmojiMap.put("CRAFTING_TABLE", "🪵");
        itemEmojiMap.put("BREWING_STAND", "🧪");
        itemEmojiMap.put("CAULDRON", "🍲");
        itemEmojiMap.put("HOPPER", "🪣");
        itemEmojiMap.put("RAIL", "🛤️");
        itemEmojiMap.put("MINECART", "🚂");
        itemEmojiMap.put("BOAT", "⛵");
        itemEmojiMap.put("SADDLE", "🪑");
        itemEmojiMap.put("CARROT", "🥕");
        itemEmojiMap.put("POTATO", "🥔");
        itemEmojiMap.put("BAKED_POTATO", "🍠");
        itemEmojiMap.put("BREAD", "🍞");
        itemEmojiMap.put("COOKIE", "🍪");
        itemEmojiMap.put("PUMPKIN_PIE", "🥧");
        itemEmojiMap.put("MELON", "🍈");
        itemEmojiMap.put("GLISTERING_MELON", "🍈");
        itemEmojiMap.put("HONEY_BOTTLE", "🍯");
        itemEmojiMap.put("HONEYCOMB", "🍯");
        itemEmojiMap.put("NETHERITE_INGOT", "🟧");
        itemEmojiMap.put("NETHERITE_SWORD", "⚔️");
        itemEmojiMap.put("NETHERITE_PICKAXE", "⛏️");
        itemEmojiMap.put("NETHERITE_HELMET", "🪖");
        itemEmojiMap.put("NETHERITE_CHESTPLATE", "🦺");
        itemEmojiMap.put("NETHERITE_LEGGINGS", "👖");
        itemEmojiMap.put("NETHERITE_BOOTS", "👢");
        itemEmojiMap.put("STICK", "🪵");
        itemEmojiMap.put("WOODEN_SWORD", "🪵⚔️");
        itemEmojiMap.put("WOODEN_PICKAXE", "🪵⛏️");
        itemEmojiMap.put("WOODEN_AXE", "🪵🪓");
        itemEmojiMap.put("WOODEN_SHOVEL", "🪵🪚");
        itemEmojiMap.put("WOODEN_HOE", "🪵🌾");
        itemEmojiMap.put("STONE_SWORD", "🪨⚔️");
        itemEmojiMap.put("STONE_PICKAXE", "🪨⛏️");
        itemEmojiMap.put("STONE_AXE", "🪨🪓");
        itemEmojiMap.put("STONE_SHOVEL", "🪨🪚");
        itemEmojiMap.put("STONE_HOE", "🪨🌾");
        itemEmojiMap.put("GOLDEN_SWORD", "🟨⚔️");
        itemEmojiMap.put("GOLDEN_PICKAXE", "🟨⛏️");
        itemEmojiMap.put("GOLDEN_AXE", "🟨🪓");
        itemEmojiMap.put("GOLDEN_SHOVEL", "🟨🪚");
        itemEmojiMap.put("GOLDEN_HOE", "🟨🌾");
        itemEmojiMap.put("DIAMOND_AXE", "💎🪓");
        itemEmojiMap.put("DIAMOND_SHOVEL", "💎🪚");
        itemEmojiMap.put("DIAMOND_HOE", "💎🌾");
        itemEmojiMap.put("NETHERITE_AXE", "🟧🪓");
        itemEmojiMap.put("NETHERITE_SHOVEL", "🟧🪚");
        itemEmojiMap.put("NETHERITE_HOE", "🟧🌾");
        itemEmojiMap.put("FLINT_AND_STEEL", "🔥🪨");
        itemEmojiMap.put("SHEARS", "✂️");
        itemEmojiMap.put("LEAD", "🧶");
        itemEmojiMap.put("NAME_TAG", "🏷️");
        itemEmojiMap.put("PAINTING", "🖼️");
        itemEmojiMap.put("ITEM_FRAME", "🖼️");
        itemEmojiMap.put("ARMOR_STAND", "🕴️");
        itemEmojiMap.put("END_CRYSTAL", "💎✨");
        itemEmojiMap.put("FIREWORK_ROCKET", "🚀");
        itemEmojiMap.put("FIREWORK_STAR", "✨");
        itemEmojiMap.put("GLASS_BOTTLE", "🍶");
        itemEmojiMap.put("EXPERIENCE_BOTTLE", "🧪✨");
        itemEmojiMap.put("DRAGON_BREATH", "🐉💨");
        itemEmojiMap.put("SPLASH_POTION", "🧪💦");
        itemEmojiMap.put("LINGERING_POTION", "🧪🌫️");
        itemEmojiMap.put("SPAWN_EGG", "🥚👾");
        itemEmojiMap.put("BLAZE_SPAWN_EGG", "🥚🔥");
        itemEmojiMap.put("CREEPER_SPAWN_EGG", "🥚💥");
        itemEmojiMap.put("ENDERMAN_SPAWN_EGG", "🥚👁️");
        itemEmojiMap.put("SKELETON_SPAWN_EGG", "🥚🦴");
        itemEmojiMap.put("ZOMBIE_SPAWN_EGG", "🥚🧟");
        itemEmojiMap.put("VILLAGER_SPAWN_EGG", "🥚👨‍🌾");
        itemEmojiMap.put("COW_SPAWN_EGG", "🥚🐄");
        itemEmojiMap.put("PIG_SPAWN_EGG", "🥚🐖");
        itemEmojiMap.put("SHEEP_SPAWN_EGG", "🥚🐑");
        itemEmojiMap.put("CHICKEN_SPAWN_EGG", "🥚🐔");
        itemEmojiMap.put("HORSE_SPAWN_EGG", "🥚🐎");
        itemEmojiMap.put("WOLF_SPAWN_EGG", "🥚🐺");
        itemEmojiMap.put("OCELOT_SPAWN_EGG", "🥚🐱");
        itemEmojiMap.put("PARROT_SPAWN_EGG", "🥚🦜");
        itemEmojiMap.put("DOLPHIN_SPAWN_EGG", "🥚🐬");
        itemEmojiMap.put("TURTLE_SPAWN_EGG", "🥚🐢");
        itemEmojiMap.put("PHANTOM_SPAWN_EGG", "🥚👻");
        itemEmojiMap.put("WITHER_SKELETON_SPAWN_EGG", "🥚💀");
        itemEmojiMap.put("ENDERMITE_SPAWN_EGG", "🥚🐛");
        itemEmojiMap.put("GUARDIAN_SPAWN_EGG", "🥚🐟");
        itemEmojiMap.put("SHULKER_SPAWN_EGG", "🥚📦");
        itemEmojiMap.put("AXOLOTL_SPAWN_EGG", "🥚🦎");
        itemEmojiMap.put("GLOW_SQUID_SPAWN_EGG", "🥚🦑✨");
        itemEmojiMap.put("GOAT_SPAWN_EGG", "🥚🐐");
        itemEmojiMap.put("FROG_SPAWN_EGG", "🥚🐸");
        itemEmojiMap.put("TADPOLE_SPAWN_EGG", "🥚🐌");
        itemEmojiMap.put("WARDEN_SPAWN_EGG", "🥚👹");
        itemEmojiMap.put("ALLAY_SPAWN_EGG", "🥚🧚");
        itemEmojiMap.put("SNIFFER_SPAWN_EGG", "🥚🦕");
        itemEmojiMap.put("CAMEL_SPAWN_EGG", "🥚🐪");
        itemEmojiMap.put("CHORUS_FRUIT", "🍇");
        itemEmojiMap.put("POPPED_CHORUS_FRUIT", "🍇🔥");
        itemEmojiMap.put("SHULKER_SHELL", "📦");
        itemEmojiMap.put("IRON_HORSE_ARMOR", "🦺🐎");
        itemEmojiMap.put("GOLDEN_HORSE_ARMOR", "🟨🐎");
        itemEmojiMap.put("DIAMOND_HORSE_ARMOR", "💎🐎");
        itemEmojiMap.put("LEATHER_HORSE_ARMOR", "🧥🐎");
        itemEmojiMap.put("TRIDENT", "🔱");
        itemEmojiMap.put("NAUTILUS_SHELL", "🐚");
        itemEmojiMap.put("HEART_OF_THE_SEA", "💙🌊");
        itemEmojiMap.put("SCUTE", "🐢🛡️");
        itemEmojiMap.put("TURTLE_HELMET", "🐢🪖");
        itemEmojiMap.put("PHANTOM_MEMBRANE", "👻🧼");
        itemEmojiMap.put("MUSIC_DISC_11", "🎵11");
        itemEmojiMap.put("MUSIC_DISC_13", "🎵13");
        itemEmojiMap.put("MUSIC_DISC_BLOCKS", "🎵🧱");
        itemEmojiMap.put("MUSIC_DISC_CAT", "🎵🐱");
        itemEmojiMap.put("MUSIC_DISC_CHIRP", "🎵🐦");
        itemEmojiMap.put("MUSIC_DISC_FAR", "🎵🌌");
        itemEmojiMap.put("MUSIC_DISC_MALL", "🎵🏬");
        itemEmojiMap.put("MUSIC_DISC_MELLOHI", "🎵🎶");
        itemEmojiMap.put("MUSIC_DISC_STAL", "🎵🕳️");
        itemEmojiMap.put("MUSIC_DISC_STRAD", "🎵🎻");
        itemEmojiMap.put("MUSIC_DISC_WARD", "🎵🏰");
        itemEmojiMap.put("MUSIC_DISC_WAIT", "🎵⏳");
        itemEmojiMap.put("MUSIC_DISC_PIGSTEP", "🎵🐷");
        itemEmojiMap.put("MUSIC_DISC_OTHERSIDE", "🎵🌀");
        itemEmojiMap.put("MUSIC_DISC_5", "🎵5");
        itemEmojiMap.put("MUSIC_DISC_RELIC", "🎵🏺");
        itemEmojiMap.put("BANNER", "🚩");
        itemEmojiMap.put("LOOM", "🧵");
        itemEmojiMap.put("SMITHING_TABLE", "⚒️");
        itemEmojiMap.put("CARTOGRAPHY_TABLE", "🗺️");
        itemEmojiMap.put("GRINDSTONE", "🪨");
        itemEmojiMap.put("LECTERN", "📖");
        itemEmojiMap.put("COMPOSTER", "🍂");
        itemEmojiMap.put("BARREL", "🪣");
        itemEmojiMap.put("SMOKER", "🍖");
        itemEmojiMap.put("BLAST_FURNACE", "🔥");
        itemEmojiMap.put("CAMPFIRE", "🔥");
        itemEmojiMap.put("SOUL_CAMPFIRE", "🔥💀");
        itemEmojiMap.put("LANTERN", "🏮");
        itemEmojiMap.put("SOUL_LANTERN", "🏮💀");
        itemEmojiMap.put("BELL", "🔔");
        itemEmojiMap.put("CHAIN", "⛓️");
        itemEmojiMap.put("SOUL_TORCH", "🔥💀");
        itemEmojiMap.put("SOUL_CAMPFIRE", "🔥💀");
        itemEmojiMap.put("RESPAWN_ANCHOR", "⚓");
        itemEmojiMap.put("LODESTONE", "🧲");
        itemEmojiMap.put("CRYING_OBSIDIAN", "😢🪵");
        itemEmojiMap.put("NETHERITE_SCRAP", "🟧");
        itemEmojiMap.put("ANCIENT_DEBRIS", "🪨🔥");
        itemEmojiMap.put("NETHER_GOLD_ORE", "🟨🔥");
        itemEmojiMap.put("NETHER_QUARTZ_ORE", "🔶🔥");
        itemEmojiMap.put("BASALT", "🪨");
        itemEmojiMap.put("POLISHED_BASALT", "🪨✨");
        itemEmojiMap.put("SOUL_SOIL", "💀🪨");
        itemEmojiMap.put("SOUL_SAND", "💀🪨");
        itemEmojiMap.put("WARPED_FUNGUS", "🍄🌀");
        itemEmojiMap.put("CRIMSON_FUNGUS", "🍄🔴");
        itemEmojiMap.put("WARPED_ROOTS", "🌱🌀");
        itemEmojiMap.put("CRIMSON_ROOTS", "🌱🔴");
        itemEmojiMap.put("NETHER_SPROUTS", "🌱🔥");
        itemEmojiMap.put("WEEPING_VINES", "🌿😢");
        itemEmojiMap.put("TWISTING_VINES", "🌿🌀");
        itemEmojiMap.put("SHROOMLIGHT", "🍄✨");
        itemEmojiMap.put("TARGET", "🎯");
        itemEmojiMap.put("HONEY_BLOCK", "🍯🧱");
        itemEmojiMap.put("SLIME_BLOCK", "🟢🧱");
        itemEmojiMap.put("MAGMA_BLOCK", "🔥🧱");
        itemEmojiMap.put("OBSERVER", "👀");
        itemEmojiMap.put("HOPPER_MINECART", "🪣🚂");
        itemEmojiMap.put("TNT_MINECART", "💣🚂");
        itemEmojiMap.put("CHEST_MINECART", "📦🚂");
        itemEmojiMap.put("FURNACE_MINECART", "🔥🚂");
        itemEmojiMap.put("COMMAND_BLOCK_MINECART", "🖥️🚂");
        itemEmojiMap.put("SPAWNER", "👾");
        itemEmojiMap.put("JIGSAW", "🧩");
        itemEmojiMap.put("STRUCTURE_BLOCK", "🧱");
        itemEmojiMap.put("STRUCTURE_VOID", "🕳️");
        itemEmojiMap.put("BARRIER", "🚧");
        itemEmojiMap.put("LIGHT", "💡");
        itemEmojiMap.put("REPEATING_COMMAND_BLOCK", "🖥️🔁");
        itemEmojiMap.put("CHAIN_COMMAND_BLOCK", "🖥️⛓️");
        itemEmojiMap.put("COMMAND_BLOCK", "🖥️");
        itemEmojiMap.put("KNOWLEDGE_BOOK", "📚🧠");
        itemEmojiMap.put("DEBUG_STICK", "🪵🐛");
        itemEmojiMap.put("BUNDLE", "🎒");
        itemEmojiMap.put("SCULK_SENSOR", "👂");
        itemEmojiMap.put("SCULK_CATALYST", "🌀");
        itemEmojiMap.put("SCULK_SHRIEKER", "📢");
        itemEmojiMap.put("RECOVERY_COMPASS", "🧭💖");
        itemEmojiMap.put("ECHO_SHARD", "🔮");
        itemEmojiMap.put("GOAT_HORN", "🐐📯");
        itemEmojiMap.put("FROGLIGHT", "🐸✨");
        itemEmojiMap.put("MANGROVE_PROPAGULE", "🌱");
        itemEmojiMap.put("OCHRE_FROGLIGHT", "🟧🐸");
        itemEmojiMap.put("VERDANT_FROGLIGHT", "🟩🐸");
        itemEmojiMap.put("PEARLESCENT_FROGLIGHT", "🟣🐸");
        itemEmojiMap.put("REINFORCED_DEEPSLATE", "🪨🛡️");
        itemEmojiMap.put("SUSPICIOUS_STEW", "🍲🕵️");
        itemEmojiMap.put("BRUSH", "🖌️");
        itemEmojiMap.put("NETHERITE_UPGRADE_SMITHING_TEMPLATE", "🟧⚒️");
        itemEmojiMap.put("SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE", "🛡️⚒️");
        itemEmojiMap.put("DUNE_ARMOR_TRIM_SMITHING_TEMPLATE", "🏜️⚒️");
        itemEmojiMap.put("COAST_ARMOR_TRIM_SMITHING_TEMPLATE", "🌊⚒️");
        itemEmojiMap.put("WILD_ARMOR_TRIM_SMITHING_TEMPLATE", "🌿⚒️");
        itemEmojiMap.put("WARD_ARMOR_TRIM_SMITHING_TEMPLATE", "👹⚒️");
        itemEmojiMap.put("EYE_ARMOR_TRIM_SMITHING_TEMPLATE", "👁️⚒️");
        itemEmojiMap.put("VEX_ARMOR_TRIM_SMITHING_TEMPLATE", "🧚⚒️");
        itemEmojiMap.put("TIDE_ARMOR_TRIM_SMITHING_TEMPLATE", "🌊⚒️");
        itemEmojiMap.put("SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE", "🐽⚒️");
        itemEmojiMap.put("RIB_ARMOR_TRIM_SMITHING_TEMPLATE", "🦴⚒️");
        itemEmojiMap.put("SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE", "🗼⚒️");
        itemEmojiMap.put("WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE", "🧭⚒️");
        itemEmojiMap.put("SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE", "🌀⚒️");
        itemEmojiMap.put("SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE", "🤫⚒️");
        itemEmojiMap.put("RAISER_ARMOR_TRIM_SMITHING_TEMPLATE", "🔼⚒️");
        itemEmojiMap.put("HOST_ARMOR_TRIM_SMITHING_TEMPLATE", "👾⚒️");
    }

    public String getEmojiForItem(String itemName) {
        return itemEmojiMap.getOrDefault(itemName, ":question:"); // Devuelve un emoji por defecto si no se encuentra el
                                                                  // ítem
    }
}