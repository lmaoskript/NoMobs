package sk.addon.noMobs;

import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class NoMobs extends JavaPlugin {

    static NoMobs instance;

    public static NoMobs getInstance() {
        return instance;
    }

    public static Set<String> allowedMobs = new HashSet<>();
    private static List<EntityType> OverworldHostileEntities;
    public static int MobsGroupChance = 20;
    public static int MaxMobsGroupAmount = 5;
    public static int MinMobsGroupAmount = 1;
    public static int MobsGroupSize = 7;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new SpawnEvent(), this);
        this.getCommand("nomobs").setExecutor(new ReloadCMD());
        instance = this;
        saveDefaultConfig();

        loadAllowedMobs();

        OverworldHostileEntities = Arrays.asList(
                EntityType.ZOMBIE,
                EntityType.SKELETON,
                EntityType.CREEPER,
                EntityType.SPIDER,
                EntityType.ENDERMAN,
                EntityType.WITCH
        );

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    public static void loadAllowedMobs() {
        allowedMobs.clear();
        allowedMobs.addAll(instance.getConfig().getStringList("allowed-mobs"));
    }

    public static int getMobsGroupChance() {
        MobsGroupChance = instance.getConfig().getInt("mobs-group.chance");
        return MobsGroupChance;
    }

    public static int getMaxMobsGroupAmount() {
        MaxMobsGroupAmount = instance.getConfig().getInt("mobs-group.max-amount");
        return MaxMobsGroupAmount;
    }

    public static int getMinMobsGroupAmount() {
        MinMobsGroupAmount = instance.getConfig().getInt("mobs-group.min-amount");
        return MinMobsGroupAmount;
    }

    public static int getMobsGroupSize() {
        MobsGroupSize = instance.getConfig().getInt("mobs-group.size");
        return MobsGroupSize;
    }

    public static List<EntityType> getOverworldHostileEntities() {
        return OverworldHostileEntities;
    }

}
