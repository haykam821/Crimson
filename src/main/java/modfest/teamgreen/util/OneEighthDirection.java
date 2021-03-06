package modfest.teamgreen.util;

import java.util.stream.Stream;

import net.minecraft.util.math.Vec3d;

public enum OneEighthDirection {
	NORTH(new Vec3d(0, 0, -1)),
	NORTHEAST(new Vec3d(0.7071, 0, -0.7071)),
	EAST(new Vec3d(1, 0, 0)),
	SOUTHEAST(new Vec3d(0.7071, 0, 0.7071)),
	SOUTH(new Vec3d(0, 0, 1)),
	SOUTHWEST(new Vec3d(-0.7071, 0, 0.7071)),
	WEST(new Vec3d(-1, 0, 0)),
	NORTHWEST(new Vec3d(-0.7071, 0, -0.7071));

	private OneEighthDirection(Vec3d vec) {
		this.vec = vec;
	}

	private final Vec3d vec;

	public static Stream<Vec3d> stream() {
		return Stream.of(
				NORTH.vec,
				NORTHEAST.vec,
				EAST.vec,
				SOUTHEAST.vec,
				SOUTH.vec,
				SOUTHWEST.vec,
				WEST.vec,
				NORTHWEST.vec
				);
	}
}
