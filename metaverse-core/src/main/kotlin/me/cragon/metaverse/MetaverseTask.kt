package me.cragon.metaverse

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

const val MAX_TICK = 3 * 60 * 20

class MetaverseTask: BukkitRunnable() {
    private var tick = 0
    private val bossBar = BossBar.bossBar(Component.text(0),  0f, BossBar.Color.GREEN, BossBar.Overlay.NOTCHED_10)
    override fun run() {
        tick++
        if (tick % 20 == 0) {
            bossBar.name(Component.text(tick / 20))
            bossBar.progress(tick / MAX_TICK.toFloat())
        }
        when (tick) {
            1 -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.showBossBar(bossBar)
                }
            }
            20 -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(Component.text("葉(엽) 싁싁\uF53A뎌 風憲所司(풍헌소사) 싁싁\uF53A뎌 風憲所司(풍헌소사)"))
                }
            }
            60 -> {

            }
            else -> {
            }
        }
    }



    override fun cancel() {
        Bukkit.getOnlinePlayers().forEach {
            it.hideBossBar(bossBar)
        }

        super.cancel()
    }
}
