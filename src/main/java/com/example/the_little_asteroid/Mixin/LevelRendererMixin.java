package com.example.the_little_asteroid.Mixin;

import org.joml.Matrix4f;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

//this mixin removes clouds from rendering when the world type is "little_asteroid" (or when the biome is "space")

@ClientOnly
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    private ClientLevel level;

    //remove clouds from space
    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    public void renderClouds(PoseStack matrices, Matrix4f projectionMatrix, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo info){
        BlockPos pos = new BlockPos((int) cameraX, (int) cameraY, (int) cameraZ);
        if (((LevelRenderer)(Object)this).level.getBiome(pos) == biome){

            info.cancel();
        }
	}

    //add stars to space

    //remove moon from space

    //use this.createStars();
    //   this.createLightSky();
    //   this.createDarkSky();
}
