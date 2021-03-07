package br.com.viavarejo.teste.observer.impl;

import br.com.viavarejo.teste.observer.Event;
import br.com.viavarejo.teste.observer.Observer;

public class Employee implements Observer {
	String name;

	public Employee(String name) {
		super();
		this.name = name;
	}

	@Override
	public void observe(Event event) {
		System.out.println("Employee " + name + " doing: " + event.description);
	}

}
