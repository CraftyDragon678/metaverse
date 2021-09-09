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

class Scene101 : TaskBase(), Listener {
    override val mainLocation = Location(Metaverse.mainWorld, -493.09, 23.50, 611.56, -90.03f, 3.96f)

    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                spawnNpcsInLocations(
                    listOf(
                        Location(Metaverse.mainWorld, -43.99, 24.00, -263.52, 271.06f, -0.30f),
                        Location(Metaverse.mainWorld, -44.00, 24.00, -261.59, 271.06f, -0.30f),
                        Location(Metaverse.mainWorld, -43.43, 24.00, -279.67, -87.29f, 8.10f),
                        Location(Metaverse.mainWorld, -43.48, 24.00, -277.39, -87.29f, 8.10f),
                        Location(Metaverse.mainWorld, -43.53, 24.00, -275.39, -87.29f, 8.10f),
                        Location(Metaverse.mainWorld, -43.82, 24.00, -259.44, 270.31f, -0.75f),
                        Location(Metaverse.mainWorld, -40.63, 24.00, -277.55, -87.59f, 16.35f),
                        Location(Metaverse.mainWorld, -40.65, 24.00, -261.41, 268.96f, 10.35f),
                        Location(Metaverse.mainWorld, -40.30, 24.00, -269.59, -91.49f, 4.35f),
                        Location(Metaverse.mainWorld, -31.86, 24.00, -262.54, -91.51f, 1.35f),
                        Location(Metaverse.mainWorld, -36.43, 24.00, -269.48, -92.39f, -6.75f),
                        Location(Metaverse.mainWorld, -31.83, 24.00, -264.54, -91.51f, 1.35f),
                        Location(Metaverse.mainWorld, -31.60, 24.00, -276.62, -90.74f, 4.80f),
                        Location(Metaverse.mainWorld, -31.81, 24.00, -266.70, -89.56f, 1.20f),
                        Location(Metaverse.mainWorld, -26.39, 24.00, -261.65, -88.41f, 1.65f),
                        Location(Metaverse.mainWorld, -31.69, 24.00, -274.59, -93.44f, 0.60f),
                        Location(Metaverse.mainWorld, -26.85, 24.00, -264.52, -88.16f, -2.55f),
                        Location(Metaverse.mainWorld, -31.56, 24.00, -272.50, -88.49f, -3.75f),
                        Location(Metaverse.mainWorld, -25.72, 24.00, -277.58, -88.49f, 1.05f),
                        Location(Metaverse.mainWorld, -26.60, 24.00, -267.52, -88.96f, -0.15f),
                        Location(Metaverse.mainWorld, -25.75, 24.00, -274.54, -88.49f, 1.05f),
                        Location(Metaverse.mainWorld, -25.83, 24.00, -271.54, -88.04f, -10.50f),
                        Location(Metaverse.mainWorld, -26.44, 24.00, -269.58, -88.56f, -0.45f),
                    ),
                    "음악대",
                    MetaverseSkin.HIGH_COURTIER,
                    true
                )
            }
        }
    }
}
