package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.StrongholdPieceWeightExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import twilightforest.world.components.structures.stronghold.StrongholdPieceWeight;
import twilightforest.world.components.structures.stronghold.StrongholdPieces;

@Mixin(StrongholdPieces.class)
public class MixinStrongholdPieces {

    @WrapOperation(method = "prepareStructurePieces", at = @At(value = "FIELD", target = "Ltwilightforest/world/components/structures/stronghold/StrongholdPieces;pieceWeightArray:[Ltwilightforest/world/components/structures/stronghold/StrongholdPieceWeight;", opcode = Opcodes.GETSTATIC, remap = false), remap = false)
    private StrongholdPieceWeight[] wrapPieces(Operation<StrongholdPieceWeight[]> original) {
        StrongholdPieceWeight[] weights = original.call().clone(); // not the most efficient
        for (int i = 0, weightsLength = weights.length; i < weightsLength; i++) {
            weights[i] = ((StrongholdPieceWeightExtension) weights[i]).clone();
        }
        return weights;
    }

}
