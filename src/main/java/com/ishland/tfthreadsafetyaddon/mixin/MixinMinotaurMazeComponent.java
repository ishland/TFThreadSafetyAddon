package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.TFMazeExtension;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.world.components.structures.TFMaze;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.minotaurmaze.MinotaurMazeComponent;

@Mixin(MinotaurMazeComponent.class)
public abstract class MixinMinotaurMazeComponent extends StructurePiece {

    @Shadow(remap = false) @Final @Mutable private TFMaze maze;

    protected MixinMinotaurMazeComponent(StructurePieceType type, int length, BlockBox boundingBox) {
        super(type, length, boundingBox);
    }

    @ModifyReceiver(method = "generate", at = @At(value = "INVOKE", target = "Ltwilightforest/world/components/structures/TFMaze;copyToStructure(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;IIILtwilightforest/world/components/structures/TFStructureComponentOld;Lnet/minecraft/util/math/BlockBox;)V"))
    private TFMaze wrapGenerate(TFMaze instance, StructureWorldAccess world, StructureAccessor manager, ChunkGenerator generator, int dx, int dy, int dz, TFStructureComponentOld component, BlockBox sbb) {
        TFMaze cloned = ((TFMazeExtension) instance).clone();
        cloned.setSeed((long)this.boundingBox.getMinX() * 90342903L + (long)this.boundingBox.getMinY() * 90342903L ^ (long)this.boundingBox.getMinZ()); // TODO [VanillaCopy] setFixedMazeSeed
        return cloned;
    }

}
