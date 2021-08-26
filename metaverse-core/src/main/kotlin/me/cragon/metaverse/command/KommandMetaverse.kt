package me.cragon.metaverse.command

import io.github.monun.kommand.KommandSource
import io.github.monun.kommand.PluginKommand
import me.cragon.metaverse.plugin.MetaversePlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class KommandMetaverse {
    private lateinit var plugin: MetaversePlugin

    fun register(plugin: MetaversePlugin, kommand: PluginKommand) {
        this.plugin = plugin
        kommand.register("metaverse", "mv") {
            permission("metaverse.commands")
            then("start") {
                executes {
                    start()
                }
            }
            then("stop") {
                executes {
                    stop()
                }
            }
        }
    }

    private fun KommandSource.start() {
        plugin.metaverse.startTask()
        feedback(Component.text("start").color(NamedTextColor.GREEN))
    }

    private fun KommandSource.stop() {
        plugin.metaverse.stopTask()
        feedback(Component.text("end").color(NamedTextColor.RED))
    }
}
