package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.CloningExtension;
import com.ishland.tfthreadsafetyaddon.common.ducks.TFMazeExtension;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import twilightforest.world.components.structures.TFMaze;
import twilightforest.world.components.structures.minotaurmaze.MinotaurMazeComponent;

@Mixin(MinotaurMazeComponent.class)
public class MixinMinotaurMazeComponent implements CloningExtension, Cloneable {

    @Shadow(remap = false) @Final @Mutable private TFMaze maze;

    @Unique
    private boolean tfthreadsafetyaddon$cloned;

    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void wrapGenerate(StructureWorldAccess world, StructureAccessor manager, ChunkGenerator generator, Random rand, BlockBox sbb, ChunkPos chunkPosIn, BlockPos blockPos, CallbackInfo ci) {
        if (!this.tfthreadsafetyaddon$isCloned()) {
            MinotaurMazeComponent cloned = this.clone();
            ((MixinMinotaurMazeComponent) (Object) cloned).tfthreadsafetyaddon$cloned = true;
            ((MixinMinotaurMazeComponent) (Object) cloned).maze = ((TFMazeExtension) ((MixinMinotaurMazeComponent) (Object) cloned).maze).clone();
            ci.cancel();
            cloned.generate(world, manager, generator, rand, sbb, chunkPosIn, blockPos);
        }
    }

    @Override
    public boolean tfthreadsafetyaddon$isCloned() {
        return this.tfthreadsafetyaddon$cloned;
    }

    @Override
    public MinotaurMazeComponent clone() {
        try {
            return (MinotaurMazeComponent) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
