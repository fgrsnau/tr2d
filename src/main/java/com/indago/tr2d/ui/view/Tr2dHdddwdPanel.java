/**
 *
 */
package com.indago.tr2d.ui.view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang.NotImplementedException;

import com.indago.tr2d.ui.model.Tr2dHdddwdModel;
import com.indago.tr2d.ui.model.Tr2dModel;
import com.indago.tr2d.ui.util.UniversalFileChooser;
import com.indago.tr2d.ui.view.bdv.overlays.Tr2dHdddwdOverlay;

import bdv.util.Bdv;
import bdv.util.BdvHandlePanel;
import weka.gui.ExtensionFileFilter;

/**
 * @author jug
 */
public class Tr2dHdddwdPanel extends JPanel implements ActionListener {

	private final Tr2dHdddwdModel model;

	private JButton importSolution;

	private JPanel bdvPanel;

	/**
	 * @param model
	 */
	public Tr2dHdddwdPanel( final Tr2dModel model ) {
		super( new BorderLayout() );
		this.model = new Tr2dHdddwdModel( model );
		buildGui();
		this.model.bdvAdd( model.getRawData(), "RAW" );

		this.model.bdvAdd( new Tr2dHdddwdOverlay( this.model ), "hdddwd_overlay" );
	}

	private void buildGui() {
		// ---- import button -------------------------
		importSolution = new JButton( "import..." );
		importSolution.addActionListener( this );

		this.add( importSolution, BorderLayout.NORTH );

		// ---- viewer (BDV) -------------------------
		bdvPanel = buildViewerPanel();
		this.add( bdvPanel, BorderLayout.CENTER );
	}

	private JPanel buildViewerPanel() {
		final JPanel panel = new JPanel( new BorderLayout() );

		model.bdvSetHandlePanel(
				new BdvHandlePanel( ( Frame ) this.getTopLevelAncestor(), Bdv
						.options()
						.is2D()
						.inputTriggerConfig( model.getTr2dModel().getDefaultInputTriggerConfig() ) ) );

		panel.add( model.bdvGetHandlePanel().getViewerPanel(), BorderLayout.CENTER );

		return panel;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent e ) {
		if ( e.getSource().equals( importSolution ) ) {
			final File solutionFileToImport = UniversalFileChooser.showLoadFileChooser(
					model.getTr2dModel().getMainPanel().getTopLevelAncestor(),
					"",
					"Choose solution text file...",
					new ExtensionFileFilter( "hdddwd", "HDDDWD solution text file" ) );
			if ( solutionFileToImport.exists() ) {
				importSolutionFile( solutionFileToImport );
			} else {
				JOptionPane.showMessageDialog( this, "Weird...", "Impossible Error", JOptionPane.ERROR_MESSAGE );
			}
		}
	}

	/**
	 * @param solutionFileToImport
	 */
	private void importSolutionFile( final File solutionFileToImport ) {
		throw new NotImplementedException( "soon..." );
	}

}
