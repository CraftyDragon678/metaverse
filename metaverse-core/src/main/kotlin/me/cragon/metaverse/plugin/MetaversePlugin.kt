package me.cragon.metaverse.plugin

import org.bukkit.plugin.java.JavaPlugin

class MetaversePlugin: JavaPlugin() {
    override fun onEnable() {
        logger.info("Enabled!")
    }
}