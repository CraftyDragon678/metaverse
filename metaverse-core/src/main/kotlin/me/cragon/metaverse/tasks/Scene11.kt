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

class Scene11 : TaskBase(), Listener {
    override val mainLocation = Location(Metaverse.mainWorld, -490.02, 23.00, 629.92, 179.52f, 29.31f)

    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                spawnNpcsInLocations(
                    listOf(
                        Location(Metaverse.mainWorld, -490.02, 23.00, 629.92, 179.52f, 29.31f),
                    ),
                    "대사헌",
                    MetaverseSkin.COURTIER
                )
                spawnNpcsInLocations(
                    listOf(
                        Location(Metaverse.mainWorld, -487.19, 23.00, 624.23, 32.07f, 36.51f),
                        Location(Metaverse.mainWorld, -486.75, 23.00, 620.79, 29.07f, 28.41f),
                        Location(Metaverse.mainWorld, -486.88, 23.00, 616.93, 28.62f, 30.81f),
                        Location(Metaverse.mainWorld, -492.79, 23.00, 625.04, -23.43f, 29.76f),
                        Location(Metaverse.mainWorld, -492.86, 23.00, 621.83, -21.63f, 40.86f),
                        Location(Metaverse.mainWorld, -492.78, 23.00, 617.70, -22.68f, 35.16f),
                    ),
                    "관리",
                    MetaverseSkin.COURTIER
                )
            }
        }
    }
}
