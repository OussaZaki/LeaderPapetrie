package ui.touch;

import java.util.ArrayList;

import java.util.List;

public class AnimatorManager implements Runnable {
	static AnimatorManager instance = new AnimatorManager();

	List<Animator> running = new ArrayList<Animator>();
	List<Animator> toRemove = new ArrayList<Animator>();

	AnimatorManager() {
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.setDaemon(true);
		t.start();
	}

	public synchronized static AnimatorManager getInstance() {
		return instance;
	}

	public void start(Animator animator) {
		animator.setStartTime(System.currentTimeMillis());
		animator.start();
		running.add(animator);
		synchronized (this) {
			notifyAll();
		}
	}

	@Override
	public void run() {
		while (true) {
			final long currentTimeMillis = System.currentTimeMillis();
			for (int i = 0; i < running.size(); i++) {
				Animator a = running.get(i);
				if (a.pulse(currentTimeMillis - a.getStartTime())) {
					toRemove.add(a);
				}
			}

			int size = toRemove.size();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					Animator a = toRemove.get(i);
					this.running.remove(a);
				}
				toRemove.clear();
			}

			try {
				Thread.sleep(10, 001);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (running.size() == 0) {
				try {
					synchronized (this) {
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}