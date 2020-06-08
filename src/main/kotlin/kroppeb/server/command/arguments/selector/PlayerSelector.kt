/*
 * Copyright (c) 2020 Kroppeb
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package kroppeb.server.command.arguments.selector

import kroppeb.server.command.reader.ReadFactory
import kroppeb.server.command.reader.Reader
import kroppeb.server.command.reader.ReaderException
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d

interface PlayerSelector : Selector {
	override fun getEntities(world: ServerWorld, pos: Vec3d?, executor: Entity?): Collection<Entity> {
		return getPlayers(world, pos, executor)
	}

	fun getPlayers(world: ServerWorld, pos: Vec3d?, executor: Entity?): Collection<PlayerEntity>
	fun getPlayers(source: ServerCommandSource): Collection<PlayerEntity?>? {
		return getPlayers(source.world, source.position, source.entity)
	}

	companion object : ReadFactory<PlayerSelector> {
		@Throws(ReaderException::class)
		override fun Reader.parse(): PlayerSelector {
			val selector = Selector.read(this)
			if (selector is PlayerSelector) return selector
			throw ReaderException("not limited to players")
		}
	}
}