package me.cragon.metaverse.tasks

import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.FakeEntity
import me.cragon.metaverse.internal.MetaverseSkin
import net.minecraft.network.protocol.game.PacketPlayOutMount
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Scene5 : TaskBase(), Listener {
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
                    armorStands += FakeEntity.spawnFakeArmorStand(location.clone().apply { y -= 1.8 }).apply {
                        isInvisible = true
                    }.also { armorStand ->
                        npcs += FakeEntity.spawnFakePlayer("관리", location, MetaverseSkin.COURTIER).apply {
                            startRiding((armorStand.bukkitEntity as CraftArmorStand).handle)
                        }
                    }
                }

                runAllPlayers { player ->
                    val connection = (player as CraftPlayer).handle.b
                    (0..8).forEach { index ->
                        connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a,
                            npcs[index]))
                        connection.sendPacket(PacketPlayOutNamedEntitySpawn(npcs[index]))
                        connection.sendPacket(PacketPlayOutSpawnEntityLiving(armorStands[index]))
                        connection.sendPacket(PacketPlayOutMount((armorStands[index].bukkitEntity as CraftArmorStand).handle))
                    }
                }
                updateNpc()
            }
        }
    }

    private val playerSeatLocation = Location(Metaverse.mainWorld, -492.92, 21.70, 627.54)

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (e.player.location.distance(playerSeatLocation) < 1) {
            armorStands += FakeEntity.spawnFakeArmorStand(playerSeatLocation).apply {
                isInvisible = true
            }.also { armorStand ->
                (e.player as CraftPlayer).handle.startRiding(armorStand)
                runAllPlayers { player ->
                    val connection = (player as CraftPlayer).handle.b
                    connection.sendPacket(PacketPlayOutMount((armorStand.bukkitEntity as CraftArmorStand).handle))
                }
            }
        }
    }
}
