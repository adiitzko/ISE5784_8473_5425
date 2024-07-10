/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;
import lighting.PointLight;
import geometries.*;
/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /**
    * Scene for the tests
    */
   private final Scene scene = new Scene("Test scene");
   /**
    * Camera builder for the tests with triangles
    */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
           .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
           .setRayTracer(new SimpleRayTracer(scene));
   // Configure and set the camera


   /**
    * Produce a picture of a sphere lighted by a spot light
    */
   @Test
   public void twoSpheres() {
      scene.geometries.add(
              new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                      .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
              new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add(
              new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                      .setKl(0.0004).setKq(0.0000006));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
              .setVpSize(150, 150)
              .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
              .build()
              .renderImage()
              .writeToImage();
   }

   /**
    * Produce a picture of a sphere lighted by a spot light
    */
   @Test
   public void twoSpheresOnMirrors() {
      scene.geometries.add(
              new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                              .setkT(new Double3(0.5, 0, 0))),
              new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                      .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
              new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                      new Point(670, 670, 3000))
                      .setEmission(new Color(20, 20, 20))
                      .setMaterial(new Material().setKr(1)),
              new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                      new Point(-1500, -1500, -2000))
                      .setEmission(new Color(20, 20, 20))
                      .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
              .setKl(0.00001).setKq(0.000005));

      cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
              .setVpSize(2500, 2500)
              .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
              .build()
              .renderImage()
              .writeToImage();
   }

   /**
    * Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow
    */
   @Test
   public void trianglesTransparentSphere() {
      scene.geometries.add(
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                      new Point(75, 75, -150))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
              new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
              new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
              new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                      .setKl(4E-5).setKq(2E-7));

      cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
              .setVpSize(200, 200)
              .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
              .build()
              .renderImage()
              .writeToImage();
   }

   /**
    * Produce a picture of several spheres lighted by 2 spot lights
    */
   @Test

   public void effectsTests() {
      ImageWriter imageWriter = new ImageWriter("GeometryCombination", 2000, 2000);

      scene.setAmbientLight(new AmbientLight(new Color(YELLOW), 0.45));

      Point a = new Point(0, 0, -100), b = new Point(100, 100, 0), c = new Point(-100, 100, 0),
              d = new Point(0, -100, 0);
      Material mirror = new Material().setKr(0.1).setKd(0.5).setKs(0.8).setShininess(3);

      scene.geometries.add(
              new Sphere(new Point(0, 0, 0), 70).setMaterial(new Material().setKd(0.4).setKs(0.001).setKt(0.2))
                      .setEmission(new Color(200, 200, 200)),
              new Triangle(a, b, c).setMaterial(mirror).setEmission(new Color(BLUE)),
              new Triangle(a, b, d).setMaterial(mirror).setEmission(new Color(RED)),
              new Triangle(a, c, d).setMaterial(mirror).setEmission(new Color(GREEN)));

      scene.lights.add(new PointLight(new Color(127, 80, 127), new Point(200, 200, 200))
              .setKl(0.0007).setKq(0.0000007));
      scene.lights.add(new SpotLight(new Color(200, 100, 50), new Point(100, 0, 0), new Vector(0, -0.15, -1))
              .setKl(0.006).setKq(0.00006));

      cameraBuilder.setLocation(new Point(0, 0, 300)) // Move the camera closer to the objects
              .setVpDistance(300) // Set the view plane distance accordingly
              .setVpSize(200, 200) // Adjust the view plane size
              .setImageWriter(imageWriter)
              .build()
              .renderImage()
              .writeToImage();
   }

   @Test
   public void complexSceneTest() {
      ImageWriter imageWriter = new ImageWriter("ComplexScene", 3000, 3000);



      // Add geometries to the scene
      scene.geometries.add(
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                      .setMaterial(new Material().setKs(0.8).setShininess(60)),
              new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                      .setMaterial(new Material().setKs(0.8).setShininess(60)),
              new Sphere(new Point(0, 0, -11), 30d)
                      .setEmission(new Color(BLUE))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
              new Plane(new Point(0, 0, -200), new Vector(0, 0, 1))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))
                      .setEmission(new Color(100, 200, 200)),
//              new Triangle(new Point(-20, 80, -90), new Point(80, 80, -90), new Point(30, -20, -90))
//                      .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(20))
//                      .setEmission(new Color(250, 100, 100)),
//              new Triangle(new Point(0, 0, -30), new Point(200, 0, 30), new Point(100, 200, -30))
//                      .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(25))
//                      .setEmission(new Color(100, 100, 250)),
//              new Cylinder(new Ray(new Point(50, 50, -120), new Vector(0, 0, 1)), 30, 100)
//                      .setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(35))
//                      .setEmission(new Color(150, 150, 50)),
              new Triangle(new Point(-120, -120, -115), new Point(-120, 120, -115), new Point(120, 120, -115))
                      .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(40))
                      .setEmission(new Color(50, 150, 150)),
              new Polygon(new Point(-70, -70, -100), new Point(70, -70, -100), new Point(70, 70, -100), new Point(-70, 70, -100))
                      .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(45))
                      .setEmission(new Color(180, 50, 180)),
              new Tube(new Ray(new Point(0, 100, 100), new Vector(1, 1, 0)), 20)
                      .setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(50))
                      .setEmission(new Color(120, 180, 80)),
              new Sphere(new Point(0, -50, -100), 50d)
                      .setEmission(new Color(GREEN))
                      .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(60)),
              new Sphere(new Point(-30, -50, -100), 30d)
                      .setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(90)),
              // Adding 2 more shapes for variety
              new Triangle(new Point(-50, 110, -100), new Point(110, 110, -100), new Point(30, -50, -100))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
                      .setEmission(new Color(200, 200, 0)),
              new Polygon(new Point(-60, -60, -90), new Point(60, -60, -90), new Point(60, 60, -90)
                      , new Point(-60, 60, -90))
                      .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(25))
                      .setEmission(new Color(50, 180, 50))
      );

// Setting ambient light and adding additional lights
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
              new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4))
                      .setKl(4E-4).setKq(2E-5)
      );


      // Set up the camera
      cameraBuilder.setLocation(new Point(0, 0, 1000))
              .setVpDistance(1000)
              .setVpSize(500, 500)
              .setImageWriter(imageWriter)
              .build()
              .renderImage()
              .writeToImage();
   }

   /////////////////////////////////////////////////////////////////////////////////////
   @Test
   public void complexSceneTest2() {
      ImageWriter imageWriter = new ImageWriter("ComplexScene2", 3000, 3000);

      // Add geometries to the scene
      scene.geometries.add(
              new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                      .setMaterial(new Material().setKs(0.8).setShininess(60)),
              new Triangle(new Point(-170, -150, -115), new Point(-200, 70, -140), new Point(65, 75, -150))
                      .setMaterial(new Material().setKs(0.8).setShininess(60)),
              new Sphere(new Point(0, 0, -11), 30d)
                      .setEmission(new Color(BLUE))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
              new Sphere(new Point(-60, -60, -11), 30d)
                      .setEmission(new Color(orange))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
              new Plane(new Point(0, 0, -200), new Vector(0, 0, 1))
                      .setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(10))
                      .setEmission(new Color(gray)),

//              new Polygon(new Point(-70, -70, -100), new Point(70, -70, -100), new Point(70, 70, -100), new Point(-70, 70, -100))
//                      .setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(45))
//                      .setEmission(new Color(180, 50, 180))
//              new Tube(new Ray(new Point(0, 100, 100), new Vector(1, 1, 0)), 20)
//                      .setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(50))
//                      .setEmission(new Color(YELLOW))
              new Sphere(new Point(20, -90, -100), 30d)
                      .setEmission(new Color(darkGray))
                      .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(60)),
              new Sphere(new Point(-140, 10, -100), 30d)
                      .setEmission(new Color(red))
                      .setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(90)),
//              // Adding 2 more shapes for variety
//              new Triangle(new Point(-50, 110, -100), new Point(110, 110, -100), new Point(30, -50, -100))
//                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
//                      .setEmission(new Color(200, 200, 0)),
              new Polygon(new Point(-200, 90, -90),  // הגדלתי את ה-y ב-40 והרחבתי את ה-x ב-80 לצד שמאל
                      new Point(100, 90, -90),   // הגדלתי את ה-y ב-40 והרחבתי את ה-x ב-110 לצד שמאל
                      new Point(100, 210, -90),  // הגדלתי את ה-y ב-40 והרחבתי את ה-x ב-110 לצד שמאל
                      new Point(-200, 210, -90)   )
                      .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(25))
                      .setEmission(new Color(50, 180, 50)),
              new Polygon( new Point(-170, 120, -120),  // הזזתי את ה-x ב-50 שמאלה
                      new Point(-170, 120, -20),   // הזזתי את ה-x ב-50 שמאלה
                      new Point(-170, 180, -20),   // הזזתי את ה-x ב-50 שמאלה
                      new Point(-170, 180, -120))
                      .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(25))
                      .setEmission(new Color(BLUE)),
              new Polygon(
                      new Point(170, -50, -50),    // נקודה ימנית למעלה של המצולע המעודכן
                      new Point(170, 10, -50),     // נקודה מעל הנקודה הימנית
                      new Point(170, 10, -140),    // נקודה מעל לנקודה השמאלית
                      new Point(170, -50, -140))  // נקודה שמאלית למטה של המצולע המקורי
                      .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(25))
                      .setEmission(new Color(BLUE))

              );

// Setting ambient light and adding additional lights
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
              new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4))
                      .setKl(4E-4).setKq(2E-5)
      );


      // Set up the camera
      cameraBuilder.setLocation(new Point(0, 0, 1000))
              .setVpDistance(1000)
              .setVpSize(500, 500)
              .setImageWriter(imageWriter)
              .build()
              .renderImage()
              .writeToImage();
   }



}
