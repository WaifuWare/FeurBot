package xyz.someboringnerd.feurbot.Util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil
{
    public static void ClientSideMessage(Text message)
    {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(message);
    }

    public static void SendMessageToChat(Text message)
    {
        MinecraftClient.getInstance().player.sendMessage(message);
    }
}
