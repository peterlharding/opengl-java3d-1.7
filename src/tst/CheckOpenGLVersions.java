package tst;

import com.jogamp.opengl.*;

// ---------------------------------------------------------------------------

public class CheckOpenGLVersions {

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

//---------------------------------------------------------------------------
