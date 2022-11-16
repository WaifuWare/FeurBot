package xyz.someboringnerd.feurbot.modules;

import xyz.someboringnerd.feurbot.modules.StaticBot.TPABot;

public class ModuleManager
{

    public static TPABot tpabot;

    public ModuleManager()
    {
        tpabot = new TPABot("tpabot", BOT_TYPE.TP_BOT);
    }

}


