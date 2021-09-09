package me.cragon.metaverse.tasks

import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.FakeEntity
import me.cragon.metaverse.internal.MetaverseSkin
import org.bukkit.Location

class Scene26 : TaskBase() {
    override val mainLocation = Location(Metaverse.mainWorld, -341.80, 25.78, 102.33)
    override fun run() {
        super.run()
        when (tick) {
            0 -> {
                listOf(
                    Location(Metaverse.mainWorld, -361.57, 22.00, 100.63, -110.90f, 4.98f),
                    Location(Metaverse.mainWorld, -354.47, 22.00, 110.67, -125.30f, -16.77f),
                    Location(Metaverse.mainWorld, -344.77, 22.00, 127.79, -133.25f, -15.72f),
                    Location(Metaverse.mainWorld, -335.54, 22.00, 138.37, -146.75f, -17.07f),
                    Location(Metaverse.mainWorld, -329.20, 22.00, 150.16, -146.75f, -17.07f),
                    Location(Metaverse.mainWorld, -330.79, 22.00, 158.42, -146.75f, -17.07f),
                    Location(Metaverse.mainWorld, -327.23, 22.00, 165.81, -146.75f, -17.07f),
                ).forEach { location ->
                    val npc = FakeEntity.spawnFakePlayer("방덕공?을 찾는 사람", location, MetaverseSkin.RESIDENT)
                    npcs += npc
                }
                spawnNpcs(npcs)
                updateNpc()
            }
        }
    }
}
