package com.dontouchat.sharkswithlasers.entity.client;

import com.dontouchat.sharkswithlasers.SharksWithLasers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation SHARK_LAYER = new ModelLayerLocation(
            new ResourceLocation(SharksWithLasers.MODID,"shark_layer"),"main"
    );
}
