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

package com.awesomeman.xtraconsole.util;

import java.io.File;
import java.io.IOException;

import com.awesomeman.xtraconsole.XtraConsole;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Utils {
    
    private CommentedConfigurationNode node;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    
    public void init(File file, CommentedConfigurationNode node, ConfigurationLoader<CommentedConfigurationNode> loader) {
        this.node = node;
        this.loader = loader;
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                XtraConsole.instance.logger.error("Could not create config file!");
                e.printStackTrace();
            }
            initNode();
            this.node.getNode("say").setValue("[Console] ").setComment("What to prefix the message with when using /say");
            saveNode();
        } else {
            initNode();
        }
    }
    
    private void initNode() {
        try {
            node = loader.load();
        } catch(IOException e) {
            XtraConsole.instance.logger.error("Error while initializing configuration node!");
            e.printStackTrace();
        }
    }
    
    private void saveNode() {
        try {
            loader.save(node);
        } catch(IOException e) {
            XtraConsole.instance.logger.error("Error while initializing configuration node!");
            e.printStackTrace();
        }
    }
    
    public void setSay(String say) {
        node.getNode("say").setValue(say.concat(" "));
    }
    
    public String getSay() {
        return node.getNode("say").getString();
    }
}
