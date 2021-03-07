package br.com.viavarejo.teste.pipeline;

import java.util.ArrayList;
import java.util.List;

public class Pipeline {

	private final List<Runnable> tasks = new ArrayList<>();

	public Pipeline toDo(Runnable taskToDo) {
		tasks.add(taskToDo);
		return this;
	}

	public void onComplete(Runnable onCompleteTask) {
		final List<Thread> pool = new ArrayList<>();
		Thread t = null;
		for (final Runnable runnable : tasks) {
			t = new Thread(runnable);
			pool.add(t);
			t.start();
		}

		if (isDone(pool)) {
			new Thread(onCompleteTask).start();
		}
	}

	private boolean isDone(List<Thread> pool) {
		while (true) {
			for (final Thread t : pool) {
				if (t.isAlive()) {
					break;
				}

				return true;
			}
		}
	}
}
