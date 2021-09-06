package me.cragon.metaverse.internal

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import me.cragon.metaverse.Metaverse
import net.minecraft.server.level.EntityPlayer
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.entity.decoration.EntityArmorStand
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_17_R1.CraftServer
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld
import java.util.*

object FakeEntity {
     fun spawnFakePlayer(name: String, loc: Location, skin: MetaverseSkin): EntityPlayer {
        val profile = GameProfile(UUID.randomUUID(), name)
        profile.properties.put("textures", skin.property)
        val server = (Bukkit.getServer() as CraftServer).server
        val world = (Metaverse.mainWorld as CraftWorld).handle
        val npc = EntityPlayer(server, world, profile)
        npc.setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)
        npc.headRotation = loc.yaw
        npc.m(loc.yaw)
        return npc
    }

   fun spawnFakeArmorStand(loc: Location): EntityArmorStand {
      val world = (Metaverse.mainWorld as CraftWorld).handle
      val armorStand = EntityArmorStand(EntityTypes.c, world)
      armorStand.setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)
      return armorStand
   }
}
