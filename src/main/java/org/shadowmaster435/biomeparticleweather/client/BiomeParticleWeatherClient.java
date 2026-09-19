package org.shadowmaster435.biomeparticleweather.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.function.Function;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.shadowmaster435.biomeparticleweather.compat.FabricSpriteProvider;
import org.shadowmaster435.biomeparticleweather.BiomeParticleWeather;
import org.shadowmaster435.biomeparticleweather.gui.ConfigScreen;
import org.shadowmaster435.biomeparticleweather.particle.BlizzardSnow;
import org.shadowmaster435.biomeparticleweather.particle.BlizzardWind;
import org.shadowmaster435.biomeparticleweather.particle.Firefly;
import org.shadowmaster435.biomeparticleweather.particle.Fog;
import org.shadowmaster435.biomeparticleweather.particle.Lightning;
import org.shadowmaster435.biomeparticleweather.particle.LightningNode;
import org.shadowmaster435.biomeparticleweather.particle.Rain;
import org.shadowmaster435.biomeparticleweather.particle.RainRipple;
import org.shadowmaster435.biomeparticleweather.particle.RainSplash;
import org.shadowmaster435.biomeparticleweather.particle.RainTrail;
import org.shadowmaster435.biomeparticleweather.particle.RedSandMote;
import org.shadowmaster435.biomeparticleweather.particle.SandMote;
import org.shadowmaster435.biomeparticleweather.particle.Snow;
import org.shadowmaster435.biomeparticleweather.particle.SteamJet;
import org.shadowmaster435.biomeparticleweather.particle.StormSoul;
import org.shadowmaster435.biomeparticleweather.particle.StormSoulImpact;
import org.shadowmaster435.biomeparticleweather.particle.TumbleBush;
import org.shadowmaster435.biomeparticleweather.particle.Updraft;
import org.shadowmaster435.biomeparticleweather.particle.WarpedSpore;
import org.shadowmaster435.biomeparticleweather.particle.WeepingTear;
import org.shadowmaster435.biomeparticleweather.particle.WeepingTearSplash;
import org.shadowmaster435.biomeparticleweather.particle.Wind;
import org.shadowmaster435.biomeparticleweather.particle.WindTrail;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticle;

/** NeoForge client-side wiring for all original particle factories and the config key. */
public final class BiomeParticleWeatherClient {
    private static KeyMapping keyBinding;

    private BiomeParticleWeatherClient() {
    }

    public static void initClient(IEventBus ignoredModBus) {
        NeoForge.EVENT_BUS.addListener(BiomeParticleWeatherClient::onClientTick);
    }

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        keyBinding = new KeyMapping(
            "key.biomeparticleweather.config",
            InputConstants.Type.KEYSYM,
            66,
            "category.biomeparticleweather.config"
        );
        event.register(keyBinding);
    }

    public static void onClientTick(ClientTickEvent.Post ignored) {
        if (keyBinding == null) {
            return;
        }
        while (keyBinding.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new ConfigScreen(minecraft.screen));
        }
    }

    private static void register(
        RegisterParticleProvidersEvent event,
        SimpleParticleType type,
        Function<FabricSpriteProvider, ParticleProvider<SimpleParticleType>> factory
    ) {
        event.registerSpriteSet(type, sprites -> factory.apply(FabricSpriteProvider.wrap(sprites)));
    }

    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        register(event, BiomeParticleWeather.RAIN, Rain.Factory::new);
        register(event, BiomeParticleWeather.RAIN_SPLASH, RainSplash.Factory::new);
        register(event, BiomeParticleWeather.RAIN_TRAIL, RainTrail.Factory::new);
        register(event, BiomeParticleWeather.TUMBLE_BUSH, TumbleBush.Factory::new);
        register(event, BiomeParticleWeather.LIGHTNING, Lightning.Factory::new);
        register(event, BiomeParticleWeather.LIGHTNING_NODE, LightningNode.Factory::new);
        register(event, BiomeParticleWeather.RAIN_RIPPLE, RainRipple.Factory::new);
        register(event, BiomeParticleWeather.FOG, Fog.Factory::new);
        register(event, BiomeParticleWeather.FIREFLY, Firefly.Factory::new);
        register(event, BiomeParticleWeather.WIND, Wind.Factory::new);
        register(event, BiomeParticleWeather.WIND_TRAIL, WindTrail.Factory::new);
        register(event, BiomeParticleWeather.RED_SAND_MOTE, RedSandMote.Factory::new);
        register(event, BiomeParticleWeather.SAND_MOTE, SandMote.Factory::new);
        register(event, BiomeParticleWeather.STEAM_JET, SteamJet.Factory::new);
        register(event, BiomeParticleWeather.BLIZZARD_WIND, BlizzardWind.Factory::new);
        register(event, BiomeParticleWeather.BLIZZARD_SNOW, BlizzardSnow.Factory::new);
        register(event, BiomeParticleWeather.SNOW, Snow.Factory::new);
        register(event, BiomeParticleWeather.WARPED_SPORE, WarpedSpore.Factory::new);
        register(event, BiomeParticleWeather.UPDRAFT, Updraft.Factory::new);
        register(event, BiomeParticleWeather.STORM_SOUL, StormSoul.Factory::new);
        register(event, BiomeParticleWeather.STORM_SOUL_IMPACT, StormSoulImpact.Factory::new);
        register(event, BiomeParticleWeather.WEEPING_TEAR, WeepingTear.Factory::new);
        register(event, BiomeParticleWeather.WEEPING_TEAR_SPLASH, WeepingTearSplash.Factory::new);
        register(event, BiomeParticleWeather.CUSTOM, CustomParticle.Factory::new);
    }
}
