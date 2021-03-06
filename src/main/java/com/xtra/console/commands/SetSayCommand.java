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

package com.xtra.console.commands;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.xtra.core.command.annotation.RegisterCommand;
import com.xtra.core.command.base.CommandBase;
import com.xtra.core.config.Config;
import com.xtra.core.config.ConfigHandler;

@RegisterCommand
public class SetSayCommand extends CommandBase<CommandSource> {

    @Override
    public String[] aliases() {
        return new String[] {"set-console", "setconsole"};
    }

    @Override
    public CommandElement[] args() {
        return new CommandElement[] {GenericArguments.remainingJoinedStrings(Text.of("say"))};
    }

    @Override
    public String description() {
        return "Sets the say prefix.";
    }

    @Override
    public String permission() {
        return "xtraconsole.set";
    }

    @Override
    public CommandResult executeCommand(CommandSource src, CommandContext args) throws Exception {
        Optional<String> say = args.<String>getOne("say");
        if (!say.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Console arg is not specified!"));
            return CommandResult.empty();
        }
        Config config = ConfigHandler.getConfig(com.xtra.console.config.Config.class);
        config.rootNode().getNode("say").setValue(say.get());
        config.save();
        src.sendMessage(Text.of(TextColors.GOLD, "Successfully set the console prefix."));
        return CommandResult.success();
    }
}
