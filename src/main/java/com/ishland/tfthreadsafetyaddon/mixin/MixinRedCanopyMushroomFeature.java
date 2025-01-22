package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.CloningExtension;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.world.components.feature.trees.RedCanopyMushroomFeature;

@Mixin(RedCanopyMushroomFeature.class)
public class MixinRedCanopyMushroomFeature implements CloningExtension, Cloneable {

    @Unique
    private boolean tfthreadsafetyaddon$cloned;

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void wrapGenerate(FeatureContext<HugeMushroomFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        if (!this.tfthreadsafetyaddon$isCloned()) {
            RedCanopyMushroomFeature cloned = this.clone();
            ((MixinRedCanopyMushroomFeature) (Object) cloned).tfthreadsafetyaddon$cloned = true;
            cir.setReturnValue(cloned.generate(context)); // also cancels
        }
    }

    @Override
    public RedCanopyMushroomFeature clone() {
        try {
            return (RedCanopyMushroomFeature) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean tfthreadsafetyaddon$isCloned() {
        return this.tfthreadsafetyaddon$cloned;
    }

}
