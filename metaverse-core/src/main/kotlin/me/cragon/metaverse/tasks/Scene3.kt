package me.cragon.metaverse.tasks

import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.FakePlayer
import me.cragon.metaverse.internal.MetaverseSkin
import net.minecraft.network.protocol.game.PacketPlayOutEntity
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer

class Scene3 : TaskBase() {
    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                listOf(
                    Location(Metaverse.mainWorld, -406.97, 22.00, 289.27, -88.83f, -4.65f),
                    Location(Metaverse.mainWorld, -406.26, 22.00, 293.46, -90.33f, -6.90f),
                    Location(Metaverse.mainWorld, -406.28, 22.00, 297.34, -89.58f, -6.90f),
                    Location(Metaverse.mainWorld, -406.72, 22.00, 301.49, -90.63f, -10.35f),
                    Location(Metaverse.mainWorld, -390.34, 22.00, 301.38, -270.63f, -13.80f),
                    Location(Metaverse.mainWorld, -390.36, 22.00, 297.50, -270.78f, -12.60f),
                    Location(Metaverse.mainWorld, -390.37, 22.00, 293.40, -271.53f, -13.65f),
                    Location(Metaverse.mainWorld, -390.42, 22.00, 289.52, -271.53f, -13.65f),
                ).forEach { location ->
                    val npc = FakePlayer.spawnFakePlayer("관리", location, MetaverseSkin.COURTIER)
                    npcs += npc
                }

                runAllPlayers {
                    val connection = (it as CraftPlayer).handle.b
                    npcs.forEach { npc ->
                        connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a,
                            npc))
                        connection.sendPacket(PacketPlayOutNamedEntitySpawn(npc))
                        connection.sendPacket(PacketPlayOutEntityHeadRotation(npc,
                            (npc.yRot / 256 * 360).toInt().toByte()))
                        connection.sendPacket(PacketPlayOutEntity.PacketPlayOutEntityLook(npc.id,
                            (npc.yRot / 256 * 360).toInt().toByte(),
                            (npc.xRot / 256 * 360).toInt().toByte(),
                            true))
                    }
                }
            }
        }
    }
}
