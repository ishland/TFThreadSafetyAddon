package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.StrongholdPieceWeightExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.world.components.structures.stronghold.StrongholdPieceWeight;

@Mixin(StrongholdPieceWeight.class)
public class MixinStrongholdPieceWeight implements StrongholdPieceWeightExtension, Cloneable {

    @Shadow(remap = false) public int instancesSpawned;

    @Override
    public StrongholdPieceWeight clone() {
        try {
            StrongholdPieceWeight cloned = (StrongholdPieceWeight) super.clone();
            ((MixinStrongholdPieceWeight) (Object) cloned).instancesSpawned = 0;
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
