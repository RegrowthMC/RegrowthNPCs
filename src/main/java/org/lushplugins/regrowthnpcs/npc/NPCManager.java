package org.lushplugins.regrowthnpcs.npc;

import org.bukkit.Chunk;
import org.lushplugins.lushlib.config.YamlUtils;
import org.lushplugins.regrowthnpcs.RegrowthNPCs;
import org.lushplugins.regrowthnpcs.utils.Locations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NPCManager {
    private Map<String, NPC> npcs;

    public NPC getNPC(String npc) {
        return npcs.get(npc);
    }

    public Collection<NPC> getNPCs() {
        return npcs.values();
    }

    public List<NPC> getNPCsInChunk(Chunk chunk) {
        return npcs.values().stream()
            .filter(npc -> npc.getWorldName().equals(chunk.getWorld().getName()))
            .filter(npc -> chunk.getChunkKey() == Locations.getChunkKey(npc.getLocation()))
            .toList();
    }

    public void reload() {
        if (npcs != null) {
            npcs.values().forEach(NPC::despawn);
        }

        this.npcs = YamlUtils.readConfigsInDirectory(RegrowthNPCs.getInstance().getDataPath().resolve("npcs")).stream()
            .map(entry -> NPC.deserialize(entry.second()))
            .collect(Collectors.toMap(
                NPC::getName,
                npc -> npc
            ));
    }
}
