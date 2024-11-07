package nordeus.challange.models;

import java.util.Random;

public class PerlinNoise {
    private final int[] permutation;

    public PerlinNoise(long seed) {
        permutation = new int[512];
        int[] p = new int[256];

        // Initialize the permutation array with values 0-255
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Shuffle using the seed for reproducibility
        Random random = new Random(seed);
        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Swap
            int temp = p[i];
            p[i] = p[index];
            p[index] = temp;
        }

        // Duplicate the permutation array
        System.arraycopy(p, 0, permutation, 0, 256);
        System.arraycopy(p, 0, permutation, 256, 256);
    }

    // Fade function as defined by Ken Perlin
    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation function
    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Gradient function
    private double grad(int hash, double x, double y) {
        int h = hash & 7;      // Convert low 3 bits of hash code
        double u = h < 4 ? x : y; // into 8 simple gradient directions
        double v = h < 4 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    // 2D Perlin Noise
    public double noise(double x, double y) {
        // Find unit grid cell containing point
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;

        // Relative x, y of point in cell
        x -= Math.floor(x);
        y -= Math.floor(y);

        // Compute fade curves for x, y
        double u = fade(x);
        double v = fade(y);

        // Hash coordinates of the square corners
        int aa = permutation[X] + Y;
        int ab = permutation[X] + Y + 1;
        int ba = permutation[X + 1] + Y;
        int bb = permutation[X + 1] + Y + 1;

        // Add blended results from the corners
        double result = lerp(v,
                lerp(u, grad(permutation[aa], x, y),
                        grad(permutation[ba], x - 1, y)),
                lerp(u, grad(permutation[ab], x, y - 1),
                        grad(permutation[bb], x - 1, y - 1))
        );

        // Normalize the result to [0, 1]
        return (result + 1) / 2;
    }
}
