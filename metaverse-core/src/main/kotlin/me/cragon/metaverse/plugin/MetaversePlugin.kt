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
    private lateinit var fakeEntityServer: FakeEntityServer

    private lateinit var fakeProjectileManager: FakeProjectileManager

    override fun onEnable() {
        logger.info("Enabled!")
        kommand {
            KommandMetaverse().register(this@MetaversePlugin, this)
        }
        loadModules()
        server.scheduler.runTaskTimer(this, SchedulerTask(fakeEntityServer, fakeProjectileManager), 0L, 1L)
    }

    private fun loadModules() {
        fakeEntityServer = FakeEntityServer.create(this).apply {
            Bukkit.getOnlinePlayers().forEach { addPlayer(it) }
        }
        fakeProjectileManager = FakeProjectileManager()
        server.pluginManager.registerEvents(this, this)
        Metaverse.initialize(this, fakeEntityServer, fakeProjectileManager)
    }

    override fun onDisable() {
        Metaverse.stopTask()
        fakeEntityServer.shutdown()
        fakeProjectileManager.clear()
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        fakeEntityServer.addPlayer(e.player)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        fakeEntityServer.removePlayer(e.player)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return false
    }
}
