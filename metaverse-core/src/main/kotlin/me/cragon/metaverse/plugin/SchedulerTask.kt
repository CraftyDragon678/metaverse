package me.cragon.metaverse.plugin

import io.github.monun.tap.fake.FakeEntityServer
import io.github.monun.tap.fake.FakeProjectileManager

class SchedulerTask(private val fakeEntityServer: FakeEntityServer, private val fakeProjectileManager: FakeProjectileManager): Runnable {
    override fun run() {
        fakeEntityServer.update()
        fakeProjectileManager.update()
    }
}
