package com.ishland.tfthreadsafetyaddon.common.ducks;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public interface LazyAreaExtension {

    void tfthreadsafetyaddon$setCachedSamples(Long2ObjectLinkedOpenHashMap<RegistryKey<Biome>> cachedSamples);

}
