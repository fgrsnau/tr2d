/**
 *
 */
package com.indago.app.hernan.models;

import java.util.List;

import com.indago.app.hernan.costs.HernanCostConstants;
import com.indago.data.segmentation.ConflictGraph;
import com.indago.data.segmentation.LabelingSegment;
import com.indago.old_fg.CostsFactory;
import com.indago.segmentation.SumImageMovieSequence;
import com.indago.tr2d.models.Tr2dSegmentationModel;
import com.indago.tr2d.models.Tr2dTrackingModel;
import com.indago.tr2d.ui.model.Tr2dModel;
import com.indago.tr2d.ui.model.Tr2dWekaSegmentationModel;
import com.indago.util.TicToc;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Pair;

/**
 * @author jug
 */
public class Tr2dTrackingModelHernan {

	private final Tr2dModel tr2dModel;
	private final Tr2dWekaSegmentationModel tr2dSegModel;

	private final SumImageMovieSequence sumImgMovie;;

	private final Tr2dTrackingModel trackingProblem;
	private final CostsFactory< LabelingSegment > segmentCosts;
	private final CostsFactory< LabelingSegment > appearanceCosts;
	private final CostsFactory< Pair< LabelingSegment, LabelingSegment > > moveCosts;
	private final CostsFactory< Pair< LabelingSegment, Pair< LabelingSegment, LabelingSegment > > > divisionCosts;
	private final CostsFactory< LabelingSegment > disappearanceCosts;

	private final RandomAccessibleInterval< DoubleType > imgSolution = null;

	/**
	 * @param model
	 */
	public Tr2dTrackingModelHernan(
			final Tr2dModel model,
			final Tr2dWekaSegmentationModel modelSeg,
			final CostsFactory< LabelingSegment > segmentCosts,
			final CostsFactory< LabelingSegment > appearanceCosts,
			final CostsFactory< Pair< LabelingSegment, LabelingSegment > > movementCosts,
			final CostsFactory< Pair< LabelingSegment, Pair< LabelingSegment, LabelingSegment > > > divisionCosts,
			final CostsFactory< LabelingSegment > disappearanceCosts ) {
		this.tr2dModel = model;

		this.appearanceCosts = appearanceCosts;
		this.moveCosts = movementCosts;
		this.divisionCosts = divisionCosts;
		this.disappearanceCosts = disappearanceCosts;
		this.trackingProblem =
				new Tr2dTrackingModel( appearanceCosts,
						movementCosts, HernanCostConstants.TRUNCATE_COST_THRESHOLD,
						divisionCosts, HernanCostConstants.TRUNCATE_COST_THRESHOLD,
						disappearanceCosts );
		this.segmentCosts = segmentCosts;

		this.tr2dSegModel = modelSeg;
		this.sumImgMovie = new SumImageMovieSequence( tr2dSegModel );
	}

	/**
	 *
	 */
	@SuppressWarnings( "unchecked" )
	public void run() {
		processSegmentationInputs();
		buildTrackingModel();
		buildFactorGraph();
	}

	/**
	 *
	 */
	public void processSegmentationInputs() {
		try {
			sumImgMovie.processFrames();
		} catch ( final IllegalAccessException e ) {
			System.err.println(
					"Segmentation Hypotheses could not be accessed!\nYou must create a segmentation prior to starting the tracking!" );
			e.printStackTrace();
			return;
		}
	}

	/**
	 *
	 */
	public void buildTrackingModel() {
		final TicToc tictoc = new TicToc();
		for ( int frameId = 0; frameId < sumImgMovie.getNumFrames(); frameId++ ) {
			System.out.println(
					String.format( "Working on frame %d of %d...", frameId + 1, sumImgMovie.getNumFrames() ) );

			// =============================
			// build Tr2dSegmentationProblem
			// =============================
			tictoc.tic( "Constructing Tr2dSegmentationProblem..." );
			final List< LabelingSegment > segments =
					sumImgMovie.getLabelingSegmentsForFrame( frameId );
			final ConflictGraph< LabelingSegment > conflictGraph =
					sumImgMovie.getConflictGraph( frameId );
			final Tr2dSegmentationModel segmentationProblem =
					new Tr2dSegmentationModel( frameId, segments, segmentCosts, conflictGraph );
			tictoc.toc( "done!" );

			// ==========================
			// add to Tr2dTrackingProblem
			// ==========================
			tictoc.tic( "Constructing Tr2dSegmentationProblem..." );
			trackingProblem.addSegmentationProblem( segmentationProblem );
			tictoc.toc( "done!" );
		}
		System.out.println( "Tracking graph was built sucessfully!" );
	}

	/**
	 *
	 */
	public void buildFactorGraph() {

	}
}