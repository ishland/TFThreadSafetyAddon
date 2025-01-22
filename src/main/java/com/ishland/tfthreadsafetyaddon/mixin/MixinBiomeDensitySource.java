package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.common.ducks.BiomeDensitySourceExtension;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.world.components.chunkgenerators.TerrainColumn;
import twilightforest.world.components.layer.BiomeDensitySource;
import twilightforest.world.components.layer.vanillalegacy.BiomeLayerFactory;

import java.util.Map;

@Mixin(BiomeDensitySource.class)
public class MixinBiomeDensitySource implements BiomeDensitySourceExtension {

    @Shadow(remap = false) @Final private Map<RegistryKey<Biome>, TerrainColumn> biomeList;

    @Shadow(remap = false) @Final private RegistryEntry<BiomeLayerFactory> genBiomeConfig;

    @Override
    public BiomeDensitySource tfthreadsafetyaddon$recreate() {
        return new BiomeDensitySource(this.biomeList, this.genBiomeConfig);
    }

}
