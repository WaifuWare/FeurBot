package xyz.someboringnerd.feurbot.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.someboringnerd.feurbot.Util.Timer;


@Mixin(DisconnectedScreen.class)
public class DisconnectScreenMixin
{

    @Shadow @Final private Screen parent;
    Timer timer;

    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo ci)
    {
        timer = new Timer();
        timer.reset();
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        if(timer.hasPassed(3000))
        {
            ServerInfo server = new ServerInfo("Server", "0b0t.org" + ":" + 25565, false);
            ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), MinecraftClient.getInstance(), ServerAddress.parse(server.address), server);
        }
    }
}
