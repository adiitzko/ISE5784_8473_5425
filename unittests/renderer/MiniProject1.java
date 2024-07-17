/**
 *
 */
package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import lighting.PointLight;
import primitives.*;
import scene.Scene;
import geometries.*;
import lighting.DirectionalLight;
/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class MiniProject1 {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */


    @Test
    public void picture() {
        Scene scene = new Scene("Test scene")//
                .setBackground(new Color(51, 153, 255));


        final Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(new Vector(0, 0, -1), new Vector(1, 0, 0))
                .setRayTracer(new SimpleRayTracer(scene));


        Point S1 = new Point(-165, 170, -500);//D
        Point S2 = new Point(-165, -150, -500);
        Point be1 = new Point(150, 170, -500);//H
        Point be2 = new Point(150, -150, -500);
        Point light = new Point(300, -70, 0);
        Point h1 = new Point(-165, 80, -220);
        Point h2 = new Point(-165, -20, -150);
        Point h3 = new Point(-50, 80, -220);
        Point h4 = new Point(-50, -20, -150);
        Point h5 = new Point(-165, -100, -270);
        Point h6 = new Point(-50, -100, -270);
        Point h7 = new Point(-165, 0, -340);
        Point h8 = new Point(-50, 0, -340);
        Point h9 = new Point(0, 30, -185);
        Point h10 = new Point(0, -50, -305);
        Point h24 = new Point(-90, -20, -150);
        Point h56 = new Point(-90, -100, -270);
        Point d1 = new Point(-165, 55, -202);
        Point d2 = new Point(-165, 5, -167.5);
        Point d5 = new Point(-50, 5, -167.5);
        Point d6 = new Point(-50, 55, -202);
        Point h13 = new Point(-90, 80, -220);
        Point c = new Point(-50, -36, -174);
        Point d = new Point(-50, -68, -222);
        Point e = new Point(-165, -36, -174);
        Point f = new Point(-165, -68, -222);
        Point g = new Point(-60, -20, -150);
        Point h = new Point(-60, -100, -270);
        Point A1 = new Point(-50, 55, -202.5);
        Point A2 = new Point(-50, 60, -250);
        Point A3 = new Point(-50, 35, -232.5);
        Point A4 = new Point(30, 55, -202.5);
        Point A5 = new Point(30, 35, -232.5);
        Point A6 = new Point(30, 60, -250);
        Point A7 = new Point(30, 80, -220);
        Point w1 = new Point(-60, -36, -174);
        Point w2 = new Point(-60, -68, -222);
        Point w3 = new Point(-100, -36, -174);
        Point w4 = new Point(-100, -68, -222);

        scene.geometries.add(
                new Sphere(new Point(100, -100, -70), 20)
                        .setEmission(new Color(200, 100, 0)) // צבע השמש
                        .setMaterial(new Material().setKd(0.3).setKs(0.4).setKt(0.5).setShininess(20)),
//                new Polygon(S1, S2, S3, S4).setEmission(new Color(GREEN)) //surface
//                        .setMaterial(new Material().setKd(0.5).setKs(0.8).setKr(1).setShininess(20)),
                new Polygon(S2, S1, be1, be2) //background
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),

//                //hose
               new Polygon(h1, h3, h8, h7).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKt(0).setKr(0).setShininess(20)),
               new Polygon(h5, h6, h8, h7).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKt(0).setKr(0).setShininess(20)),
                new Polygon(h6, h4, h3, h8).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0).setKs(0).setKt(0).setKr(0).setShininess(0)),
                //roof
                new Triangle(h4, h9, h3).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Triangle(h6, h10, h8).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h4, h9, h10, h6).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
               new Polygon(h10, h9, h3, h8).setEmission(new Color(RED)) //
                       .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h2, h4, d5, d2).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h1, h3, d6, d1).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h4, h24, h13, h3).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h2, h4, c, e).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(f, d, h6, h5).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h2, h24, h56, h5).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(g, h4, h6, h).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(A1, A3, A5, A4).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(A1, A4, A7, h3).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(h3, A7, A6, A2).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(A2, A6, A5, A3).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Polygon(A4, A5, A6, A7).setEmission(new Color(GRAY)) //
                        .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                new Sphere( new Point(50, 57.5, -226.25),12.5).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setKd(0.05).setKs(0).setKr(0).setKt(0.7).setShininess(20)),
                new Sphere( new Point(80, 57.5, -226.25),12.5).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setKd(0).setKs(0).setKr(0).setKt(0.96).setShininess(20)),
                new Sphere( new Point(110, 57.5, -226.25),12.5).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setKd(0).setKs(0).setKr(0).setKt(0.98).setShininess(20)),
                new Polygon(w1, w2, w4, w3).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setKd(0).setKs(0).setKr(0).setKt(0.96).setShininess(20)));

        scene.setAmbientLight(new AmbientLight(new Color(200, 200, 255),0.1)); // צבע כהה יותר ליצירת תאורה רכה

        scene.lights.add(
                new PointLight(new Color(200, 150, 0), new Point(100, -100, -70))
                        .setKl(0).setKq(0.1));
        scene.lights.add(
                new DirectionalLight(new Color(100, 100, 25), new Vector(1, 1, 0)));
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), light, new Vector(-280, 65, -300)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(-135, 0, -260), new Vector(-55, 50, 10)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(0, 200, 0), new Vector(-135, -155, -160)) //
                        .setKl(0.05).setKq(1.5E-7));

        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(0, -200, 0), new Vector(-135, 200, -240)) //
                        .setKl(0.09).setKq(1.5E-7));
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(0, -300, 0), new Vector(-135, 300, -240)) //
                        .setKl(0.09).setKq(1.5E-7));



        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("afterAntiAliasing", 500, 500))
                .build()
                .setRaynum(80)
                .renderImage()
                .writeToImage();

        cameraBuilder.setLocation(new Point(0, 0, 800))
                .setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("beforeAntiAliasing", 500, 500))
                .build()
                .setRaynum(1)
                .renderImage()
                .writeToImage();
    }

}
