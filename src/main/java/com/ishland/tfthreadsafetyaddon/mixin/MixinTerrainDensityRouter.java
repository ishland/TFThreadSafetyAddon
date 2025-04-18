package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.BiomeDensitySourceExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.registry.entry.RegistryEntry;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.world.components.chunkgenerators.TerrainDensityRouter;
import twilightforest.world.components.layer.BiomeDensitySource;

@Mixin(TerrainDensityRouter.class)
public class MixinTerrainDensityRouter {

    @WrapOperation(method = "apply", at = @At(value = "FIELD", target = "Ltwilightforest/world/components/chunkgenerators/TerrainDensityRouter;biomeDensitySourceHolder:Lnet/minecraft/registry/entry/RegistryEntry;", opcode = Opcodes.GETFIELD))
    private RegistryEntry<BiomeDensitySource> isolateCache(TerrainDensityRouter instance, Operation<RegistryEntry<BiomeDensitySource>> original) {
        return new RegistryEntry.Direct<>(((BiomeDensitySourceExtension) original.call(instance).value()).tfthreadsafetyaddon$recreate());
    }

}
