package me.cragon.metaverse

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

class MetaverseTask: Runnable {
    private var tick = 0
    override fun run() {
        tick++
        when (tick) {
            1 -> {
                Bukkit.getOnlinePlayers().forEach {
                    it.sendActionBar(Component.text("葉(엽) 싁싁\uF53A뎌 風憲所司(풍헌소사) 싁싁\uF53A뎌 風憲所司(풍헌소사)"))
                }
            }
            else -> {
            }
        }
    }
}
