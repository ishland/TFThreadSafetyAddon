package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.TFBiomeProviderExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.world.components.biomesources.TFBiomeProvider;

@Mixin(NoiseChunkGenerator.class)
public class MixinNoiseChunkGenerator {

    @WrapOperation(method = "populateBiomes(Lnet/minecraft/world/gen/chunk/Blender;Lnet/minecraft/world/gen/noise/NoiseConfig;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/chunk/Chunk;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/chunk/NoiseChunkGenerator;biomeSource:Lnet/minecraft/world/biome/source/BiomeSource;", opcode = Opcodes.GETFIELD))
    private BiomeSource recreateBiomeResolver(NoiseChunkGenerator instance, Operation<BiomeSource> original) {
        BiomeSource value = original.call(instance);
        if (value instanceof TFBiomeProvider tfBiomeProvider) {
            return ((TFBiomeProviderExtension) tfBiomeProvider).tfthreadsafetyaddon$recreate();
        } else {
            return value;
        }
    }

}
