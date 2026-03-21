package org.lushplugins.regrowthnpcs;

import org.bukkit.plugin.java.JavaPlugin;

public final class RegrowthNpCs extends JavaPlugin {
    private static RegrowthNpCs plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static RegrowthNpCs getInstance() {
        return plugin;
    }
}
