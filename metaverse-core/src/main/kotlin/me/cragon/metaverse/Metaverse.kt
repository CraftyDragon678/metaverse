package me.cragon.metaverse

import me.cragon.metaverse.plugin.MetaversePlugin
import org.bukkit.scheduler.BukkitTask

class Metaverse(private val plugin: MetaversePlugin) {
    private var task: BukkitTask? = null
    fun startTask() {
        if (task != null) return
        task = MetaverseTask().runTaskTimer(plugin, 0L, 1L)
    }

    fun stopTask() {
        task?.cancel()
        task = null
    }
}
