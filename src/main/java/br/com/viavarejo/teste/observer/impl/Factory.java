package br.com.viavarejo.teste.observer.impl;

import br.com.viavarejo.teste.observer.Event;
import br.com.viavarejo.teste.observer.Subject;

public class Factory extends Subject {
	public void contract(Employee employee) {
		add(employee);
	}

	public void lunchTime() {
		notifi(new Event(1, "lunch time at 11:30am"));
	}

	public void gotHomeTime() {
		notifi(new Event(2, "got home time at 17:00pm"));
	}
}
