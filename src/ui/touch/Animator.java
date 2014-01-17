package ui.touch;

public abstract class Animator {

	long duration = 10000;
	private int repeatCount = 1;
	private long startTime;
	boolean running = false;

	/**
	 * Returns the duration of the animation.
	 * 
	 * @return the length of the animation, in milliseconds. a negative value
	 *         indicates an infinite duration.
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Sets the duration for the animation
	 * 
	 * @param duration
	 *            the length of the animation, in milliseconds.
	 * 
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return true if finished
	 */
	public boolean pulse(long totalElapsed) {
		if (!isRunning()) {
			return true;
		}
		long dur = duration;
		double rep = repeatCount; // promote for better long calculations

		dur = (long) (dur * rep);
		if (totalElapsed < dur) {
			process(totalElapsed);
			return false;
		} else {
			process(dur);
			stop();

			return true;
		}

	}

	/**
	 * Turns the elapsed time into a fraction, which is then sent out as a
	 * timingEvent to all targets of this Clip
	 */
	private void process(long timeElapsed) {
		if (!running) {
			return;
		}
		float fraction;

		if (timeElapsed >= duration) {
			process(1f, timeElapsed);
		} else {

			if (duration == 0) {
				fraction = 1f;
			} else {

				double iterationCount = ((double) timeElapsed) / duration;

				iterationCount = iterationCount % 1.0;

				fraction = (float) iterationCount;
			}

			// fraction = interpolator.interpolate(fraction);

			process(fraction, timeElapsed);
		}
	}

	abstract public void process(float fraction, long totalElapsed);

	public void setStartTime(long currentTimeMillis) {
		this.startTime = currentTimeMillis;

	}

	public long getStartTime() {
		return startTime;
	}

	public void stop() {
		this.running = false;

	}

	public void start() {
		this.running = true;

	}

	public boolean isRunning() {

		return this.running;
	}

}
