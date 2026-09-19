package org.shadowmaster435.biomeparticleweather.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import org.joml.Vector2i;
import org.shadowmaster435.biomeparticleweather.util.custom_particle.CustomParticleRegistry;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleModelPart;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleVoxel;
import org.shadowmaster435.biomeparticleweather.util.particle_model.ParticleVoxelParams;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelTransform;
import org.shadowmaster435.biomeparticleweather.util.particle_model.VoxelUVMap;

/** NeoForge resource-reload bridge for the model and custom-particle packs. */
public final class ModResourceLoader {
    public static final HashMap<String, ParticleModelPart> models = new HashMap<>();
    public static int counter = 0;

    private ModResourceLoader() {
    }

    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager manager, ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(Void ignored, ResourceManager manager, ProfilerFiller profiler) {
                loadModels(manager);
            }
        });
        event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
            @Override
            protected Void prepare(ResourceManager manager, ProfilerFiller profiler) {
                return null;
            }

            @Override
            protected void apply(Void ignored, ResourceManager manager, ProfilerFiller profiler) {
                loadCustomParticles(manager);
            }
        });
    }

    private static void loadModels(ResourceManager manager) {
        models.clear();
        manager.listResources("textures/particle_model", path -> path.getPath().endsWith(".png"))
            .keySet().stream().toList().forEach(id -> {
                try (InputStream stream = manager.getResource(id).orElseThrow().open()) {
                    build_model(id, stream);
                } catch (Exception exception) {
                    System.err.println("[BiomeParticleWeather] Could not load particle model " + id + ": " + exception);
                }
            });
    }

    private static void loadCustomParticles(ResourceManager manager) {
        CustomParticleRegistry.behaviors.clear();
        CustomParticleRegistry.sprites.clear();
        manager.listResources("particles/custom", path -> path.getPath().endsWith(".json"))
            .keySet().stream().toList().forEach(id -> {
                try (InputStream stream = manager.getResource(id).orElseThrow().open()) {
                    JsonObject json = JsonParser.parseString(new String(stream.readAllBytes())).getAsJsonObject();
                    CustomParticleRegistry.register(id.getPath(), json);
                } catch (Exception exception) {
                    System.err.println("[BiomeParticleWeather] Could not load custom particle " + id + ": " + exception);
                }
            });
    }

    public static void build_model(ResourceLocation id, InputStream stream) {
        ArrayList<ParticleVoxel> result = new ArrayList<>();
        BufferedImage image;
        try {
            image = ImageIO.read(stream);
        } catch (Exception exception) {
            System.err.println("[BiomeParticleWeather] Could not decode model " + id + ": " + exception);
            return;
        }
        if (image == null) {
            return;
        }
        int height = image.getHeight();
        int width = image.getWidth();
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int rgba = image.getRGB(x, y);
                if ((rgba >>> 24 & 0xFF) == 0) {
                    continue;
                }
                VoxelUVMap uv = VoxelUVMap.single_pixel(new Vector2i(x, y));
                result.add(ParticleVoxel.create(new Vector3(x, y, 0.0f), new Vector3(1.0f), uv));
            }
        }
        ParticleVoxel[] voxels = result.toArray(new ParticleVoxel[0]);
        String key = id.toString()
            .replace("biomeparticleweather:textures/particle_model/", "")
            .replace(".png", "");
        models.put(key, new ParticleModelPart(
            voxels,
            new ParticleVoxelParams(Vector3.ONE, Vector3.ZERO, VoxelTransform.def())
        ));
    }
}
