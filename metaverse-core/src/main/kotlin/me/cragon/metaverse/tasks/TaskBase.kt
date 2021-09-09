package me.cragon.metaverse.tasks

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.PlayerInfoData
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedGameProfile
import io.github.monun.tap.fake.FakeEntity
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.internal.MetaverseSkin
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.minecraft.network.protocol.game.PacketPlayOutEntity
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata
import net.minecraft.network.protocol.game.PacketPlayOutMount
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving
import net.minecraft.server.level.EntityPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.decoration.EntityArmorStand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

abstract class TaskBase: BukkitRunnable() {
    protected open val maxTick = 3 * 60 * 20
    protected val runAllPlayers = Bukkit.getOnlinePlayers()::forEach

    protected val npcs = mutableListOf<EntityPlayer>()
    protected val armorStands = mutableListOf<EntityArmorStand>()

    protected var tick = -1

    abstract val mainLocation: Location

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
        despawnAllNpcs()
        runAllPlayers {
            it.hideBossBar(bossBar)
        }

        super.cancel()
    }

    protected fun updateNpc() {
        npcs.forEach { npc ->
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutEntityHeadRotation(npc,
                ((npc.yRot * 256) / 360).toInt().toByte())
            ))
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutEntity.PacketPlayOutEntityLook(npc.id,
                    ((( npc.yRot + (if (-180 < npc.yRot && npc.yRot < 0 || 180 < npc.yRot && npc.yRot < 360) { -45 } else { 45 })) * 256) / 360).toInt().toByte(),
                    ((npc.xRot * 256) / 360).toInt().toByte(),
                    true)
            ))
        }
    }

    protected fun spawnNpcs(npcs: List<EntityPlayer>) {
        Metaverse.protocolManager.broadcastServerPacket(PacketContainer(PacketType.Play.Server.PLAYER_INFO).apply {
            playerInfoAction.write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER)
            playerInfoDataLists.write(0, npcs.map {
                PlayerInfoData(WrappedGameProfile.fromPlayer(it.bukkitEntity), 0,
                    EnumWrappers.NativeGameMode.CREATIVE, WrappedChatComponent.fromText(""))
            })
        })
        npcs.map { npc ->
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(PacketPlayOutNamedEntitySpawn(npc)))
        }
    }

    protected fun spawnArmorStands(armorStands: List<EntityArmorStand>) {
        armorStands.forEach { armorStand ->
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutSpawnEntityLiving(armorStand)))
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutMount(armorStand.bukkitEntity.handle)))
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutEntityMetadata(
                    armorStand.id,
                    armorStand.bukkitEntity.handle.dataWatcher,
                    false)
            ))
        }
    }

    protected fun spawnNpcsInLocations(locations: List<Location>, name: String, skin: MetaverseSkin, isSeated: Boolean = false) {
        val startArmorStandIndex = armorStands.size
        val startNpcIndex = npcs.size
        locations.forEach { location ->
            if (isSeated) {
                armorStands += me.cragon.metaverse.internal.FakeEntity.spawnFakeArmorStand(location.clone().apply { y -= 1.8 }).apply {
                    (bukkitEntity as CraftArmorStand).isInvisible = true
                }.also { armorStand ->
                    npcs += me.cragon.metaverse.internal.FakeEntity.spawnFakePlayer(name, location, skin).apply {
                        startRiding((armorStand.bukkitEntity as CraftArmorStand).handle)
                    }
                }
            } else {
                npcs += me.cragon.metaverse.internal.FakeEntity.spawnFakePlayer(name, location, skin)
            }
        }

        spawnNpcs(npcs.drop(startNpcIndex))
        if (isSeated) {
            spawnArmorStands(armorStands.drop(startArmorStandIndex))
        }
        updateNpc()
    }

    protected fun despawnNpcs(npcs: List<EntityPlayer>) {
        npcs.forEach { npc ->
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc)
            ))
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutEntityDestroy(npc.id)
            ))
        }
        this.npcs -= npcs
    }

    @Suppress("UNCHECKED_CAST")
    protected fun despawnEntities(entities: List<Entity>) {
        entities.forEach { entity ->
            Metaverse.protocolManager.broadcastServerPacket(PacketContainer.fromPacket(
                PacketPlayOutEntityDestroy(entity.id)
            ))
        }
        if (entities.all { it is EntityArmorStand }) {
            this.armorStands -= entities as List<EntityArmorStand>
        }
    }

    protected fun despawnAllNpcs() {
        despawnNpcs(npcs)
        despawnEntities(armorStands)
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
