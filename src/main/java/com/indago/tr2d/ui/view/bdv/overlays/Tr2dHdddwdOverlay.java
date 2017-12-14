/**
 *
 */
package com.indago.tr2d.ui.view.bdv.overlays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.indago.pg.segments.SegmentNode;
import com.indago.tr2d.ui.model.Tr2dHdddwdModel;

import bdv.util.BdvOverlay;
import net.imglib2.realtransform.AffineTransform2D;

/**
 * @author jug
 */
public class Tr2dHdddwdOverlay extends BdvOverlay {

	private final Tr2dHdddwdModel hdddwdModel;
	private final int time;

	/**
	 * @param model
	 */
	public Tr2dHdddwdOverlay( final Tr2dHdddwdModel model ) {
		this.hdddwdModel = model;
		this.time = -1;
	}

	/**
	 * @param model
	 */
	public Tr2dHdddwdOverlay( final Tr2dHdddwdModel model, final int t ) {
		this.hdddwdModel = model;
		this.time = t;
	}

	/**
	 * @see bdv.util.BdvOverlay#draw(java.awt.Graphics2D)
	 */
	@Override
	protected void draw( final Graphics2D g ) {
//		final Assignment< IndicatorNode > pgSolution = hdddwdModel.getSolution();
//
//		if ( pgSolution != null ) {
//			final AffineTransform2D trans = new AffineTransform2D();
//			getCurrentTransform2D( trans );
//
//			final int t = ( this.time == -1 ) ? info.getTimePointIndex() : this.time;
//			drawCOMs( g, t );
//			drawCOMTails( g, t, 5 );
//		}
	}

	/**
	 * @param g
	 * @param cur_t
	 * @param length
	 */
	private void drawCOMTails( final Graphics2D g, final int cur_t, final int length ) {
	}

	/**
	 * @param g
	 * @param trans
	 * @param from_t
	 * @param from
	 * @param to
	 * @param color
	 * @param i
	 * @param length
	 */
	private void drawCOMTailSegment(
			final Graphics2D g,
			final AffineTransform2D trans,
			final int from_t,
			final SegmentNode from,
			final SegmentNode to,
			final Color color,
			final int i,
			final int length ) {

		final Graphics2D g2 = g;
		g2.setStroke( new BasicStroke( 3 * ( ( length - i ) / ( ( float ) length ) ) ) );


	}

	/**
	 * @param g
	 */
	private void drawCOMs( final Graphics2D g, final int cur_t ) {
		final Color theRegularColor = Color.RED.darker();
		final Color theForcedColor = Color.RED.brighter();
		final Color theAvoidedColor = Color.GRAY.brighter().brighter();

		final Graphics2D g2 = g;
		final int len = 3;

//		final Tr2dTrackingProblem tr2dPG = hdddwdModel.getTrackingProblem();
//		final Assignment< IndicatorNode > pgSolution = hdddwdModel.getSolution();
//
//		final AffineTransform2D trans = new AffineTransform2D();
//		getCurrentTransform2D( trans );
//		final Tr2dSegmentationProblem tp0 = tr2dPG.getTimepoints().get( cur_t );
//		for ( final SegmentNode segvar : tp0.getSegments() ) {
//			if ( pgSolution.getAssignment( segvar ) == 1 || tp0.getEditState().isAvoided( segvar ) ) {
//				final RealLocalizable com = segvar.getSegment().getCenterOfMass();
//				final double[] lpos = new double[ 2 ];
//				final double[] gpos = new double[ 2 ];
//				com.localize( lpos );
//				trans.apply( lpos, gpos );
//
//				if ( tp0.getEditState().isForced( segvar ) ) {
//					g.setColor( theForcedColor );
//					g2.setStroke( new BasicStroke( ( float ) 3.5 ) );
//					len = 8;
//					g.drawOval( ( int ) gpos[ 0 ] - len, ( int ) gpos[ 1 ] - len, len * 2, len * 2 );
//				} else if ( tp0.getEditState().isAvoided( segvar ) ) {
//					g.setColor( theAvoidedColor );
//					g2.setStroke( new BasicStroke( 4 ) );
//					len = 8;
//					g.drawLine( ( int ) gpos[ 0 ] - len, ( int ) gpos[ 1 ] - len, ( int ) gpos[ 0 ] + len, ( int ) gpos[ 1 ] + len );
//					g.drawLine( ( int ) gpos[ 0 ] - len, ( int ) gpos[ 1 ] + len, ( int ) gpos[ 0 ] + len, ( int ) gpos[ 1 ] - len );
//				} else {
//					g.setColor( theRegularColor );
//					g2.setStroke( new BasicStroke( ( float ) 2.0 ) );
//					len = 4;
//					g.drawOval( ( int ) gpos[ 0 ] - len, ( int ) gpos[ 1 ] - len, len * 2, len * 2 );
//				}
//			}
//		}
	}

}
