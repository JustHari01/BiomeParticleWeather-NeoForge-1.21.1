package org.shadowmaster435.biomeparticleweather.compat;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.util.RandomSource;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;

/**
 * NeoForge adapter for the old Fabric sprite-provider abstraction.  The
 * particle implementation only needs the normal SpriteSet operations plus
 * access to the particle atlas for custom voxel UVs.
 */
public interface FabricSpriteProvider extends SpriteSet {
    default TextureAtlas getAtlas() {
        return BiomeParticleWeather.provider;
    }

    static FabricSpriteProvider wrap(SpriteSet delegate) {
        return new FabricSpriteProvider() {
            @Override
            public net.minecraft.client.renderer.texture.TextureAtlasSprite get(int age, int lifetime) {
                return delegate.get(age, lifetime);
            }

            @Override
            public net.minecraft.client.renderer.texture.TextureAtlasSprite get(RandomSource random) {
                return delegate.get(random);
            }
        };
    }
}
