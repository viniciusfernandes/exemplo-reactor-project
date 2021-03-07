package br.com.viavarejo.teste.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
	private final List<Observer> observers = new ArrayList<>();

	protected void add(Observer observer) {
		observers.add(observer);
	}

	protected void notifi(Event event) {
		observers.forEach(obs -> obs.observe(event));
	}
}
