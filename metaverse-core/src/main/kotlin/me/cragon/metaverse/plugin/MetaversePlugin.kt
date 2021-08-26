package me.cragon.metaverse.plugin

import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.MetaverseTask
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class MetaversePlugin: JavaPlugin() {
    lateinit var metaverse: Metaverse
    override fun onEnable() {
        logger.info("Enabled!")
        server.scheduler.runTaskTimer(this, MetaverseTask(), 0L, 30L)
        metaverse = Metaverse(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return false
    }
}
