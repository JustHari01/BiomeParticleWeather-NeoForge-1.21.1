package org.shadowmaster435.biomeparticleweather;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.shadowmaster435.biomeparticleweather.util.ConfigFile;
import org.shadowmaster435.biomeparticleweather.util.ModResourceLoader;

@Mod(BiomeParticleWeather.MOD_ID)
public class BiomeParticleWeather {
    public static final String MOD_ID = "biomeparticleweather";
    public static final SimpleParticleType RAIN = new SimpleParticleType(false);
    public static final SimpleParticleType RAIN_SPLASH = new SimpleParticleType(false);
    public static final SimpleParticleType RAIN_TRAIL = new SimpleParticleType(false);
    public static final SimpleParticleType RAIN_RIPPLE = new SimpleParticleType(false);
    public static final SimpleParticleType TUMBLE_BUSH = new SimpleParticleType(false);
    public static final SimpleParticleType LIGHTNING = new SimpleParticleType(true);
    public static final SimpleParticleType LIGHTNING_NODE = new SimpleParticleType(true);
    public static final SimpleParticleType FOG = new SimpleParticleType(false);
    public static final SimpleParticleType FIREFLY = new SimpleParticleType(false);
    public static final SimpleParticleType WIND = new SimpleParticleType(false);
    public static final SimpleParticleType WIND_TRAIL = new SimpleParticleType(false);
    public static final SimpleParticleType SAND_MOTE = new SimpleParticleType(false);
    public static final SimpleParticleType RED_SAND_MOTE = new SimpleParticleType(false);
    public static final SimpleParticleType SNOW = new SimpleParticleType(false);
    public static final SimpleParticleType BLIZZARD_WIND = new SimpleParticleType(false);
    public static final SimpleParticleType BLIZZARD_SNOW = new SimpleParticleType(false);
    public static final SimpleParticleType WEEPING_TEAR = new SimpleParticleType(false);
    public static final SimpleParticleType WEEPING_TEAR_SPLASH = new SimpleParticleType(false);
    public static final SimpleParticleType UPDRAFT = new SimpleParticleType(false);
    public static final SimpleParticleType STORM_SOUL = new SimpleParticleType(false);
    public static final SimpleParticleType STORM_SOUL_IMPACT = new SimpleParticleType(false);
    public static final SimpleParticleType WARPED_SPORE = new SimpleParticleType(false);
    public static final SimpleParticleType STEAM_JET = new SimpleParticleType(false);
    public static final SimpleParticleType CUSTOM = new SimpleParticleType(false);
    public static TextureAtlas provider;
    /** NeoForge-owned registration queue; registration is completed before the vanilla registry freezes. */
    public static final DeferredRegister<net.minecraft.core.particles.ParticleType<?>> PARTICLES =
        DeferredRegister.create(Registries.PARTICLE_TYPE, MOD_ID);

    static {
        register("rain", RAIN);
        register("rain_splash", RAIN_SPLASH);
        register("rain_trail", RAIN_TRAIL);
        register("tumble_bush", TUMBLE_BUSH);
        register("lightning", LIGHTNING);
        register("lightning_node", LIGHTNING_NODE);
        register("rain_ripple", RAIN_RIPPLE);
        register("fog", FOG);
        register("firefly", FIREFLY);
        register("wind", WIND);
        register("wind_trail", WIND_TRAIL);
        register("sand_mote", SAND_MOTE);
        register("red_sand_mote", RED_SAND_MOTE);
        register("snow", SNOW);
        register("blizzard_wind", BLIZZARD_WIND);
        register("blizzard_snow", BLIZZARD_SNOW);
        register("warped_spore", WARPED_SPORE);
        register("storm_soul", STORM_SOUL);
        register("storm_soul_impact", STORM_SOUL_IMPACT);
        register("weeping_tear", WEEPING_TEAR);
        register("weeping_tear_splash", WEEPING_TEAR_SPLASH);
        register("updraft", UPDRAFT);
        register("steam_jet", STEAM_JET);
        register("custom", CUSTOM);
    }

    public BiomeParticleWeather(IEventBus modBus, Dist dist, ModContainer ignoredContainer) {
        PARTICLES.register(modBus);
        if (dist == Dist.CLIENT) {
            modBus.addListener(org.shadowmaster435.biomeparticleweather.client.BiomeParticleWeatherClient::registerParticleProviders);
            modBus.addListener(org.shadowmaster435.biomeparticleweather.client.BiomeParticleWeatherClient::registerKeyMappings);
            modBus.addListener(ModResourceLoader::registerReloadListeners);
            org.shadowmaster435.biomeparticleweather.client.BiomeParticleWeatherClient.initClient(modBus);
        }
        onInitialize();
    }

    public void onInitialize() {
        ConfigFile.ensure_existance();
        ConfigFile.load_config();
    }

    private static void register(String path, SimpleParticleType type) {
        PARTICLES.register(path, () -> type);
    }
}
