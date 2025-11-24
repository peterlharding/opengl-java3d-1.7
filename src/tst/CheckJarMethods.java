package tst;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class CheckJarMethods {

	static String libDir = "lib/";

	// -----------------------------------------------------------------------

	public static void listJarMethods(String jarPath) throws Exception {

	    File file = new File(jarPath);
	    URL url = file.toURI().toURL();
	    URLClassLoader cl = new URLClassLoader(new URL[]{url});

	    try (JarInputStream jar = new JarInputStream(new FileInputStream(file))) {
	        JarEntry entry;

	        while ((entry = jar.getNextJarEntry()) != null) {

	            String name = entry.getName();

	            if (!name.endsWith(".class")) continue;

	            // Convert path to class name
	            String className = name
	                    .replace('/', '.')
	                    .replace(".class", "");

	            try {
	                Class<?> cls = cl.loadClass(className);
	                System.out.println("\nClass: " + className);

	                for (Method m : cls.getDeclaredMethods()) {
	                    System.out.println("  " + m);
	                }

	            } catch (Throwable t) {
	                // Ignore classes that fail to load
	            }
	        }
	    }
	    
	    cl.close();
	}

	// -----------------------------------------------------------------------

	public static void listClassesInJar(String jarPath) throws Exception {
	    try (JarFile jar = new JarFile(jarPath)) {
	        jar.stream()
	            .filter(e -> e.getName().endsWith(".class"))
	            .forEach(e -> {
	                String className = e.getName()
	                        .replace('/', '.')
	                        .replace(".class", "");
	                System.out.println(className);
	            });
	    }
	}

	// -----------------------------------------------------------------------

	public static void listJarClassMethods(String name) {
		String path = libDir + name;
		
        try {
			listJarMethods(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -----------------------------------------------------------------------

	public static void listJarClasses(String name) {
		String path = libDir + name;
		
      try {
			listClassesInJar(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// -----------------------------------------------------------------------

	public static void main(String[] args) {
        System.out.println("CheckJarMethods\n\n");
        
        listJarClasses("java3d-core.jar");
        
        // listJarClasses("jogl-all.jar");
        // listJarClassMethods("jogl-all.jar");
	}

	// -----------------------------------------------------------------------

}
