package me.cragon.metaverse.tasks

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import io.github.monun.tap.fake.FakeEntity
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.PacketPlayOutEntity
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo
import net.minecraft.server.level.EntityPlayer
import net.minecraft.world.entity.decoration.EntityArmorStand
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.scheduler.BukkitRunnable

abstract class TaskBase: BukkitRunnable() {
    protected open val maxTick = 3 * 60 * 20
    protected val runAllPlayers = Bukkit.getOnlinePlayers()::forEach

    protected val npcs = mutableListOf<EntityPlayer>()
    protected val armorStands = mutableListOf<EntityArmorStand>()

    protected var tick = -1

    override fun run() {
        tick++
        if (tick % 20 == 0) {
            bossBar.name(Component.text("${tick / 20 / 60}:${((tick / 20) % 60).toString().padStart(2, '0')}"))
            bossBar.progress(tick / maxTick.toFloat())
        }
        if (tick == 0) {
            runAllPlayers {
                it.showBossBar(bossBar)
            }
        }
        if (tick == maxTick) {
            cancel()
        }
    }

    override fun cancel() {
        runAllPlayers {
            val connection = (it as CraftPlayer).handle.b
            npcs.forEach { npc ->
                connection.sendPacket(PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc))
                connection.sendPacket(PacketPlayOutEntityDestroy(npc.id))
            }
            armorStands.forEach { armorStand ->
                connection.sendPacket(PacketPlayOutEntityDestroy(armorStand.id))
            }
            it.hideBossBar(bossBar)
        }

        super.cancel()
    }

    protected fun updateNpc() {
        runAllPlayers {
            PacketContainer(PacketType.Play.Server.PLAYER_INFO)
            val connection = (it as CraftPlayer).handle.b
            npcs.forEach { npc ->
                connection.sendPacket(PacketPlayOutEntityHeadRotation(npc,
                    ((npc.yRot * 256) / 360).toInt().toByte()))
                connection.sendPacket(PacketPlayOutEntity.PacketPlayOutEntityLook(npc.id,
                    ((npc.yRot * 256) / 360).toInt().toByte(),
                    ((npc.xRot * 256) / 360).toInt().toByte(),
                    true))
            }
        }
    }

    companion object {
        @JvmStatic
        private val bossBar = BossBar.bossBar(Component.text("0:00"),  0f, BossBar.Color.GREEN, BossBar.Overlay.NOTCHED_10)

        fun getScene(sceneNumber: Int): Class<out TaskBase>? {
            return try {
                Class.forName("${TaskBase::class.java.packageName}.Scene$sceneNumber").asSubclass(TaskBase::class.java)
            } catch (e: ClassNotFoundException) {
                null
            }
        }
    }
}
