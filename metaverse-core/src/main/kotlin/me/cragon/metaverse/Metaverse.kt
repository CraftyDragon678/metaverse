package me.cragon.metaverse

import me.cragon.metaverse.plugin.MetaversePlugin
import org.bukkit.scheduler.BukkitTask
import org.jetbrains.annotations.Nullable

class Metaverse(private val plugin: MetaversePlugin) {
    private var task: BukkitTask? = null
    fun startTask() {
        task = plugin.server.scheduler.runTaskTimer(plugin, MetaverseTask(), 0L, 1L)
    }

    fun stopTask() {
        task?.cancel()
        task = null
    }
}
