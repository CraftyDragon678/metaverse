package me.cragon.metaverse.plugin

import io.github.monun.kommand.kommand
import io.github.monun.tap.fake.FakeEntityServer
import io.github.monun.tap.fake.FakeProjectileManager
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.command.KommandMetaverse
import me.cragon.metaverse.internal.MetaverseSkin
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
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
    fun onPlayerPreJoin(e: AsyncPlayerPreLoginEvent) {
        e.playerProfile.setProperty(MetaverseSkin.ME.makeProperty())
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
