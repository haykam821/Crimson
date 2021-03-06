package modfest.teamgreen.logic;

import java.util.Random;
import java.util.function.Consumer;

import modfest.teamgreen.CrimsonInit;
import modfest.teamgreen.world.CrimsonWorld;
import modfest.teamgreen.world.noise.OctaveOpenSimplexNoise;
import modfest.teamgreen.world.util.Int2ToDoubleCache;
import net.minecraft.world.biome.Biome;

public class CrimsonBiomeLogic {
	public CrimsonBiomeLogic(Random rand) {
		this.alterationNoise = new OctaveOpenSimplexNoise(rand, 1, CrimsonInit.CONFIG.crimsonNoisePeriod);

		final double crimsonFadeRadius = CrimsonInit.CONFIG.crimsonFadeRadius;
		final double target = crimsonFadeRadius * crimsonFadeRadius;

		this.thresholdCache = new Int2ToDoubleCache((x, z) -> {
			double xd = (double) x; // m i c r o o p t i m i s a t i o n
			double zd = (double) z;

			double sqrDist = xd * xd + zd * zd;
			double sample = this.alterationNoise.sample(xd, zd);

			if (sqrDist < target) {
				sample -= 1.0 - (sqrDist / target);
			}

			return sample;
		});

		this.crimsonNoiseThreshold = CrimsonInit.CONFIG.crimsonNoiseCutoff;
	}

	private OctaveOpenSimplexNoise alterationNoise;
	private Int2ToDoubleCache thresholdCache;
	private final double crimsonNoiseThreshold;

	public void apply(Biome result, int genX, int genZ, Consumer<Biome> setReturnValue) {
		double threshold = this.thresholdCache.apply(genX, genZ);

		if (threshold > this.crimsonNoiseThreshold) {
			switch (result.getCategory()) {
			case MUSHROOM:
			case OCEAN:
				break;
			case RIVER:
				setReturnValue.accept(CrimsonWorld.CRIMSON_RIVER);
				break;
			case TAIGA:
			case JUNGLE:
			case FOREST:
				setReturnValue.accept(CrimsonWorld.CRIMSON_FOREST);
				break;
			case EXTREME_HILLS:
			case SWAMP:
				setReturnValue.accept(CrimsonWorld.CRIMSON_MARSHLAND);
				break;
			default:
				setReturnValue.accept(CrimsonWorld.CRIMSON_BRUSHLAND);
				break;
			}
		}
	}
}
