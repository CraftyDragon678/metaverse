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

class Scene20 : TaskBase(), Listener {
    override val mainLocation = Location(Metaverse.mainWorld, -493.09, 23.50, 611.56, -90.03f, 3.96f)

    private val locations = listOf(
        Location(Metaverse.mainWorld, -547.90, 23.50, 623.50, -840.33f, 3.76f),
        Location(Metaverse.mainWorld, -547.91, 23.50, 619.48, -810.33f, 3.76f),
        Location(Metaverse.mainWorld, -547.93, 23.50, 615.52, -778.98f, 0.61f),
    )

    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                spawnNpcsInLocations(locations, "관리", MetaverseSkin.COURTIER, true)
            }
            100 -> {
                despawnNpcs(npcs.take(1))
                despawnEntities(armorStands.take(1))
                spawnNpcsInLocations(locations.take(1), "관리", MetaverseSkin.COURTIER_WITHOUT, true)
            }
            120 -> {
                despawnNpcs(npcs.take(1))
                despawnEntities(armorStands.take(1))
                spawnNpcsInLocations(locations.take(2).takeLast(1), "관리", MetaverseSkin.COURTIER_WITHOUT, true)
            }
            140 -> {
                despawnNpcs(npcs.take(1))
                despawnEntities(armorStands.take(1))
                spawnNpcsInLocations(locations.takeLast(1), "관리", MetaverseSkin.COURTIER_WITHOUT, true)
            }
        }
    }
}
