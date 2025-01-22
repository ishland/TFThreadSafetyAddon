package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.TFMazeExtension;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.world.components.structures.TFMaze;

@Mixin(TFMaze.class)
public class MixinTFMaze implements TFMazeExtension, Cloneable {

    @Shadow @Final @Mutable
    public Random rand;

    @Override
    public TFMaze clone() {
        try {
            TFMaze cloned = (TFMaze) super.clone();
            ((MixinTFMaze) (Object) cloned).rand = Random.create();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
