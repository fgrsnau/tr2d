package com.indago.app.selectsegment;

import com.indago.data.segmentation.LabelData;

import net.trackmate.graph.object.AbstractObjectVertex;
import net.trackmate.revised.model.HasLabel;
import net.trackmate.spatial.HasTimepoint;

public class SegmentVertex extends AbstractObjectVertex< SegmentVertex, SubsetEdge > implements HasTimepoint, HasLabel {

	private LabelData labelData;

	private int timepoint;

	public SegmentVertex init( final LabelData labelData ) {
		this.labelData = labelData;
		this.timepoint = 0;
		return this;
	}

	public LabelData getLabelData() {
		return labelData;
	}

	@Override
	public int getTimepoint() {
		return timepoint;
	}

	public void setTimepoint( final int timepoint ) {
		this.timepoint = timepoint;
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public void setLabel( final String label ) {}

	@Override
	public String toString() {
		return "v" + labelData.getId() + " t" + timepoint;
	}
}