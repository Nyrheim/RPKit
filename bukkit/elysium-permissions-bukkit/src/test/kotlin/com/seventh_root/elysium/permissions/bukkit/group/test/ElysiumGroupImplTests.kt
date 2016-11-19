/*
 * Copyright 2016 Ross Binden
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seventh_root.elysium.permissions.bukkit.group.test

import com.seventh_root.elysium.permissions.bukkit.group.ElysiumGroupImpl
import io.kotlintest.specs.WordSpec

class ElysiumGroupImplTests : WordSpec() {

    init {
        val inheritedGroup = ElysiumGroupImpl(
                name = "test inherited",
                allow = listOf(
                        "elysium.permissions.inherited.test"
                ),
                deny = listOf(),
                inheritance = listOf()
        )
        val group = ElysiumGroupImpl(
                name = "test group",
                allow = listOf(
                        "elysium.permissions.test"
                ),
                deny = listOf(
                        "elysium.permissions.denied.test"
                ),
                inheritance = listOf(
                        inheritedGroup
                )
        )
        "ElysiumGroupImpl.deserialize" should {
            "return an equivalent instance to what was serialized" {
                ElysiumGroupImpl.deserialize(group.serialize()) shouldEqual group
            }
        }
    }

}