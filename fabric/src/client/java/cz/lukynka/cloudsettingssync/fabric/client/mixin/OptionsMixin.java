package cz.lukynka.cloudsettingssync.fabric.client.mixin;

import cz.lukynka.cloudsettingssync.fabric.client.FabricClient;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsMixin {

    @Inject(at = @At("TAIL"), method = "save")
    public void save(CallbackInfo ci) {
        FabricClient.updateCloudSettings();
    }
}
