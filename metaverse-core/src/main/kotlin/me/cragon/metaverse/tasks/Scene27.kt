package me.cragon.metaverse.tasks

import com.comphenix.protocol.events.PacketContainer
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.FakeEntity
import me.cragon.metaverse.internal.MetaverseSkin
import net.minecraft.network.protocol.game.PacketPlayOutEntity
import org.bukkit.Location

class Scene27 : TaskBase() {
    override val mainLocation = Location(Metaverse.mainWorld, 75.71, 24.50, -414.43, -180.59f, 7.77f)
    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                listOf(
                    Location(Metaverse.mainWorld, 75.71, 24.50, -414.43, -180.59f, 7.77f),
                ).forEach { location ->
                    val npc = FakeEntity.spawnFakePlayer("내가 왕이다", location, MetaverseSkin.KING)
                    npcs += npc
                }
                spawnNpcs(npcs)
                updateNpc()
            }
            in 1..2000 -> {
                Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                    PacketPlayOutEntity.PacketPlayOutRelEntityMove(npcs.first().id, 0, 0, -500, true)
                ))
                Short.MIN_VALUE
            }
        }
    }
}
