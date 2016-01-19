package net.imglib2.paramaxflow;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.indago.tr2d.ui.view.Tr2dPanel;

/**
 * A helper to load the library generated by the nar-maven-plugin.
 *
 * @author Johannes Schindelin
 * @author HongKee Moon
 */
public class NarHelper {

	/**
	 * Loads the library compiled by the nar-maven-plugin. This function is
	 * called in Parametric.java.
	 * When you update the version number, consider to change the version number
	 * in Parametric.java as well.
	 *
	 * @param clazz
	 *            a class compiled into the same project as the library
	 * @param groupId
	 *            the groupId of the project
	 * @param artifactId
	 *            the artifactId of the project
	 * @param version
	 *            the version of the project
	 */
	public static void loadLibrary( final Class< ? > clazz, final String groupId, final String artifactId, final String version ) {
		final String osName = System.getProperty( "os.name" ).toLowerCase();

		String libPrefix = "lib";
		final String libSuffix;
		if ( osName.startsWith( "mac" ) ) {
			// MacOSX library naming convention
			libSuffix = ".jnilib";
		} else if ( osName.startsWith( "win" ) ) {
			// Windows library naming convention
			libPrefix = "";
			libSuffix = ".dll";
		} else {
			// Linux library naming convention
			libSuffix = ".so";
		}

		//TODO: Dirty, dirty trick... this must point to any file that is in the same folder
		//      as all the folders containing nar binaries for supported platforms.
		//      This is not beautiful at all!
		final String propertiesPath = "/test.jug";
		final URL url = clazz.getResource( propertiesPath );
		//IJ.log( "url = " + url );

		try {
			final String libName = artifactId + "-" + version;
			final String aol = predictAOL();
			final String urlString = url.toString();
			final String libPath;
			if ( urlString.startsWith( "file:" ) ) {

				// for developement purpose, we need to load the native libraries as a file itself
				final String path = urlString.substring( 5, urlString.length() - propertiesPath.length() );
				libPath = path + "/" + libName + "-" + aol + "-jni/lib/" + aol + "/jni/" + libPrefix + libName + libSuffix;
				//System.out.println(libPath);

			} else if ( urlString.startsWith( "jar:file:" ) ) {

				// for Fiji runtime, we load the native library from the jar package.
				final int bang = urlString.indexOf( "!/" );
				if ( bang < 0 ) { throw new UnsatisfiedLinkError( "Unexpected URL: " + urlString ); }
				String path = urlString.substring( 9, bang );
				//IJ.log("path = " + path);

				path = "/" + libName + "-" + aol + "-jni/lib/" + aol + "/jni/" + libPrefix + libName + libSuffix;
				//IJ.log("path2 = " + path);
				//IJ.log("path3 = " + libPrefix + libName + libSuffix);
				libPath = loadNative( libPrefix + libName + libSuffix, path );

				//IJ.log("libPath = " + libPath);
			} else {
				throw new UnsatisfiedLinkError( "Could not load native library: URL of .jar is " + urlString );
			}

			System.load( libPath );
		} catch ( final IOException e ) {
			e.printStackTrace();
			throw new UnsatisfiedLinkError( "Could not find native library" );
		}
	}

	/*
	 * Build AOL(Architecture, Operating system, Linker) string based on the
	 * current system.
	 * The AOL string is required to figure out the exact folder name where the
	 * appropriate native library exists.
	 * In case, it is required to add more machine types, check the "os.name"
	 * and "os.arch" to match the exact native library path.
	 *
	 * Currently, we provide mac(x86_64), windows(x86, amd64), linux(i386,
	 * amd64) and the paths are listed below.
	 * 1. paramaxflow-jni-1.0.0-SNAPSHOT-amd64-Linux-gpp-jni
	 * 2. paramaxflow-jni-1.0.0-SNAPSHOT-amd64-Windows-msvc-jni
	 * 3. paramaxflow-jni-1.0.0-SNAPSHOT-i386-Linux-gpp-jni
	 * 4. paramaxflow-jni-1.0.0-SNAPSHOT-x86_64-MacOSX-gpp-jni
	 * 5. paramaxflow-jni-1.0.0-SNAPSHOT-x86-Windows-msvc-jni
	 */
	private static String predictAOL() throws IOException {
		final String osName = System.getProperty( "os.name" ).toLowerCase();
		final String archName = System.getProperty( "os.arch" ).toLowerCase();

		String aol = null;

		if ( osName.startsWith( "mac" ) ) {
			if ( archName.startsWith( "x86_64" ) ) {
				aol = "x86_64-MacOSX-gpp";
			}
		} else if ( osName.startsWith( "windows" ) ) {
			if ( archName.startsWith( "amd64" ) ) {
				aol = "amd64-Windows-msvc";
			} else if ( archName.startsWith( "x86" ) ) {
				aol = "x86-Windows-msvc";
			}
		} else if ( osName.startsWith( "linux" ) ) {
			if ( archName.startsWith( "amd64" ) ) {
				aol = "amd64-Linux-msvc";
			} else if ( archName.startsWith( "i386" ) ) {
				aol = "i386-Linux-msvc";
			}
		}

		if ( aol == null ) { throw new IOException(); }

		return aol;
	}

	/*
	 * Packaged native library cannot be loaded directly.
	 * Therefore, it is necessary to extract them in the temporary folder.
	 * http://stackoverflow.com/questions/2937406/how-to-bundle-a-native-library-
	 * and-a-jni-library-inside-a-jar
	 */
	public static String loadNative( final String libName, final String libPath ) {
		String path = null;
		try {
			// Create an InputStream for the DataOutputStream writer as a temporary file
			final InputStream in = Tr2dPanel.class.getResourceAsStream( libPath );

			final File fileOut = File.createTempFile( "lib", libName );
			path = fileOut.getAbsolutePath();

			final DataOutputStream writer = new DataOutputStream( new FileOutputStream( fileOut ) );

			long oneChar = 0;
			while ( ( oneChar = in.read() ) != -1 ) {
				writer.write( ( int ) oneChar );
			}

			in.close();
			writer.close();
		} catch ( final Exception e ) {
			e.printStackTrace();
		}

		return path;
	}
}
