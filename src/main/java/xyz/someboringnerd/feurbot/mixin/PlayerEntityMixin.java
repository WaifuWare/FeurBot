package xyz.someboringnerd.feurbot.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
    @Shadow public abstract void jump();

    int frame = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    void AntiAFK(CallbackInfo ci)
    {
        // simple anti afk for 0B0T.
        // player will make a jump action every 600 frames (~ 10 seconds)
        if(frame >= 600){
            this.jump();
            frame = 0;
        }else{
            frame++;
        }
    }
}
