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

class Scene15 : TaskBase(), Listener {
    override val mainLocation = Location(Metaverse.mainWorld, 75.48, 39.75, -582.05, 359.93f, 39.75f)

    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                listOf(
                    Location(Metaverse.mainWorld, 75.48, 39.75, -582.05, 359.93f, 29.75f),
                ).forEach { location ->
                    armorStands += FakeEntity.spawnFakeArmorStand(location.clone().apply { y -= 1.8 }).apply {
                        (bukkitEntity as CraftArmorStand).isInvisible = true
                    }.also { armorStand ->
                        npcs += FakeEntity.spawnFakePlayer("왕", location, MetaverseSkin.KING).apply {
                            startRiding((armorStand.bukkitEntity as CraftArmorStand).handle)
                        }
                    }
                }

                spawnNpcs(npcs)
                val index = 0
                Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                    PacketPlayOutSpawnEntityLiving(armorStands[index])))
                Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                    PacketPlayOutMount(armorStands[index].bukkitEntity.handle)))
                Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                    PacketPlayOutEntityMetadata(
                        armorStands[index].id,
                        armorStands[index].bukkitEntity.handle.dataWatcher,
                        false)
                ))
                updateNpc()
            }
        }
    }
}
