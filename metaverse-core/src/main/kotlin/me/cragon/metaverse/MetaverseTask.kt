package me.cragon.metaverse

import io.github.monun.tap.fake.FakeEntity
import me.cragon.metaverse.plugin.MetaversePlugin
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Chicken
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

const val MAX_TICK = 3 * 60 * 20

class MetaverseTask: BukkitRunnable() {
    private var tick = 0
    private val bossBar = BossBar.bossBar(Component.text("0:00"),  0f, BossBar.Color.GREEN, BossBar.Overlay.NOTCHED_10)

    private lateinit var chicken: FakeEntity
    private lateinit var metaverse: Metaverse

    override fun run() {
        tick++
        if (tick % 20 == 0) {
            bossBar.name(Component.text("${tick / 20 / 60}:${((tick / 20) % 60).toString().padStart(2, '0')}"))
            bossBar.progress(tick / MAX_TICK.toFloat())
        }
        metaverse.fakeEntityServer.update()
        when (tick) {
            1 -> {
                Bukkit.getOnlinePlayers().forEach {
                    metaverse.fakeEntityServer.addPlayer(it)
                    it.showBossBar(bossBar)
                }
            }
            20 -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(Component.text("葉(엽) 싁싁\uF53A뎌 風憲所司(풍헌소사) 싁싁\uF53A뎌 風憲所司(풍헌소사)"))
                }
            }
            60 -> {
                chicken = metaverse.fakeEntityServer.spawnEntity(Location(Bukkit.getWorlds().first(), 0.0, 61.0, 0.0, 270f, 0f), Chicken::class.java)
                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(Component.text("치킨!!"))
                }
                val bukkitWorld = Bukkit.getWorlds().first()
                bukkitWorld.time = 23000L
            }
            in 61..99 -> {
                chicken.moveAndRotation(0.0, 0.0, 0.0, -90f, -0.5f * (tick - 60))
                val bukkitWorld = Bukkit.getWorlds().first()
                bukkitWorld.time = 23000L + (tick - 60) * 30
            }
            100 -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(Component.text("치킨 치킨!!"))
                }
            }
            MAX_TICK -> {
                cancel()
            }
        }
    }

    override fun runTaskTimer(plugin: Plugin, delay: Long, period: Long): BukkitTask {
        metaverse = (plugin as MetaversePlugin).metaverse
        return super.runTaskTimer(plugin, delay, period)
    }

    override fun cancel() {
        Bukkit.getOnlinePlayers().forEach {
            it.hideBossBar(bossBar)
        }
        chicken.remove()
        metaverse.fakeEntityServer.update()

        super.cancel()
    }
}
