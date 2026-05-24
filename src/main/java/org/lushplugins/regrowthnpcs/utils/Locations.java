package org.lushplugins.regrowthnpcs.utils;

import com.github.retrooper.packetevents.protocol.world.Location;
import org.bukkit.Chunk;
import org.bukkit.World;

public class Locations {

    public static long getChunkKey(Location location) {
        return Chunk.getChunkKey((int) Math.floor(location.getX()) >> 4, (int) Math.floor(location.getZ()) >> 4);
    }

    public static Chunk getChunkAt(World world, Location location) {
        return world.getChunkAt(getChunkKey(location));
    }
}
