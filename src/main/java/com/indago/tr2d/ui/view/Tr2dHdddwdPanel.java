/**
 *
 */
package com.indago.tr2d.ui.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang.NotImplementedException;

import com.indago.tr2d.ui.model.Tr2dModel;
import com.indago.tr2d.ui.util.UniversalFileChooser;

import weka.gui.ExtensionFileFilter;

/**
 * @author jug
 */
public class Tr2dHdddwdPanel extends JPanel implements ActionListener {

	private final Tr2dModel model;

	private JButton importSolution;

	/**
	 * @param model
	 */
	public Tr2dHdddwdPanel( final Tr2dModel model ) {
		super( new BorderLayout() );
		this.model = model;
		buildGui();
	}

	private void buildGui() {
		importSolution = new JButton( "import..." );
		importSolution.addActionListener( this );

		this.add( importSolution, BorderLayout.NORTH );
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed( final ActionEvent e ) {
		if ( e.getSource().equals( importSolution ) ) {
			final File solutionFileToImport = UniversalFileChooser.showLoadFileChooser(
					model.getMainPanel().getTopLevelAncestor(),
					"",
					"Choose folder for tr2d Schnitzcell export...",
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
