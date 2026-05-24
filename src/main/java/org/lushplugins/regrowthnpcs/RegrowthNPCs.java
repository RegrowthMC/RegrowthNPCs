package org.lushplugins.regrowthnpcs;

import org.lushplugins.configurablenpcs.ConfigurableNPCs;
import org.lushplugins.lushlib.utils.plugin.SpigotPlugin;
import org.lushplugins.regrowthnpcs.command.NPCsCommand;
import org.lushplugins.regrowthnpcs.command.annotation.MetaFields;
import org.lushplugins.regrowthnpcs.listener.PlayerListener;
import org.lushplugins.regrowthnpcs.npc.NPC;
import org.lushplugins.regrowthnpcs.npc.NPCManager;
import revxrsal.commands.bukkit.BukkitLamp;

public final class RegrowthNPCs extends SpigotPlugin {
    private static RegrowthNPCs plugin;

    private NPCManager npcManager;

    @Override
    public void onLoad() {
        plugin = this;
        ConfigurableNPCs.init(this);
    }

    @Override
    public void onEnable() {
        this.npcManager = new NPCManager();
        this.npcManager.reload();

        registerListener(new PlayerListener());

        BukkitLamp.builder(this)
            .suggestionProviders(providers -> providers
                .addProviderForAnnotation(MetaFields.class, (annotation) -> (context) -> {
                    return ConfigurableNPCs.metaSerializers().values().stream()
                        .flatMap(serializer -> serializer.getFieldNames().stream())
                        .toList();
                })
                .addProvider(NPC.class, (context) -> {
                    return RegrowthNPCs.getInstance().getNPCManager().getNPCs().stream()
                        .map(NPC::getName)
                        .toList();
                })
            )
            .parameterTypes(types -> types
                .addParameterType(NPC.class, (input, context) -> {
                    return RegrowthNPCs.getInstance().getNPCManager().getNPC(input.readString());
                }))
            .build()
            .register(new NPCsCommand());
    }

    public NPCManager getNPCManager() {
        return npcManager;
    }

    public static RegrowthNPCs getInstance() {
        return plugin;
    }
}
