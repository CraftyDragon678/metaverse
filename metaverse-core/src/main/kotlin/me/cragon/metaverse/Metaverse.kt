package me.cragon.metaverse

import io.github.monun.tap.fake.FakeEntityServer
import io.github.monun.tap.fake.FakeProjectileManager
import me.cragon.metaverse.internal.MetaverseSkin
import me.cragon.metaverse.plugin.MetaversePlugin
import me.cragon.metaverse.tasks.TaskBase
import org.bukkit.Bukkit
import org.bukkit.World

object Metaverse {
    lateinit var plugin: MetaversePlugin
        private set

    lateinit var fakeEntityServer: FakeEntityServer
        private set

    lateinit var fakeProjectileManager: FakeProjectileManager

    lateinit var mainWorld: World
        private set

    lateinit var joinSkin: MetaverseSkin

    internal fun initialize(plugin: MetaversePlugin, fakeEntityServer: FakeEntityServer, fakeProjectileManager: FakeProjectileManager) {
        this.plugin = plugin
        this.fakeEntityServer = fakeEntityServer
        this.fakeProjectileManager = fakeProjectileManager
        mainWorld = Bukkit.getWorlds().first()
        joinSkin = MetaverseSkin.ME
    }

    private var task: TaskBase? = null

    fun startTask(task: TaskBase): Boolean {
        if (this.task != null) return false
        this.task = task
        task.runTaskTimer(plugin, 0L, 1L)
        return true
    }

    fun stopTask(): Boolean {
        if (task == null) return false
        task?.cancel()
        task = null
        return true
    }
}
