/**
 *
 */
package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;
import geometries.*;
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
    private final Camera.Builder cameraB = Camera.getBuilder()
            .setDirection(new Vector(-6, 0, -1.5).normalize(), new Vector(-1.5, 0, 6).normalize())
            .setRayTracer(new SimpleRayTracer(scene));
    // Configure and set the camera

    @Test
    public void antialiasing() {
        Scene scene = new Scene("trying").setBackground(new Color(0, 192, 203));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        // Define points for geometries
        Point a = new Point(100, 100, 0);
        Point b = new Point(100, -100, 0);
        Point c = new Point(-100, -100, 0);
        Point d = new Point(-100, 100, 0);
        Point sphere1 = new Point(0, -50, 50);
        Point sphere2 = new Point(0, 50, 50);
        Point light1 = new Point(200, 16, 100);
        Point light2 = new Point(200, 16, 100);

        // Add geometries to the scene
        scene.geometries.add(
                new Polygon(a, b, c, d)
                        .setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKs(0.5).setKd(0.5).setShininess(60)),

                new Sphere(sphere1, 90)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Sphere(sphere2, 30)
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
        );

        // Add lights to the scene
        scene.lights.add(
                new SpotLight(new Color(WHITE), light1, new Vector(-170, -20, -160))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE), light2, new Vector(-170, 20, -160))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE), new Point(-180, -40, 255), new Vector(156, -10, -215))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE), light2, new Vector(156, 10, -215))
                        .setKl(4E-4).setKq(2E-5));


        cameraB.setLocation(new Point(600, 0, 200)) // Move the camera closer to the objects
                .setVpDistance(700) // Set the view plane distance accordingly
                .setVpSize(300, 300) // Adjust the view plane size
                .setImageWriter(new ImageWriter("aliasing", 600, 600))
                .build()
                .setRaynum(200)
                .renderImage()
                .writeToImage();
    }

    @Test
    public void notAntialiasing() {
        Scene scene = new Scene("trying").setBackground(new Color(0, 192, 203));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        // Define points for geometries
        Point a = new Point(100, 100, 0);
        Point b = new Point(100, -100, 0);
        Point c = new Point(-100, -100, 0);
        Point d = new Point(-100, 100, 0);
        Point sphere1 = new Point(0, -50, 50);
        Point sphere2 = new Point(0, 50, 50);
        Point light1 = new Point(200, 16, 100);
        Point light2 = new Point(200, 16, 100);

        // Add geometries to the scene
        scene.geometries.add(
                new Polygon(a, b, c, d)
                        .setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKs(0.5).setKd(0.5).setShininess(60)),

                new Sphere(sphere1, 30)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Sphere(sphere2, 30)
                        .setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
        );

        // Add lights to the scene
        scene.lights.add(
                new SpotLight(new Color(WHITE), light1, new Vector(-170, -20, -160))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE), light2, new Vector(-170, 20, -160))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE), new Point(-180, -40, 255), new Vector(156, -10, -215))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE), light2, new Vector(156, 10, -215))
                        .setKl(4E-4).setKq(2E-5));


        cameraB.setLocation(new Point(600, 0, 200)) // Move the camera closer to the objects
                .setVpDistance(700) // Set the view plane distance accordingly
                .setVpSize(300, 300) // Adjust the view plane size
                .setImageWriter(new ImageWriter("notAntialiasing", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }


        @Test
        public void picture() {
            Scene scene = new Scene("Test scene")//
                    .setBackground(new Color(51, 153, 255));

//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(1, 0, 0)) //
//                .setVPDistance(600).setVPSize(250, 250); //
            final Camera.Builder cameraBuilder = Camera.getBuilder()
                    .setDirection(new Vector(0, 0, -1), new Vector(1, 0, 0))
                    .setRayTracer(new SimpleRayTracer(scene));


            Point S1 = new Point(-165, 170, -500);//D
            Point S2 = new Point(-165, -150, -500);
            Point S3 = new Point(-165, -150, -100);
            Point S4 = new Point(-165, 170, -100);//G
            Point SP1 = new Point(-145, 135, -120);
            Point SP2 = new Point(-145, 120, -180);
            Point SP3 = new Point(-145, 105, -240);
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
            Point d3 = new Point(-95, 55, -202);
            Point d4 = new Point(-95, 5, -167.5);
            Point d5 = new Point(-50, 5, -167.5);
            Point d6 = new Point(-50, 55, -202);
            Point h13 = new Point(-90, 80, -220);
            Point c = new Point(-50, -36, -174);
            Point d = new Point(-50, -68, -222);
            Point e = new Point(-165, -36, -174);
            Point f = new Point(-165, -68, -222);
            Point g = new Point(-60, -20, -150);
            Point h = new Point(-60, -100, -270);
            Point l1 = new Point(-80, -50, -210);
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
                    new Polygon(S1, S2, S3, S4).setEmission(new Color(GREEN)) //surface
                            .setMaterial(new Material().setKd(0.5).setKs(0.8).setKr(1).setShininess(20)),
                    new Polygon(S2, S1, be1, be2) //background
                            .setEmission(new Color(20, 20, 20)) //
                            .setMaterial(new Material().setKr(1)),
                    //garden
                    new Sphere(SP1,20).setEmission(new Color(BLUE))
                            .setMaterial(new Material().setKd(0.5).setKs(0.4).setShininess(20)),
                    new Sphere((SP2),20).setEmission(new Color(RED))
                            .setMaterial(new Material().setKd(0.5).setKs(0.4).setKt(0.3).setShininess(20)),
                    new Sphere(SP3,20).setEmission(new Color(51, 153, 255))
                            .setMaterial(new Material().setKd(0.5).setKs(0.4).setShininess(20)),
                    //hose
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
                    new Sphere( new Point(-115, 45, -150),15)//head
                            .setEmission(new Color(51, 0, 0)) //
                            .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                    new Sphere( new Point(-103, 40, -98),2)//eyeR
                            .setEmission(new Color(BLUE))
                            .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                    new Sphere( new Point(-105, 50, -125),2)//eyeL
                            .setEmission(new Color(BLUE))
                            .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                    new Sphere( new Point(-110, 45, -100),5)//nose
                            .setEmission(new Color(153, 102, 0)) //
                            .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                    new Sphere( new Point(-145, 45, -150),20)//body
                            .setEmission(new Color(51, 0, 0)) //
                            .setMaterial(new Material().setKd(0.95).setKs(0).setKr(0).setKt(0).setShininess(20)),
                    new Sphere( l1,5).setEmission(new Color(YELLOW))//hose light
                            .setMaterial(new Material().setKd(0.5).setKs(0.4).setKt(0.1).setKr(0.2).setShininess(20)),
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
                            .setMaterial(new Material().setKd(0.05).setKs(0).setKr(0).setKt(0).setShininess(20)),
                    new Sphere( new Point(80, 57.5, -226.25),12.5).setEmission(new Color(BLACK)) //
                            .setMaterial(new Material().setKd(0).setKs(0).setKr(0).setKt(0.96).setShininess(20)),
                    new Sphere( new Point(110, 57.5, -226.25),12.5).setEmission(new Color(BLACK)) //
                            .setMaterial(new Material().setKd(0).setKs(0).setKr(0).setKt(0.98).setShininess(20)),
                    new Polygon(w1, w2, w4, w3).setEmission(new Color(BLACK)) //
                            .setMaterial(new Material().setKd(0).setKs(0).setKr(0).setKt(0.96).setShininess(20)));


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

            ImageWriter imagewriter = new ImageWriter("picture", 1000, 1000);

//        cameraBuilder.setImageWriter(imagewriter)
//                .setRayTracer(new SimpleRayTracer(scene))
//                //.setNumberOfRays(9)
//                //.setAdaptive(true)
//                //.setThreadsCount(3)
//
//                .renderImage()
//                .writeToImage();

            cameraBuilder.setLocation(new Point(0, 0, 1000))
                    .setVpDistance(1000)
                    .setVpSize(150, 150)
                    .setImageWriter(new ImageWriter("aaa", 500, 500))
                    .build()
                    .setRaynum(80)
                    .renderImage()
                    .writeToImage();


    }
}
