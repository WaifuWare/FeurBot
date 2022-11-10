package xyz.someboringnerd.feurbot.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Pattern;

@Mixin(ChatHud.class)
public class ChatHudMixin
{
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At("HEAD"))
    void addMessage(Text message, CallbackInfo ci)
    {
        // remove the <${USERNAME> part of the message
        message = Text.literal(message.getString().replaceAll("<[a-zA-Z1-9]+>", ""));
    }
}
