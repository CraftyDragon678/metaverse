package me.cragon.metaverse

import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import io.github.monun.tap.fake.FakeEntityServer
import io.github.monun.tap.fake.FakeProjectileManager
import me.cragon.metaverse.internal.MetaverseSkin
import me.cragon.metaverse.plugin.MetaversePlugin
import me.cragon.metaverse.tasks.TaskBase
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.event.Listener

object Metaverse {
    lateinit var plugin: MetaversePlugin
        private set

    lateinit var fakeEntityServer: FakeEntityServer
        private set

    lateinit var fakeProjectileManager: FakeProjectileManager

    lateinit var mainWorld: World
        private set

    lateinit var joinSkin: MetaverseSkin

    lateinit var protocolManager: ProtocolManager

    internal fun initialize(plugin: MetaversePlugin, fakeEntityServer: FakeEntityServer, fakeProjectileManager: FakeProjectileManager) {
        this.plugin = plugin
        this.fakeEntityServer = fakeEntityServer
        this.fakeProjectileManager = fakeProjectileManager
        mainWorld = Bukkit.getWorlds().first()
        joinSkin = MetaverseSkin.ME
        protocolManager = ProtocolLibrary.getProtocolManager()
    }

    private var task: TaskBase? = null

    fun startTask(task: TaskBase): Boolean {
        if (this.task != null) return false
        this.task = task
        task.runTaskTimer(plugin, 0L, 1L)
        if (task is Listener) {
            Bukkit.getPluginManager().registerEvents(task, plugin)
        }
        return true
    }

    fun stopTask(): Boolean {
        if (task == null) return false
        task?.cancel()
        task = null
        return true
    }
}
