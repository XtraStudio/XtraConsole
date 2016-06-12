/**
 * This file is part of XtraConsole, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 XtraStudio <https://github.com/XtraStudio>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.xtra.console;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;
import com.xtra.console.config.Config;
import com.xtra.core.Core;
import com.xtra.core.command.CommandRegistrar;
import com.xtra.core.config.ConfigHandler;

@Plugin(name = PluginInfo.NAME, id = PluginInfo.ID, version = PluginInfo.VERSION, description = PluginInfo.DESCRIPTION)
public class XtraConsole {

    private @Inject Logger logger;

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        logger.info("Initializing XtraConsole version " + PluginInfo.VERSION);

        Core.initialize(this);
        ConfigHandler.create();
    }

    @Listener
    public void init(GameInitializationEvent event) {
        CommandRegistrar.create();
    }

    @Listener
    public void reload(GameReloadEvent event) {
        ConfigHandler.getConfig(Config.class).load();
    }
}
