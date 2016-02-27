/*
The MIT License (MIT)

Copyright (c) 2016 12AwesomeMan34 / 12AwsomeMan34

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.awesomeman.xtraconsole;

import java.io.File;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.awesomeman.xtraconsole.commands.SayCommand;
import com.awesomeman.xtraconsole.commands.SetSayCommand;
import com.awesomeman.xtraconsole.util.Utils;
import com.google.inject.Inject;

@Plugin(name = "XtraConsole", id = "XtraConsole", version = XtraConsole.VERSION)
public class XtraConsole {
    
    public static XtraConsole instance;
    public @Inject Logger logger;
    public Utils utils;
    protected static final String VERSION = "1.0";
    private CommentedConfigurationNode node;
    private @Inject @DefaultConfig(sharedRoot = true) File config;
    private @Inject @DefaultConfig(sharedRoot = true) ConfigurationLoader<CommentedConfigurationNode> loader;
    
    @Listener
    public void preInit(GamePreInitializationEvent event) {
        logger.info("Initializing XtraConsole version " + VERSION);
        instance = this;
        utils = new Utils();
        utils.init(config, node, loader);
    }
    
    @Listener
    public void init(GameInitializationEvent event) {        
        CommandSpec sayCommand = CommandSpec.builder()
                .permission("xtraconsole.say")
                .description(Text.of("Says a message as if it were from the console!"))
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new SayCommand())
                .build();
        
        CommandSpec setSayCommand = CommandSpec.builder()
                .permission("xtraconsole.set")
                .description(Text.of("Sets the say prefix."))
                .arguments(GenericArguments.string(Text.of("say")))
                .executor(new SetSayCommand())
                .build();
        
        CommandManager service = Sponge.getCommandManager();
        
        service.register(this, sayCommand, "say");
        service.register(this, setSayCommand, "set-console", "setconsole");
    }
}
