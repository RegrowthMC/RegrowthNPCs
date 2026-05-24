package org.lushplugins.regrowthnpcs.listener;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import io.papermc.paper.event.packet.PlayerChunkUnloadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.lushplugins.regrowthnpcs.RegrowthNPCs;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerChunkLoad(PlayerChunkLoadEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        RegrowthNPCs.getInstance().getNPCManager().getNPCsInChunk(event.getChunk()).forEach(npc -> {
            // TODO: Check worlds match
            npc.getEntity().addViewer(uuid);
        });
    }

    @EventHandler
    public void onPlayerChunkUnload(PlayerChunkUnloadEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        RegrowthNPCs.getInstance().getNPCManager().getNPCsInChunk(event.getChunk()).forEach(npc -> {
            // TODO: Check worlds match
            npc.getEntity().removeViewer(uuid);
        });
    }
}
