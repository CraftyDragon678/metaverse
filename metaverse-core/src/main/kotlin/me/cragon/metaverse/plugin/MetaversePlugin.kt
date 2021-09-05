package me.cragon.metaverse.plugin

import io.github.monun.kommand.kommand
import io.github.monun.tap.fake.FakeEntityServer
import io.github.monun.tap.fake.FakeProjectileManager
import me.cragon.metaverse.Metaverse
import me.cragon.metaverse.command.KommandMetaverse
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class MetaversePlugin: JavaPlugin(), Listener {
    private lateinit var fakeEntityServer: FakeEntityServer

    private lateinit var fakeProjectileManager: FakeProjectileManager

    override fun onEnable() {
        logger.info("Enabled!")
        kommand {
            KommandMetaverse().register(this@MetaversePlugin, this)
        }
        loadModules()
        server.scheduler.runTaskTimer(this, SchedulerTask(fakeEntityServer, fakeProjectileManager), 0L, 1L)
    }

    private fun loadModules() {
        fakeEntityServer = FakeEntityServer.create(this).apply {
            Bukkit.getOnlinePlayers().forEach { addPlayer(it) }
        }
        fakeProjectileManager = FakeProjectileManager()
        server.pluginManager.registerEvents(this, this)
        Metaverse.initialize(this, fakeEntityServer, fakeProjectileManager)
    }

    override fun onDisable() {
        Metaverse.stopTask()
        fakeEntityServer.shutdown()
        fakeProjectileManager.clear()
    }

    @EventHandler
    fun onPlayerPreJoin(e: AsyncPlayerPreLoginEvent) {
        e.playerProfile.setProperty(Metaverse.joinSkin.profileProperty)
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        fakeEntityServer.addPlayer(e.player)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        fakeEntityServer.removePlayer(e.player)
    }

//    @EventHandler
//    fun onPlayerInteract(e: PlayerInteractEvent) {
//        if (e.action == Action.LEFT_CLICK_AIR && e.item?.type == Material.LIGHT_BLUE_STAINED_GLASS) {
//            val projectile = Projectile().apply {
//                projectile = fakeEntityServer.spawnEntity(e.player.eyeLocation, ArmorStand::class.java).apply {
//                    updateMetadata<ArmorStand> {
//                        isVisible = false
//                        isMarker = true
//                    }
//                    updateEquipment {
//                        helmet = ItemStack(Material.LIGHT_BLUE_STAINED_GLASS)
//                    }
//                }
//                velocity = e.player.eyeLocation.direction
//            }
//            fakeProjectileManager.launch(e.player.eyeLocation, projectile)
//        }
//    }
//
//    inner class Projectile: FakeProjectile(1200, 128.0) {
//        lateinit var projectile: FakeEntity
//
//        override fun onPreUpdate() {
//            velocity = velocity.apply { y -= 0.05 }
//        }
//
//        override fun onMove(movement: Movement) {
//            projectile.moveTo(movement.to.clone().apply { y -= 1.62 })
//        }
//
//        override fun onTrail(trail: Trail) {
//            trail.velocity?.let { v ->
//                val length = v.normalizeAndLength()
//                if (length > 0.0) {
//                    val start = trail.from
//                    val world = start.world
//
//                    world.rayTrace(start, v, length, FluidCollisionMode.NEVER, true, 1.0) { false }?.let {
//                        remove()
//                    }
//                }
//            }
//        }
//
//        override fun onRemove() {
//            projectile.remove()
//        }
//    }
//
//    @EventHandler
//    fun onBlockChange(e: EntityBlockFormEvent) {
//        println(e.block.type.name)
//    }
}
