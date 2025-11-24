package tst;

import org.jogamp.java3d.*;
import org.jogamp.vecmath.*;
import org.jogamp.java3d.utils.universe.*;
import org.jogamp.java3d.utils.geometry.*;
// import org.jogamp.java3d.utils.behaviors.mouse.*;

import com.jogamp.opengl.GLProfile;
import org.jogamp.vecmath.*;

import javax.swing.*;
import java.awt.*;

// ---------------------------------------------------------------------------

public class Java3DTest {

	// -----------------------------------------------------------------------

    public static void main(String[] args) {
    	
    	System.out.println(" Java version: " + System.getProperty("java.version"));
    	System.out.println(" Library path: " + System.getProperty("java.library.path"));
    	
    	System.out.println("Canvas3D path: " + org.jogamp.java3d.Canvas3D.class.getProtectionDomain()
    	        .getCodeSource().getLocation());
    	
    	System.out.println("");
    	
        SwingUtilities.invokeLater(Java3DTest::start);
    }

    // -----------------------------------------------------------------------

    private static void start() {
   
        JFrame frame = new JFrame("Java3D 1.7.1 Test");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Ensure AWT is realized before creating Canvas3D
        frame.setVisible(true);
        
        
        // Force a specific OpenGL profile that Java3D likes
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("j3d.rend", "jogl");
        
        // Try specifying a profile explicitly
        try {
            GLProfile.initSingleton();
            System.setProperty("jogl.gljpanel.nohw", "false");
        } catch (Exception e) {
        	System.err.println("GLProfile.initSingleton() failed...");
            e.printStackTrace();
        }
        
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();

        if (config == null) {
        	System.err.println("Check: OpenGL drivers, native libraries");
            throw new RuntimeException("No usable GraphicsConfiguration found!");
        }

        Canvas3D canvas = new Canvas3D(config);
        


        frame.add(canvas, BorderLayout.CENTER);

        // Create universe & scene
        SimpleUniverse universe = new SimpleUniverse(canvas);
        
        universe.getViewingPlatform().setNominalViewingTransform();

        BranchGroup scene = createSceneGraph();
        
        scene.compile();

        universe.addBranchGraph(scene);
    }

    // -----------------------------------------------------------------------

    /** Simple spinning cube */
    private static BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();

        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Add a color cube
        tg.addChild(new ColorCube(0.3));
        root.addChild(tg);

        // Add simple light
        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        AmbientLight amb = new AmbientLight(new Color3f(0.5f, 0.5f, 0.5f));
        amb.setInfluencingBounds(bounds);
        root.addChild(amb);

        DirectionalLight dir =
                new DirectionalLight(new Color3f(1f, 1f, 1f),
                        new Vector3f(-0.3f, -0.3f, -0.3f));
        dir.setInfluencingBounds(bounds);
        root.addChild(dir);

        // Add rotation
        Alpha alpha = new Alpha(-1, 4000);
        RotationInterpolator rotator =
                new RotationInterpolator(alpha, tg);
        rotator.setSchedulingBounds(bounds);
        tg.addChild(rotator);

        return root;
    }
    
 // -----------------------------------------------------------------------

}

//---------------------------------------------------------------------------
