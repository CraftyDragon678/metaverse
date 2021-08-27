package me.cragon.metaverse

import io.github.monun.tap.fake.FakeEntityServer
import me.cragon.metaverse.plugin.MetaversePlugin
import org.bukkit.Bukkit

class Metaverse(private val plugin: MetaversePlugin) {
    private var task: MetaverseTask? = null
    val fakeEntityServer by lazy {
        val fes = FakeEntityServer.create(plugin)
        Bukkit.getOnlinePlayers().forEach {
            fes.addPlayer(it)
        }
        fes
    }

    fun startTask() {
        if (task != null) return
        task = MetaverseTask()
        task!!.runTaskTimer(plugin, 0L, 1L)
    }

    fun stopTask() {
        task?.cancel()
        task = null
    }
}
