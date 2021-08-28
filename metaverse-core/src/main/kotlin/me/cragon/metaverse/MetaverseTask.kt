package me.cragon.metaverse

import io.github.monun.tap.fake.FakeEntity
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Chicken
import org.bukkit.scheduler.BukkitRunnable

const val MAX_TICK = 3 * 60 * 20

private val runAllPlayers = Bukkit.getOnlinePlayers()::forEach

class MetaverseTask: BukkitRunnable() {
    private var tick = -1

    private var chicken: FakeEntity? = null

    override fun run() {
        tick++
        if (tick % 20 == 0) {
            bossBar.name(Component.text("${tick / 20 / 60}:${((tick / 20) % 60).toString().padStart(2, '0')}"))
            bossBar.progress(tick / MAX_TICK.toFloat())
        }
        when (tick) {
            0 -> {
                runAllPlayers {
                    it.showBossBar(bossBar)
                }
            }
            20 -> {
                runAllPlayers {
                    it.sendActionBar(Component.text("葉(엽) 싁싁\uF53A뎌 風憲所司(풍헌소사) 싁싁\uF53A뎌 風憲所司(풍헌소사)"))
                }
            }
            60 -> {
                chicken = Metaverse.fakeEntityServer.spawnEntity(Location(Bukkit.getWorlds().first(), 0.0, 61.0, 0.0, 270f, 0f), Chicken::class.java)
                chicken!!.updateMetadata<Chicken> {
                    setAI(true)
                }
                runAllPlayers {
                    it.sendActionBar(Component.text("치킨!!"))
                }
                val bukkitWorld = Bukkit.getWorlds().first()
                bukkitWorld.time = 23000L
            }
            in 61..99 -> {
                chicken!!.moveAndRotation(0.0, 0.0, 0.0, -90f, -0.5f * (tick - 60))
                val bukkitWorld = Bukkit.getWorlds().first()
                bukkitWorld.time = 23000L + (tick - 60) * 30
            }
            100 -> {
                chicken!!.updateMetadata<Chicken> {
                    println("metadata")
                    println(getMetadata("OnGround"))
                }
                runAllPlayers {
                    it.sendActionBar(Component.text("치킨 치킨!!"))
                }
            }
            MAX_TICK -> {
                cancel()
            }
        }
    }

    override fun cancel() {
        runAllPlayers {
            it.hideBossBar(bossBar)
        }
        chicken?.remove()
        Metaverse.fakeEntityServer.update()

        super.cancel()
    }

    companion object {
        private val bossBar = BossBar.bossBar(Component.text("0:00"),  0f, BossBar.Color.GREEN, BossBar.Overlay.NOTCHED_10)
    }
}
