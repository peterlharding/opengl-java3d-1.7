# opengl-java3d-1.7

Sandpit for demonstrating OpenGL/Java3d 1.7 configuration


# Confirguration

You will need to add these arguments to the VM:

```
-Djava.library.path=C:/src/eclipse-workspace/opengl-java3d-1.7/natives
--add-opens java.desktop/sun.awt=ALL-UNNAMED
--add-opens java.desktop/sun.java2d.opengl=ALL-UNNAMED
```

# Links

You might find these useful:

* 
## JOGL

* https://jogamp.org/
* https://jogamp.org/deployment/archive/rc/
* https://jogamp.org/deployment/jogamp-current/jar/ 2.6
* https://jogamp.org/deployment/archive/rc/v2.3.2/

## Java3D

* https://github.com/hharrison/java3d-core/releases/tag/1.6.2
* https://jogamp.org/deployment/java3d/1.7.1-final/
* https://jogamp.org/deployment/java3d/1.7.1-final/jogamp-java3d-1.7.1-final.zip
* https://jogamp.org/deployment/java3d/1.6.2/
* https://en.wikipedia.org/wiki/Java_3D


## Stale Links

* https://j3d.org/
* https://www.oracle.com/java/technologies/javase/java-3d.html
* https://github.com/Java-3D/Java3D
* https://www.javaspring.net/blog/java-java3d/
* https://www.instructables.com/Java-3D-how-to/
* https://reintech.io/blog/java-3d-programming-creating-simple-3d-applications--
* https://codingtechroom.com/tutorial/java-java-3d-game-development


# Programs

## CheckOpenGLVersions

Gives this output:

```
Available GL profiles:
  GL4bc
  GL3bc
  GL2
  GL4
  GL3
  GL4ES3
  GL2GL3
  GL2ES2
  GL2ES1
```

## CheckLibraryVersions

Gives this output:

```
JAR file versions

>>>     gluegen-rt.jar                            version: 2.6.0
>>>     gluegen-rt-natives-windows-amd64.jar      version: 2.6.0
>>>     jogl-all.jar                              version: 2.6.0
>>>     jogl-all-natives-windows-amd64.jar        version: 2.6.0
>>>     java3d-core.jar                           version: null
>>>     java3d-utils.jar                          version: null
>>>     vecmath.jar                               version: null
```

Note the three Java3D JAR files do not contain "Implementation-Version" in the Manifest.  Also I noticed that they were built with Java 1.8

```
Manifest-Version: 1.0
Archiver-Version: Plexus Archiver
Built-By: pjnz
Class-Path: vecmath-1.7.1.jar gluegen-rt-main-2.4.0.jar gluegen-rt-2.4
 .0.jar gluegen-rt-2.4.0-natives-android-aarch64.jar gluegen-rt-2.4.0-
 natives-linux-amd64.jar gluegen-rt-2.4.0-natives-linux-armv6hf.jar gl
 uegen-rt-2.4.0-natives-linux-aarch64.jar gluegen-rt-2.4.0-natives-mac
 osx-universal.jar gluegen-rt-2.4.0-natives-windows-amd64.jar antlr-2.
 7.7.jar jogl-all-main-2.4.0.jar jogl-all-2.4.0.jar jogl-all-2.4.0-nat
 ives-android-aarch64.jar jogl-all-2.4.0-natives-linux-amd64.jar jogl-
 all-2.4.0-natives-linux-armv6hf.jar jogl-all-2.4.0-natives-linux-aarc
 h64.jar jogl-all-2.4.0-natives-macosx-universal.jar jogl-all-2.4.0-na
 tives-windows-amd64.jar
Created-By: Apache Maven 3.9.1
Build-Jdk: 1.8.0_25
```
