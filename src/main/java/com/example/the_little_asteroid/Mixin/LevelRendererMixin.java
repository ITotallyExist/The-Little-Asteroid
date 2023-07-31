package com.example.the_little_asteroid.Mixin;

import org.joml.Matrix4f;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.impl.modification.BuiltInRegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.example.the_little_asteroid.Asteroid;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.fabric.mixin.registry.sync.RegistriesAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

//this mixin removes clouds from rendering when the world type is "little_asteroid" (or when the biome is "space")

@ClientOnly
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    private ClientLevel level;
    
    private ResourceKey<Biome> spaceKey = ResourceKey.create(Registries.BIOME, new ResourceLocation("space"));
    private ResourceKey<Biome> spaceKey2 = ResourceKey.create(Registries.BIOME, new ResourceLocation(Asteroid.MOD_ID,"space"));
    private ResourceKey<Biome> spaceKey3 = ResourceKey.create(Registries.BIOME, new ResourceLocation("plains"));
    private ResourceKey<Biome> spaceKey4 = ResourceKey.create(Registries.BIOME, new ResourceLocation("biome.minecraft.plains"));
    private ResourceKey<Biome> spaceKey5 = ResourceKey.create(Registries.BIOME, new ResourceLocation("biome.the_little_asteroid.space"));

    //remove clouds from space
    @Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
    public void renderClouds(PoseStack matrices, Matrix4f projectionMatrix, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo info){
        BlockPos pos = new BlockPos((int) cameraX, (int) cameraY, (int) cameraZ);

        // System.out.println(((LevelRendererAccessor)((LevelRenderer)(Object)this)).getLevel().);
        try {
        // System.out.println(BuiltInRegistryKeys.isBuiltinBiome(spaceKey));
        // System.out.println(BuiltInRegistryKeys.isBuiltinBiome(spaceKey2));
        // System.out.println(BuiltInRegistryKeys.isBuiltinBiome(spaceKey3));
        // System.out.println(BuiltInRegistryKeys.isBuiltinBiome(spaceKey4));
        // System.out.println(BuiltInRegistryKeys.isBuiltinBiome(spaceKey5));
        BuiltInRegistryKeys.biomeRegistryWrapper().listElementIds().forEach(y -> System.out.println(y.location()));//.toArray();
            //after printing these out, i found that the biome that i created is just straight up fucking missing and now i want to cry
        } catch (Exception e){
            System.out.println(e);
        }
        
        try{
            
            if (((LevelRendererAccessor)((LevelRenderer)(Object)this)).getLevel().getBiome(pos).value() == BuiltInRegistryKeys.biomeRegistryWrapper().get(spaceKey).get().value()){
                System.out.println("1");
                info.cancel();
            }
            if (((LevelRendererAccessor)((LevelRenderer)(Object)this)).getLevel().getBiome(pos).value() == BuiltInRegistryKeys.biomeRegistryWrapper().get(spaceKey).get().value()){
                System.out.println("2");
                info.cancel();
            }
        }catch(Exception e){

        }

        
	}

    //add stars to space

    //remove moon from space

    //use this.createStars();
    //   this.createLightSky();
    //   this.createDarkSky();
}
