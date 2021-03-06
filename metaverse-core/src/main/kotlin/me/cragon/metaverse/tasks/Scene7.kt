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
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Scene7 : TaskBase(), Listener {
    override val mainLocation = Location(Metaverse.mainWorld, -397.50, 22.00, 286.50, 361.20f, 10.50f)

    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                spawnNpcsInLocations(
                    listOf(
                        Location(Metaverse.mainWorld, -397.50, 22.00, 286.50, 361.20f, 10.50f),
                        Location(Metaverse.mainWorld, -397.50, 22.00, 296.50, -1.80f, -5.40f),
                        Location(Metaverse.mainWorld, -394.50, 22.00, 296.50, -5.10f, -2.10f),
                        Location(Metaverse.mainWorld, -394.51, 22.00, 286.50, 0.15f, 1.50f),
                    ),
                    "관리",
                    MetaverseSkin.COURTIER
                )
            }
            in 0..20*30 step 20 -> {
                Bukkit.getPlayer("CraftyDragon678")?.performCommand("/move 1 s -es")
            }
            in 20..Int.MAX_VALUE -> {
                npcs.forEach { npc ->
                    Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                        PacketPlayOutEntity.PacketPlayOutRelEntityMove(npc.id, 0, 0, (4096 / 19).toShort(), true)
                    ))
                }
            }
        }
    }
}
