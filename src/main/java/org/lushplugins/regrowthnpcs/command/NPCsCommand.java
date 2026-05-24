package org.lushplugins.regrowthnpcs.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lushplugins.regrowthnpcs.RegrowthNPCs;
import org.lushplugins.regrowthnpcs.command.annotation.MetaFields;
import org.lushplugins.regrowthnpcs.npc.NPC;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
@Command("npcs")
public class NPCsCommand {

    @Subcommand("edit")
    @CommandPermission("npcs.edit")
    public void edit(NPC npc, @MetaFields String field) {

    }

    @Subcommand("reload")
    @CommandPermission("npcs.reload")
    public void reload(CommandSender sender) {
        RegrowthNPCs.getInstance().getNPCManager().reload();

        sender.sendMessage(Component.text()
            .content("RegrowthNPCs reloaded!")
            .color(TextColor.fromHexString("#b7faa2"))
            .build());
    }

    @Subcommand("save")
    public void save() {
        RegrowthNPCs.getInstance().getNPCManager().getNPCs().forEach(npc -> {
            File file = RegrowthNPCs.getInstance().getDataPath()
                .resolve("npcs")
                .resolve(npc.getName().toLowerCase() + ".yml")
                .toFile();

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            npc.serialize(config);

            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
