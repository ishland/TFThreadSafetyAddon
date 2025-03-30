package com.ishland.tfthreadsafetyaddon.mixin;

import com.ishland.tfthreadsafetyaddon.TFThreadSafetyAddon;
import com.ishland.tfthreadsafetyaddon.common.ducks.LazyAreaExtension;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.world.components.layer.vanillalegacy.Area;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;

@Mixin(LazyArea.class)
public class MixinLazyArea implements LazyAreaExtension {

    @Shadow @Final private Area transformer;
    @Shadow @Final private int maxCache;
    private Long2ObjectLinkedOpenHashMap<RegistryKey<Biome>> tfthreadsafetyaddon$cachedSamples;

    @Override
    public void tfthreadsafetyaddon$setCachedSamples(Long2ObjectLinkedOpenHashMap<RegistryKey<Biome>> cachedSamples) {
        this.tfthreadsafetyaddon$cachedSamples = cachedSamples;
    }

    @WrapMethod(method = "getBiome")
    private RegistryKey<Biome> wrapSample(int biomeX, int biomeZ, Operation<RegistryKey<Biome>> original) {
        Long2ObjectLinkedOpenHashMap<RegistryKey<Biome>> tfthreadsafetyaddon$cachedSamples1 = this.tfthreadsafetyaddon$cachedSamples;
        if (tfthreadsafetyaddon$cachedSamples1 == null) {
            TFThreadSafetyAddon.LOGGER.warn("LazyArea.tfthreadsafetyaddon$cachedSamples is null", new Throwable());
            return original.call(biomeX, biomeZ);
        }
        long i = ChunkPos.toLong(biomeX, biomeZ);
        synchronized (tfthreadsafetyaddon$cachedSamples1) {
            RegistryKey<Biome> j = tfthreadsafetyaddon$cachedSamples1.getAndMoveToLast(i);
            if (j != null && j != BiomeKeys.THE_VOID) {
                return j;
            } else {
                RegistryKey<Biome> k = this.transformer.getBiome(biomeX, biomeZ);
                tfthreadsafetyaddon$cachedSamples1.put(i, k);
                if (tfthreadsafetyaddon$cachedSamples1.size() > this.maxCache) {
                    for (int l = 0; l < this.maxCache / 16; ++l) {
                        tfthreadsafetyaddon$cachedSamples1.removeFirst();
                    }
                }

                return k;
            }
        }
    }

}
