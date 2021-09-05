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

class Scene5 : TaskBase() {
    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                listOf(
                    Location(Metaverse.mainWorld, -493.09, 23.50, 611.56, -90.03f, 3.96f),
                    Location(Metaverse.mainWorld, -492.93, 23.50, 615.52, -90.33f, 1.80f),
                    Location(Metaverse.mainWorld, -492.90, 23.50, 619.65, -91.08f, 0.60f),
                    Location(Metaverse.mainWorld, -493.01, 23.50, 623.55, -90.03f, 1.20f),
                    Location(Metaverse.mainWorld, -486.90, 23.50, 626.37, -270.78f, 1.80f),
                    Location(Metaverse.mainWorld, -486.92, 23.50, 622.45, -270.78f, 1.95f),
                    Location(Metaverse.mainWorld, -487.00, 23.50, 618.00, -269.58f, -2.10f),
                    Location(Metaverse.mainWorld, -486.99, 23.50, 614.53, -268.98f, -1.35f),
                    Location(Metaverse.mainWorld, -486.97, 23.50, 611.64, -269.43f, -1.05f),
                ).forEach { location ->
                    val npc = FakePlayer.spawnFakePlayer("관리", location, MetaverseSkin.COURTIER)
                    npcs += npc
                }

                updateNpc()
            }
        }
    }
}
