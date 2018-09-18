package cs6301.github.io.math;

/**
 * This class contains only log2ceiling function used in `cs6301.github.io.lock.TournamentPetersonLock.
 * The function is based on https://github.com/google/guava/blob/release19/guava/src/com/google/common/math/IntMath.java#L87
 */
public class IntegerMath {
    /**
     * Returns the ceiling of base-2 logarithm of {@code x}.
     *
     * @param x
     * @throws IllegalArgumentException if {@code x <= 0}
     */
    public static int log2ceiling(int x) {
        if (x <= 0) {
            throw new IllegalArgumentException(x + " must be > 0");
        }
        return Integer.SIZE - Integer.numberOfLeadingZeros(x - 1);
    }
}
