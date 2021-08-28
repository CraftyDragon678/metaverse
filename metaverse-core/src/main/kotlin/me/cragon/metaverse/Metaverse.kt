package me.cragon.metaverse

import io.github.monun.tap.fake.FakeEntityServer
import io.github.monun.tap.fake.FakeProjectileManager
import me.cragon.metaverse.plugin.MetaversePlugin
import org.bukkit.Bukkit

object Metaverse {
    lateinit var plugin: MetaversePlugin
        private set

    lateinit var fakeEntityServer: FakeEntityServer
        private set

    lateinit var fakeProjectileManager: FakeProjectileManager

    internal fun initialize(plugin: MetaversePlugin, fakeEntityServer: FakeEntityServer, fakeProjectileManager: FakeProjectileManager) {
        this.plugin = plugin
        this.fakeEntityServer = fakeEntityServer
        this.fakeProjectileManager = fakeProjectileManager
    }

    private var task: MetaverseTask? = null


    fun startTask(): Boolean {
        if (task != null) return false
        task = MetaverseTask()
        task!!.runTaskTimer(plugin, 0L, 1L)
        return true
    }

    fun stopTask(): Boolean {
        if (task == null) return false
        task?.cancel()
        task = null
        return true
    }
}
