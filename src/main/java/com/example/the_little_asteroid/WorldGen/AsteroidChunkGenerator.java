//parts of the code for this chunk generator were taken from Vazkii's Botania mod's SkyblockChunkGenerator class
// https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/common/world/SkyblockChunkGenerator.java

package com.example.the_little_asteroid.WorldGen;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DensityFunction.SinglePointContext;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public class AsteroidChunkGenerator extends ChunkGenerator {
    //copied from vanilla class NoiseBasedChunkGenerator
    public static final Codec<AsteroidChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> {
            return generator.getBiomeSource();
        }), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((generator) -> {
            return generator.settings;
        })).apply(instance, instance.stable(AsteroidChunkGenerator::new));
    });
    //copied from vanilla class NoiseBasedChunkGenerator
    private final Holder<NoiseGeneratorSettings> settings;

    public AsteroidChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> holder) {
        super(biomeSource);

        this.settings = holder;
    }

    @Override //copied from vanilla class NoiseBasedChunkGenerator
    public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos pos) {
      DecimalFormat decimalFormat = new DecimalFormat("0.000");
      NoiseRouter noiseRouter = randomState.router();
      SinglePointContext singlePointContext = new SinglePointContext(pos.getX(), pos.getY(), pos.getZ());
      double d = noiseRouter.ridges().compute(singlePointContext);
      String var10001 = decimalFormat.format(noiseRouter.temperature().compute(singlePointContext));
      list.add("NoiseRouter T: " + var10001 + " V: " + decimalFormat.format(noiseRouter.vegetation().compute(singlePointContext)) + " C: " + decimalFormat.format(noiseRouter.continents().compute(singlePointContext)) + " E: " + decimalFormat.format(noiseRouter.erosion().compute(singlePointContext)) + " D: " + decimalFormat.format(noiseRouter.depth().compute(singlePointContext)) + " W: " + decimalFormat.format(d) + " PV: " + decimalFormat.format((double)NoiseRouterData.peaksAndValleys((float)d)) + " AS: " + decimalFormat.format(noiseRouter.initialDensityWithoutJaggedness().compute(singlePointContext)) + " N: " + decimalFormat.format(noiseRouter.finalDensity().compute(singlePointContext)));
   }

    @Override
    public void applyCarvers(WorldGenRegion chunkRegion, long seed, RandomState randomState, BiomeManager biomeAccess,
            StructureManager structureManager, ChunkAccess chunk, Carving generationStep) {
        //i think this controls caves?
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureManager, RandomState randomState,
            ChunkAccess chunk) {
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
		return CompletableFuture.completedFuture(chunk);
	}

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
        return new NoiseColumn(levelHeightAccessor.getMinBuildHeight(), new BlockState[0]);
    }

    @Override
    public int getBaseHeight(int x, int z, Types heightmap, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
		return levelHeightAccessor.getMinBuildHeight();
    }

    @Override
    //world height from bottom to top i think?
    public int getGenDepth() {
        return ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().height();
        //return 640; //320 in either direction
    }

    @Override
    public int getMinY() { //maybe find a way to use this to make world go from -320 to +320?
        return ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().minY();
    }

    @Override
    public int getSeaLevel() {
         return 0;
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        //would be fun to get it to spawn a dog that starts with you
    }

    
}
