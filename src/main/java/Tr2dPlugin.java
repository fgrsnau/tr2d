import javax.swing.JOptionPane;

import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import com.indago.app.hernan.Tr2dApplication;
import com.indago.io.ImageSaver;

import net.imagej.ops.OpService;

/**
 * Tr2d Plugin for Fiji/ImageJ2
 *
 * @author Florian Jug
 */

@Plugin( type = ContextCommand.class, headless = true, menuPath = "Plugins>Tracking>Tr2d 0.1 (alpha)" )
public class Tr2dPlugin implements Command {

	@Parameter
	private OpService ops;

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Tr2dApplication.isStandalone = false;
		Tr2dApplication.ops = ops;
		ImageSaver.context = ops.context();

		try {
			Tr2dApplication.main( null );
		} catch ( final NoClassDefFoundError err ) {
			final String jlp = System.getProperty( "java.library.path" );
			final String msgs =
					"Gurobi seems to be not installed on your system.\n" + "Please visit 'www.gurobi.com' for further information.\n\n" + "Java library path: " + jlp;
			JOptionPane.showMessageDialog(
					null,
					msgs,
					"Gurobi not installed?",
					JOptionPane.ERROR_MESSAGE );
			err.printStackTrace();
			Tr2dApplication.quit( 100 );
		}
	}
}