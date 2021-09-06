package me.cragon.metaverse.command

import io.github.monun.kommand.KommandSource
import io.github.monun.kommand.PluginKommand
import io.github.monun.kommand.getValue
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.MetaverseSkin
import me.cragon.metaverse.plugin.MetaversePlugin
import me.cragon.metaverse.tasks.TaskBase
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import java.util.*

class KommandMetaverse {
    private lateinit var plugin: MetaversePlugin

    fun register(plugin: MetaversePlugin, kommand: PluginKommand) {
        this.plugin = plugin
        kommand.register("metaverse", "mv") {
            permission("metaverse.commands")
            then("start") {
                then("sceneNumber" to int(1)) {
                    executes {
                        val sceneNumber: Int by it
                        start(sceneNumber)
                    }
                }
                executes {
                    feedback(Component.text("Please specify scene number").color(NamedTextColor.RED))
                }
            }
            then("stop") {
                executes {
                    stop()
                }
            }
            then("test") {
                executes {
                    test()
                }
            }
            then("skin")  {
                then("skinName" to dynamicByEnum(EnumSet.allOf(MetaverseSkin::class.java))) {
                    executes {
                        val skinName: MetaverseSkin by it
                        setJoinSkin(skinName)
                    }
                }
                executes {
                    feedback(Component.text("Please specify skin name: one of the ").color(NamedTextColor.RED)
                        .append(Component.text(MetaverseSkin.values().joinToString(", ")).color(NamedTextColor.GREEN)))
                }
            }
            then("teleport") {
                then("sceneNumber" to int(1)) {
                    requires {
                        playerOrNull != null
                    }
                    executes {
                        val sceneNumber: Int by it
                        teleport(sceneNumber)
                    }
                }
                executes {
                    feedback(Component.text("Please specify scene number").color(NamedTextColor.RED))
                }
            }
        }
    }

    private fun KommandSource.start(sceneNumber: Int) {
        val task = TaskBase.getScene(sceneNumber)?.getDeclaredConstructor()?.newInstance()
        if (task == null) {
            feedback(Component.text().content("can't find scene")
                .color(NamedTextColor.RED).decorate(TextDecoration.ITALIC))
        } else if (Metaverse.startTask(task)) {
            broadcast(Component.text("start").color(NamedTextColor.GREEN))
        } else {
            return feedback(Component.text().content("metaverse is currently running")
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

    private fun KommandSource.test() {
        if (!isPlayer) feedback(Component.text("only for player").color(NamedTextColor.RED))
        else {
            feedback(Component.text("test").color(NamedTextColor.GRAY))
        }
    }

    private fun KommandSource.setJoinSkin(skin: MetaverseSkin) {
        Metaverse.joinSkin = skin
        feedback(Component.text().content("join skin is set: ")
            .append(Component.text().content(skin.name).color(NamedTextColor.GREEN)))
    }

    private fun KommandSource.teleport(sceneNumber: Int) {
        TaskBase.getScene(sceneNumber)?.getDeclaredConstructor()?.newInstance()?.let {
            player.teleport(it.mainLocation)
            feedback(Component.text().content("Proof!").color(NamedTextColor.LIGHT_PURPLE))
            return
        }
        feedback(Component.text().content("can't find scene")
            .color(NamedTextColor.RED).decorate(TextDecoration.ITALIC))
    }
}
