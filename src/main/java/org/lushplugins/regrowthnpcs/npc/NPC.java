package org.lushplugins.regrowthnpcs.npc;

import com.github.retrooper.packetevents.protocol.world.Location;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.NumberConversions;
import org.lushplugins.configurablenpcs.entity.EntityConfiguration;
import org.lushplugins.regrowthnpcs.utils.Locations;

public class NPC {
    private final String name;
    private String world;
    private Location location;
    private final EntityConfiguration entityConfig;
    private final WrapperEntity entity;

    public NPC(String name, String world, Location location, EntityConfiguration entityConfig) {
        this.name = name;
        this.world = world;
        this.location = location;
        this.entityConfig = entityConfig;
        this.entity = entityConfig.createEntity();

        spawn();
    }

    public NPC(String name, World world, Location location, EntityConfiguration entityConfig) {
        this(name, world.getName(), location, entityConfig);
    }

    public String getName() {
        return name;
    }

    public String getWorldName() {
        return world;
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public void setWorld(World world) {
        this.world = world.getName();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(org.bukkit.Location location) {
        World world = location.getWorld();
        if (world != null) {
            setWorld(world.getName());
        }

        setLocation(SpigotConversionUtil.fromBukkitLocation(location));
    }

    public EntityConfiguration getEntityConfig() {
        return entityConfig;
    }

    public WrapperEntity getEntity() {
        return entity;
    }

    public void spawn() {
        if (entity.isSpawned()) {
            return;
        }

        entity.spawn(location);

        Locations.getChunkAt(getWorld(), location).getPlayersSeeingChunk().forEach(player -> {
            entity.addViewer(player.getUniqueId());
        });
    }

    public void despawn() {
        entity.despawn();
    }

    public void serialize(ConfigurationSection config) {
        config.set("name", name);
        config.set("world", world);
        config.set("location.x", location.getX());
        config.set("location.y", location.getY());
        config.set("location.z", location.getZ());
        config.set("location.yaw", location.getYaw());
        config.set("location.pitch", location.getPitch());

        ConfigurationSection entitySection = config.createSection("entity");
        entityConfig.serialize(entitySection);
    }

    public static NPC deserialize(ConfigurationSection config) {
        String name = config.getString("name");
        String world = config.getString("world");
        Location location = new Location(
            NumberConversions.toDouble(config.get("location.x")),
            NumberConversions.toDouble(config.get("location.y")),
            NumberConversions.toDouble(config.get("location.z")),
            NumberConversions.toFloat(config.get("location.yaw")),
            NumberConversions.toFloat(config.get("location.pitch"))
        );

        ConfigurationSection entitySection = config.getConfigurationSection("entity");
        EntityConfiguration entityConfig = EntityConfiguration.deserialize(entitySection);

        return new NPC(name, world, location, entityConfig);
    }
}
