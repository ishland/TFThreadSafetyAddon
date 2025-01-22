package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.BiomeDensitySourceExtension;
import com.ishland.tfthreadsafetyaddon.common.ducks.TFBiomeProviderExtension;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.layer.BiomeDensitySource;

@Mixin(TFBiomeProvider.class)
public class MixinTFBiomeProvider implements TFBiomeProviderExtension {

    @Shadow @Final private RegistryEntry<BiomeDensitySource> biomeTerrainDataHolder;

    @Override
    public TFBiomeProvider tfthreadsafetyaddon$recreate() {
        return new TFBiomeProvider(new RegistryEntry.Direct<>(((BiomeDensitySourceExtension) this.biomeTerrainDataHolder.value()).tfthreadsafetyaddon$recreate()));
    }

}
