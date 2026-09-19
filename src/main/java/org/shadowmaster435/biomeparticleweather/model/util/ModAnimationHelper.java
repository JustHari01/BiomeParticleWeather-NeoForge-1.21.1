/*
 * Decompiled with CFR 0.152.
 */
package org.shadowmaster435.biomeparticleweather.model.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;
import org.shadowmaster435.biomeparticleweather.model.util.SinglePartAnimatedModel;

public class ModAnimationHelper {
    public ModAnimationHelper() {
    }

    public static void animate(SinglePartAnimatedModel<?> model, AnimationDefinition animation, long runningTime, float scale, Vector3f tempVec) {
        float f = ModAnimationHelper.getRunningSeconds(animation, runningTime);
        for (Map.Entry<String, List<AnimationChannel>> entry : animation.boneAnimations().entrySet()) {
            Optional<ModelPart> optional = model.getChild(entry.getKey());
            List<AnimationChannel> list = entry.getValue();
            optional.ifPresent(part -> list.forEach(transformation -> {
                Keyframe[] keyframes = transformation.keyframes();
                int i = Math.max(0, Mth.binarySearch((int)0, (int)keyframes.length, index -> f <= keyframes[index].timestamp()) - 1);
                int j = Math.min(keyframes.length - 1, i + 1);
                Keyframe keyframe = keyframes[i];
                Keyframe keyframe2 = keyframes[j];
                float h = f - keyframe.timestamp();
                float k = j != i ? Mth.clamp((float)(h / (keyframe2.timestamp() - keyframe.timestamp())), (float)0.0f, (float)1.0f) : 0.0f;
                part.resetPose();
                keyframe2.interpolation().apply(tempVec, k, keyframes, i, j, scale);
                transformation.target().apply(part, tempVec);
            }));
        }
    }

    private static float getRunningSeconds(AnimationDefinition animation, long runningTime) {
        float f = (float)runningTime / 1000.0f;
        return animation.looping() ? f % animation.lengthInSeconds() : f;
    }
}
