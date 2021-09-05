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

class Scene16 : TaskBase() {
    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                listOf(
                    Location(Metaverse.mainWorld, 68.70, 33.06, -565.57, -87.22f, 66.55f),
                    Location(Metaverse.mainWorld, 68.63, 32.87, -560.82, -88.42f, 58.45f),
                    Location(Metaverse.mainWorld, 68.53, 33.06, -555.38, -85.27f, 52.45f),
                    Location(Metaverse.mainWorld, 83.30, 33.06, -565.56, -271.57f, 62.35f),
                    Location(Metaverse.mainWorld, 83.30, 33.06, -560.47, -265.72f, 58.15f),
                    Location(Metaverse.mainWorld, 83.32, 33.06, -555.33, -273.67f, 68.05f),
                ).forEach { location ->
                    val npc = FakePlayer.spawnFakePlayer("관리", location, MetaverseSkin.COURTIER)
                    npcs += npc
                }

                updateNpc()
            }
        }
    }
}
