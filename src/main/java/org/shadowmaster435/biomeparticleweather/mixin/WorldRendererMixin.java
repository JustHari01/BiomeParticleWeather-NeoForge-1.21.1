/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.shadowmaster435.biomeparticleweather.util.ParticleSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LevelRenderer.class})
public class WorldRendererMixin {
    @Final
    @Shadow
    private Minecraft minecraft;
    @Shadow
    private int ticks;

    public WorldRendererMixin() {
    }

    @Inject(at={@At(value="HEAD")}, method={"renderSnowAndRain"}, cancellable=true)
    public void renderWeather(LightTexture manager, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        if (!ParticleSettings.get_bool("vanilla_rain_rendering")) {
            ci.cancel();
        }
    }

    @Inject(at={@At(value="HEAD")}, method={"tickRain"}, cancellable=true)
    public void tickRainSplashing(Camera camera, CallbackInfo ci) {
        if (!ParticleSettings.get_bool("vanilla_rain_rendering")) {
            float f = this.minecraft.level.getRainLevel(1.0f) / (Minecraft.useFancyGraphics() ? 1.0f : 2.0f);
            if (!(f <= 0.0f)) {
                RandomSource random = RandomSource.createNewThreadLocalInstance();
                ClientLevel worldView = this.minecraft.level;
                BlockPos blockPos = BlockPos.containing((Position)camera.getPosition());
                BlockPos blockPos2 = null;
                int i = (int)(100.0f * f * f) / (this.minecraft.options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);
                for (int j = 0; j < i; ++j) {
                    SimpleParticleType particleEffect;
                    Biome biome;
                    int l;
                    int k = random.nextInt(21) - 10;
                    BlockPos blockPos3 = worldView.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos.offset(k, 0, l = random.nextInt(21) - 10));
                    if (blockPos3.getY() <= worldView.getMinBuildHeight() || blockPos3.getY() > blockPos.getY() + 10 || blockPos3.getY() < blockPos.getY() - 10 || (biome = (Biome)worldView.getBiome(blockPos3).value()).getPrecipitationAt(blockPos3) != Biome.Precipitation.RAIN) continue;
                    blockPos2 = blockPos3.below();
                    if (this.minecraft.options.particles().get() == ParticleStatus.MINIMAL || ParticleSettings.get_bool("disable_vanilla_rain_splash")) break;
                    double d = random.nextDouble();
                    double e = random.nextDouble();
                    BlockState blockState = worldView.getBlockState(blockPos2);
                    FluidState fluidState = worldView.getFluidState(blockPos2);
                    VoxelShape voxelShape = blockState.getCollisionShape((BlockGetter)worldView, blockPos2);
                    double g = voxelShape.max(Direction.Axis.Y, d, e);
                    double h = fluidState.getHeight((BlockGetter)worldView, blockPos2);
                    double m = Math.max(g, h);
                    SimpleParticleType simpleParticleType = particleEffect = !fluidState.is(FluidTags.LAVA) && !blockState.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire((BlockState)blockState) ? ParticleTypes.RAIN : ParticleTypes.SMOKE;
                    if (!ParticleSettings.get_bool("vanilla_rain_splash")) continue;
                    this.minecraft.level.addParticle((ParticleOptions)particleEffect, (double)blockPos2.getX() + d, (double)blockPos2.getY() + m, (double)blockPos2.getZ() + e, 0.0, 0.0, 0.0);
                }
                if (blockPos2 != null && random.nextInt(3) < this.ticks++) {
                    this.ticks = 0;
                    if (blockPos2.getY() > blockPos.getY() + 1 && worldView.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY() > Mth.floor((float)blockPos.getY())) {
                        this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1f, 0.5f, false);
                    } else {
                        this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2f, 1.0f, false);
                    }
                }
            }
            ci.cancel();
        }
    }
}
