# Java3D 1.7 Setup Guide for Java 21

## Overview

This guide documents the setup and configuration of Java3D 1.7 with JOGL 2.6 on Java 21. Java3D requires specific VM arguments to work with Java 16+ due to module access restrictions.

## Prerequisites

- Java Development Kit (JDK) 21
- Eclipse IDE (or similar)
- 7-Zip (for extracting .7z archives)

## Required Libraries

### Download Locations

1. **Java3D 1.7.1** (Latest version)
   - https://jogamp.org/deployment/java3d/1.7.1-build-20200222/jogamp-java3d.7z
   - Alternative: https://jogamp.org/deployment/java3d/1.7.0-final/jogamp-java3d.7z

2. **JogAmp Libraries** (JOGL + GlueGen)
   - https://jogamp.org/deployment/jogamp-current/archive/jogamp-all-platforms.7z

### JAR Files Needed

After extraction, you should have:

**Java3D JARs:**
- `java3d-core.jar` (or `j3dcore.jar`)
- `java3d-utils.jar` (or `j3dutils.jar`)
- `vecmath.jar`

**JOGL JARs:**
- `jogl-all.jar`
- `jogl-all-natives-windows-amd64.jar` (contains native DLLs)
- `gluegen-rt.jar`
- `gluegen-rt-natives-windows-amd64.jar` (contains native DLLs)

**Optional:**
- `com.stevesoft.pat.jar` (pattern matching utilities)

## Native Library Setup

### Step 1: Extract Native DLLs

1. Right-click `gluegen-rt-natives-windows-amd64.jar` in Eclipse
2. Extract all `.dll` files to a folder (e.g., `project/natives/`)
3. Repeat for `jogl-all-natives-windows-amd64.jar`

You should have DLLs like:
- `gluegen_rt.dll`
- `jogl_desktop.dll`
- `nativewindow_awt.dll`
- `nativewindow_win32.dll`

### Step 2: Configure Eclipse

1. **Right-click project** → **Run As** → **Run Configurations...**
2. Select your Java Application
3. Go to **Arguments** tab
4. Add VM arguments (see below)

## Critical VM Arguments

### Required for Java 16+

Java3D needs reflective access to internal JDK classes. Add these VM arguments:

```bash
--add-opens java.desktop/sun.awt=ALL-UNNAMED
--add-opens java.desktop/sun.java2d.opengl=ALL-UNNAMED
```

### Complete VM Arguments Example

```bash
-Djava.library.path=C:/path/to/your/project/natives
--add-opens java.desktop/sun.awt=ALL-UNNAMED
--add-opens java.desktop/sun.java2d.opengl=ALL-UNNAMED
```

**Replace** `C:/path/to/your/project/natives` with your actual path to the extracted DLL files.

## Package Name Changes

**Important:** Java3D 1.7 uses different package names than 1.6:

| Java3D 1.6 | Java3D 1.7 |
|------------|------------|
| `javax.media.j3d.*` | `org.jogamp.java3d.*` |
| `javax.vecmath.*` | `org.jogamp.vecmath.*` |
| `com.sun.j3d.utils.*` | `org.jogamp.java3d.utils.*` |

### Update Your Imports

**Old (1.6):**
```java
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
```

**New (1.7):**
```java
import org.jogamp.java3d.*;
import org.jogamp.vecmath.*;
import org.jogamp.java3d.utils.universe.*;
import org.jogamp.java3d.utils.geometry.*;
```

## Sample Working Code

```java
package test;

import org.jogamp.java3d.*;
import org.jogamp.vecmath.*;
import org.jogamp.java3d.utils.universe.*;
import org.jogamp.java3d.utils.geometry.*;

import com.jogamp.opengl.GLProfile;

import javax.swing.*;
import java.awt.*;

public class Java3DTest {

    public static void main(String[] args) {
        System.out.println("Java version: " + System.getProperty("java.version"));
        SwingUtilities.invokeLater(Java3DTest::start);
    }

    private static void start() {
        // Create and show frame FIRST
        JFrame frame = new JFrame("Java3D 1.7 Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        
        // Initialize OpenGL profile
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("j3d.rend", "jogl");
        
        try {
            GLProfile.initSingleton();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Get graphics configuration
        GraphicsConfiguration config = 
            SimpleUniverse.getPreferredConfiguration();
        
        if (config == null) {
            System.err.println("No usable GraphicsConfiguration found!");
            return;
        }
        
        // Create canvas and add to frame
        Canvas3D canvas = new Canvas3D(config);
        frame.add(canvas, BorderLayout.CENTER);
        frame.validate();
        
        // Create universe & scene
        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        
        BranchGroup scene = createSceneGraph();
        scene.compile();
        universe.addBranchGraph(scene);
    }

    private static BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        
        TransformGroup tg = new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        // Add a color cube
        tg.addChild(new ColorCube(0.3));
        root.addChild(tg);
        
        // Add lighting
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
        
        // Add rotation animation
        Alpha alpha = new Alpha(-1, 4000);
        RotationInterpolator rotator = 
            new RotationInterpolator(alpha, tg);
        rotator.setSchedulingBounds(bounds);
        tg.addChild(rotator);
        
        return root;
    }
}
```

## Troubleshooting

### Error: "AWTGraphicsConfiguration is null"

**Cause:** Missing VM arguments for Java 16+

**Solution:** Add the `--add-opens` VM arguments (see above)

### Error: "ArrayIndexOutOfBoundsException: Index -1"

**Cause:** Native libraries not found or incompatible JOGL version

**Solution:** 
- Verify native DLLs are extracted and path is correct
- Ensure JOGL 2.3+ is being used with Java3D 1.7

### Error: "No usable GraphicsConfiguration found"

**Cause:** Graphics drivers don't support OpenGL or timing issue

**Solution:**
- Update graphics drivers (NVIDIA/AMD/Intel)
- Ensure frame is created and visible before getting configuration
- Check OpenGL support with test program

### Test OpenGL Support

```java
import com.jogamp.opengl.*;

public class CheckOpenGL {
    public static void main(String[] args) {
        try {
            GLProfile.initSingleton();
            System.out.println("Available GL profiles:");
            for (String p : GLProfile.GL_PROFILE_LIST_ALL) {
                if (GLProfile.isAvailable(p)) {
                    System.out.println("  " + p);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
```

## IntelliJ IDEA Configuration

If using IntelliJ instead of Eclipse:

1. Open **Run/Debug Configurations**
2. Press **Alt+V** or **Modify Options → Add VM Options**
3. Add the VM arguments separated by spaces

## Notes

- Java3D 1.7 is maintained by the JogAmp community
- Works with Java 8 through Java 21+
- OpenGL 2.0+ required (check with `CheckOpenGL` test)
- For Java 11-15: Warnings about reflective access are expected but harmless
- For Java 16+: VM arguments are **mandatory**

## Version Compatibility

| Component | Version | Notes |
|-----------|---------|-------|
| Java3D | 1.7.0 or 1.7.1 | Use 1.7.1 for latest fixes |
| JOGL | 2.3.2+ | 2.6.0 recommended |
| Java | 8 - 21+ | Requires `--add-opens` for 16+ |
| OpenGL | 2.0+ | Check with graphics drivers |

## Additional Resources

- JogAmp Wiki: https://jogamp.org/wiki/index.php/Downloading_and_installing_Java3D
- Java3D Examples: https://github.com/philjord/java3d-examples
- JogAmp Forum: https://forum.jogamp.org/java3d-f3728156.html

## String Formatting in Java

Java equivalent to C's `sprintf()`:

```java
// Format to string
String result = String.format("Hello %s, you are %d years old", name, age);

// Print directly
System.out.printf("Value: %.2f%n", 3.14159);  // prints: Value: 3.14

// Common format specifiers
%s  - string
%d  - decimal integer
%f  - floating point
%x  - hexadecimal
%n  - platform-specific newline
```

---

*Last updated: November 2025*
