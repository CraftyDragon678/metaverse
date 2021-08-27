package me.cragon.metaverse.plugin

import io.github.monun.kommand.PluginKommand
import io.github.monun.kommand.kommand
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.MetaverseTask
import me.cragon.metaverse.command.KommandMetaverse
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class MetaversePlugin: JavaPlugin(), Listener {
    lateinit var metaverse: Metaverse
    override fun onEnable() {
        logger.info("Enabled!")
        server.scheduler.runTaskTimer(this, MetaverseTask(), 0L, 30L)
        kommand {
            KommandMetaverse().register(this@MetaversePlugin, this)
        }
        metaverse = Metaverse(this)
        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        metaverse.stopTask()
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        metaverse.fakeEntityServer.addPlayer(e.player)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        metaverse.fakeEntityServer.removePlayer(e.player)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return false
    }
}
