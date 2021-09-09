package me.cragon.metaverse.tasks

import com.comphenix.protocol.events.PacketContainer
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.FakeEntity
import me.cragon.metaverse.internal.MetaverseSkin
import net.minecraft.network.protocol.game.PacketPlayOutEntity
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata
import net.minecraft.network.protocol.game.PacketPlayOutMount
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Scene12 : TaskBase(), Listener {
    override val mainLocation = Location(Metaverse.mainWorld, -493.09, 23.50, 611.56, -90.03f, 3.96f)

    private val playerSeatLocation = Location(Metaverse.mainWorld, -547.99, 21.70, 623.49)
    private val playerSeatLocation2 = Location(Metaverse.mainWorld, -547.83, 21.70, 619.44)

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (e.player.location.distance(playerSeatLocation) < 3) {
            e.isCancelled = true
            if (e.player.isInsideVehicle) {
                exitArmorStand(e.player)
            } else {
                enterArmorStand(e.player, playerSeatLocation)
            }
        } else if (e.player.location.distance(playerSeatLocation2) < 3) {
            e.isCancelled = true
            if (e.player.isInsideVehicle) {
                exitArmorStand(e.player)
            } else {
                enterArmorStand(e.player, playerSeatLocation2)
            }
        }
    }

    private fun exitArmorStand(player: Player) {
        player.vehicle?.let {
            val armorStand = it as CraftArmorStand
            armorStands -= armorStand.handle
            (player as CraftPlayer).handle.stopRiding()
            runAllPlayers { player ->
                val connection = (player as CraftPlayer).handle.b
                connection.sendPacket(PacketPlayOutMount(armorStand.handle))
                connection.sendPacket(PacketPlayOutEntityDestroy(armorStand.handle.id))
            }
        }
    }

    private fun enterArmorStand(player: Player, location: Location) {
        armorStands += FakeEntity.spawnFakeArmorStand(location).apply {
            (bukkitEntity as CraftArmorStand).isInvisible = true
        }.also { armorStand ->
            (player as CraftPlayer).handle.startRiding(armorStand)
            runAllPlayers { player ->
                val connection = (player as CraftPlayer).handle.b
                connection.sendPacket(PacketPlayOutSpawnEntityLiving(armorStand))
                connection.sendPacket(PacketPlayOutMount(armorStand.bukkitEntity.handle))
                connection.sendPacket(PacketPlayOutEntityMetadata(armorStand.id, armorStand.bukkitEntity.handle.dataWatcher, false))
            }
            val npc = player.handle
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutEntity.PacketPlayOutEntityLook(npc.id,
                    ((( npc.yRot + (if (-180 < npc.yRot && npc.yRot < 0 || 180 < npc.yRot && npc.yRot < 360) { -45 } else { 45 })) * 256) / 360).toInt().toByte(),
                    ((npc.xRot * 256) / 360).toInt().toByte(),
                    true)
            ))
        }
    }

    override fun cancel() {
        super.cancel()
        PlayerInteractEvent.getHandlerList().unregister(this)
    }
}
