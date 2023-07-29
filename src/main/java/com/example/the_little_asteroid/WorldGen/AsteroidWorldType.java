package com.example.the_little_asteroid.WorldGen;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class AsteroidWorldType extends WorldType {

    public AsteroidWorldType() {
        super("asteroid_world");
    }

    @Override
    public BiomeProvider getBiomeProvider(net.minecraft.world.IWorld world) {
        return new CustomBiomeProvider(world.getSeed(), world.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public IChunkGenerator createChunkGenerator(net.minecraft.world.IWorld world) {
        return new CustomChunkGenerator(world, new CustomBiomeProvider(world.getSeed(), world.getWorldInfo().getGeneratorOptions()));
    }
}