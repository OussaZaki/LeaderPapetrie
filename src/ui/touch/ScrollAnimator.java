package ui.touch;

public class ScrollAnimator extends Animator {

	private ScrollableList scroll;
	private long startOffset;
	private long stopOfset;

	public ScrollAnimator(ScrollableList scrollableList) {
		this.scroll = scrollableList;
		this.duration = 200;
	}

	@Override
	public void process(float fraction, long totalElapsed) {
		double offset = startOffset + Math.sqrt(fraction)
				* (stopOfset - startOffset);
		scroll.setOffsetY((int) Math.round(offset));

	}

	public void setStart(int offsetY) {
		this.startOffset = offsetY;
	}

	public void setStop(long targetOffset) {
		this.stopOfset = targetOffset;
	}

}