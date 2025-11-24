package tst;

import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

// ---------------------------------------------------------------------------

public class CheckLibraryVersions {

	static String libDir = "lib/";

	// -----------------------------------------------------------------------

	public static void chkGluegenVersion() {
		
        String gluegenPath = libDir + "gluegen-rt.jar";
        
        JarFile gluegenJar;
        
		try {
			gluegenJar = new JarFile(gluegenPath);
	        Manifest gluegenManifest = gluegenJar.getManifest();
	        System.out.println(">>>      Gluegen version: " +
	                gluegenManifest.getMainAttributes().getValue("Implementation-Version"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -----------------------------------------------------------------------

	public static void chkJoglVersion() {
        String joglPath = libDir + "jogl-all.jar";

        JarFile joglJar;
        
		try {
			joglJar = new JarFile(joglPath);
	        Manifest joglManifest = joglJar.getManifest();
	        System.out.println(">>>         JOGL version: " +
	                joglManifest.getMainAttributes().getValue("Implementation-Version"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -----------------------------------------------------------------------

	public static void chkJarVersion(String name) {
        String path = libDir + name;

        JarFile jar;
        
		try {
			jar = new JarFile(path);
	        Manifest manifest = jar.getManifest();
	        
	        String ver = manifest.getMainAttributes().getValue("Implementation-Version");
	        String s = String.format(">>>     %-40s  version: %s", name, ver);
	        
	        System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -----------------------------------------------------------------------
	
	public static void main(String[] args) {

        System.out.println("JAR file versions\n");
        
        chkJarVersion("gluegen-rt.jar");
        chkJarVersion("gluegen-rt-natives-windows-amd64.jar");
        chkJarVersion("jogl-all.jar");
        chkJarVersion("jogl-all-natives-windows-amd64.jar");

        chkJarVersion("java3d-core.jar");
        chkJarVersion("java3d-utils.jar");
        chkJarVersion("vecmath.jar");
	}

	// -----------------------------------------------------------------------
	
}

//---------------------------------------------------------------------------


