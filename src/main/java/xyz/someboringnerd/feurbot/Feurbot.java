package xyz.someboringnerd.feurbot;

import net.fabricmc.api.ModInitializer;
import xyz.someboringnerd.feurbot.modules.ModuleManager;

public class Feurbot implements ModInitializer {
    @Override
    public void onInitialize() {
        new ModuleManager();
    }
}
