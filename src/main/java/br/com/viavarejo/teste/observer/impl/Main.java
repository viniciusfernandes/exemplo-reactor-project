package br.com.viavarejo.teste.observer.impl;

public class Main {

	public static void main(String[] args) {
		final var factory = new Factory();
		factory.contract(new Employee("Paul"));
		factory.contract(new Employee("Marc"));
		factory.contract(new Employee("Jim"));

		factory.lunchTime();
	}

}
