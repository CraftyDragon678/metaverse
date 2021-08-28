package me.cragon.metaverse.command

import io.github.monun.kommand.KommandSource
import io.github.monun.kommand.PluginKommand
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.plugin.MetaversePlugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

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
            then("test") {
                executes {
                    feedback(Component.text("test"))
                }
            }
        }
    }

    private fun KommandSource.start() {
        if (Metaverse.startTask()) {
            broadcast(Component.text("start").color(NamedTextColor.GREEN))
        } else {
            feedback(Component.text().content("metaverse is currently running")
                .color(NamedTextColor.RED).decorate(TextDecoration.ITALIC))
        }
    }

    private fun KommandSource.stop() {
        if (Metaverse.stopTask()) {
            broadcast(Component.text("end").color(NamedTextColor.RED))
        } else {
            feedback(Component.text().content("metaverse not running")
                .color(NamedTextColor.RED).decorate(TextDecoration.ITALIC))
        }
    }
}
