/**
 *
 */
package com.indago.tr2d.ui.model;

import java.util.ArrayList;
import java.util.List;

import com.indago.io.ProjectFolder;
import com.indago.tr2d.io.projectfolder.Tr2dProjectFolder;
import com.indago.tr2d.plugins.seg.Tr2dSegmentationPlugin;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.integer.IntType;

/**
 * @author jug
 */
public class Tr2dSegmentationCollectionModel {

	private final Tr2dModel model;

	private final ProjectFolder projectFolder;

	private final List< Tr2dSegmentationPlugin > plugins = new ArrayList<>();

//	private final Tr2dImportedSegmentationModel importedSegmentationModel;
//	private final Tr2dWekaSegmentationModel wekaModel;

	/**
	 * @param model
	 */
	public Tr2dSegmentationCollectionModel( final Tr2dModel model ) {
		this.model = model;
		projectFolder = model.getProjectFolder().getFolder( Tr2dProjectFolder.SEGMENTATION_FOLDER );

//		importedSegmentationModel = new Tr2dImportedSegmentationModel( this, projectFolder );
//		wekaModel = new Tr2dWekaSegmentationModel( this, projectFolder );
	}

//	/**
//	 * @return the importedSegmentationModel
//	 */
//	public Tr2dImportedSegmentationModel getImportedSegmentationModel() {
//		return importedSegmentationModel;
//	}
//
//	/**
//	 * @return the wekaModel
//	 */
//	public Tr2dWekaSegmentationModel getWekaModel() {
//		return wekaModel;
//	}
//
	/**
	 * @return
	 */
	public ProjectFolder getProjectFolder() {
		return projectFolder;
	}

	/**
	 * @return
	 */
	public Tr2dModel getModel() {
		return model;
	}

	public void addPlugin( final Tr2dSegmentationPlugin segPlugin ) {
		this.plugins.add( segPlugin );
	}

	public List< Tr2dSegmentationPlugin > getPlugins() {
		return plugins;
	}

	/**
	 * @return
	 */
	public List< RandomAccessibleInterval< IntType > > getSumImages() {
		final List< RandomAccessibleInterval< IntType > > ret = new ArrayList< RandomAccessibleInterval< IntType > >();
		for ( final Tr2dSegmentationPlugin plugin : plugins ) {
			ret.addAll( plugin.getOutputs() );
		}
//		ret.addAll( wekaModel.getSegmentHypotheses() );
//		ret.addAll( importedSegmentationModel.getSegmentHypothesesImages() );
		return ret;
	}

}
