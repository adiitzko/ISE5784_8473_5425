package renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.ColorModel;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.ImageWriter;
/**
 * Testing Camera Class
 */
class ImageWriterTest {
    /**
     * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
     */

    @Test
    void makeImage() {
        final int nx=800;
        final int ny=500;
        ImageWriter imageWriter = new ImageWriter("TestPic", nx, ny);
        Color background = new Color(41, 227, 128);
        Color grid = new Color(130, 10, 245);
        // Separately colors each pixel based on the ray tracer's findings
        for (int i = 0; i < ny; ++i) {
            for (int j = 0; j < nx; ++j) {
                if (i % (ny/10) == 0 || j % (nx/16) == 0)
                    imageWriter.writePixel(j, i, grid);
                else
                    imageWriter.writePixel(j, i, background);
            }
        }
        imageWriter.writeToImage();
    }
}