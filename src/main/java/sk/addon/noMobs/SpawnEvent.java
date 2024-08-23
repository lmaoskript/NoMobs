package sk.addon.noMobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

import static sk.addon.noMobs.NoMobs.*;

public class SpawnEvent implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            EntityType originalType = event.getEntityType();
            String originalTypeName = originalType.name().toUpperCase();


            if (!allowedMobs.contains(originalTypeName)) {
                event.setCancelled(true);
            } else {
                if (event.getEntity() instanceof Monster) {
                    if (NoMobs.getOverworldHostileEntities().contains(event.getEntityType())) {
                        if (random.nextInt(100) < getMobsGroupChance()) {
                            Location location = event.getLocation();
                            int minsize = getMinMobsGroupAmount();
                            int maxsize = getMaxMobsGroupAmount();
                            int groupsize = getMobsGroupSize();
                            int size = (int)(Math.random() * (maxsize - minsize + 1)) + minsize;
                            for (int a = 0; a < size; a++) {
                                EntityType randomEntity = getOverworldHostileEntities().get(random.nextInt(getOverworldHostileEntities().size()));
                                location.getWorld().spawnEntity(getRandomLocationOnGround(location.getWorld(), location, groupsize), randomEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    static Random random = new Random();

    public static Location getRandomLocationOnGround(World world, Location location, int radius) {
        Location OGLocation = location.clone();

        int x = (int) location.getX();
        int z = (int) location.getZ();

        Location pcorner = new Location(world, x + radius, location.getY(), z + radius);
        Location ncorner = new Location(world, x - radius, location.getY(), z - radius);

        int randomx = ncorner.getBlockX() + random.nextInt(pcorner.getBlockX() - ncorner.getBlockX() + 1);
        int randomz = ncorner.getBlockZ() + random.nextInt(pcorner.getBlockZ() - ncorner.getBlockZ() + 1);

        Location randomLoc = new Location(world, randomx + 0.5, location.getY(), randomz + 0.5);
        randomLoc.setY(getHighestYInRadius(randomLoc, radius, 0));

        if (world.getHighestBlockAt(randomLoc).getY() + 1 == (int) randomLoc.getY()) {
            if (world.getTime() < 23600 && world.getTime() > 12500) {
                return randomLoc;
            }
        }
        return OGLocation;
    }

    private static double getHighestYInRadius(Location location, int radius, int isfortest) {
        for (int a = 0; a < radius; a++) {
            Location blockUnder = location.clone().subtract(0, 1, 0);

            Material blockType = location.getBlock().getType();
            Material blockTypeUnder = blockUnder.getBlock().getType();

            if (blockType != Material.AIR) {
                location.add(0, 1, 0);
                blockUnder.add(0, 1, 0);
            }
            if (blockTypeUnder == Material.AIR) {
                location.subtract(0, 1, 0);
                blockUnder.subtract(0, 1, 0);
            }
            blockType = location.getBlock().getType();
            blockTypeUnder = blockUnder.getBlock().getType();

            Location blockAbove = location.clone().add(0, 1, 0);
            Location blockAbove2 = location.clone().add(0, 2, 0);
            Material blockTypeAbove = blockAbove.getBlock().getType();
            Material blockTypeAbove2 = blockAbove2.getBlock().getType();

            if (blockTypeUnder != Material.AIR && blockType == Material.AIR && blockTypeAbove == Material.AIR && blockTypeAbove2 == Material.AIR) {
                return location.getY();
            }
        }
        if (isfortest == 0) {
            return getHighestYInRadius(location, radius, 1);
        }
        return -1;
    }


}
