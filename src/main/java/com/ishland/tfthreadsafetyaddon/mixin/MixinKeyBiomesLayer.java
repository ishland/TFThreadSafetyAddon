package com.ishland.tfthreadsafetyaddon.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.world.components.layer.KeyBiomesLayer;

import java.util.Random;

@Mixin(KeyBiomesLayer.class)
public class MixinKeyBiomesLayer {

    @Redirect(method = "applyPixel", at = @At(value = "FIELD", target = "Ltwilightforest/world/components/layer/KeyBiomesLayer;RANDOM:Ljava/util/Random;", opcode = Opcodes.GETSTATIC), require = 5)
    private Random replaceRandom(@Share("tfthreadsafetyaddon$random") LocalRef<Random> randomLocalRef) {
        if (randomLocalRef.get() == null) {
            randomLocalRef.set(new Random());
        }
        Random random = randomLocalRef.get();
        assert random != null;
        return random;
    }

}
