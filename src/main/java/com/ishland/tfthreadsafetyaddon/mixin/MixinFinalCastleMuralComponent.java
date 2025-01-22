package com.ishland.tfthreadsafetyaddon.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import twilightforest.world.components.structures.finalcastle.FinalCastleMuralComponent;

@Mixin(FinalCastleMuralComponent.class)
public class MixinFinalCastleMuralComponent {

    @WrapMethod(method = "generate")
    private void synchronizeGenerate(StructureWorldAccess world, StructureAccessor manager, ChunkGenerator generator, Random rand, BlockBox sbb, ChunkPos chunkPosIn, BlockPos blockPos, Operation<Void> original) {
        synchronized (this) { // not very efficient
            original.call(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
        }
    }

}
