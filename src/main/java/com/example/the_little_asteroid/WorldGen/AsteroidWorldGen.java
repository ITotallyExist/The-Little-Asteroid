package com.example.the_little_asteroid.WorldGen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class AsteroidWorldGen {
    public static void registerWorldGen(){
        //register the chunk generator/world type
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, "the_little_asteroid:asteroid", AsteroidChunkGenerator.CODEC);
    }
}
