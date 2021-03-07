package br.com.viavarejo.teste;

import java.util.Date;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Teste {

	public static void main(String[] args) throws InterruptedException {

		final var categorias = findAll();
		final var novaCategoria = Flux.just("e", "f");
		final var valores = Flux.just(1, 2, 3);
		final var todasCategorias = categorias.concatWith(novaCategoria).map(categoria -> categoria.toUpperCase());

		Flux.zip(todasCategorias, valores).map(tupla -> tupla.getT1());
	}

	public static Mono<Integer> count() {
		return Mono.just(10);
	}

	public static Flux<String> findAll() {
		return Flux.just("a", "b", "c");
	}

	public static Flux<String> findById(Integer id) {
		return id.equals(1) ? Flux.just("a") : Flux.empty();
	}

	private static void teste() {
		final var stream = Flux.just(1, 2, 3).parallel(8).runOn(Schedulers.parallel()).map(val -> {
			try {
				Thread.sleep(1000l);
			}
			catch (final Exception e) {
			}
			return val;
		});

		System.out.println("CPUs: " + Runtime.getRuntime().availableProcessors());
		final var init = new Date();
		stream.subscribe(valor -> System.out.println(Thread.currentThread().getName() + "-> Valor: " + valor));
		final var end = new Date();

		System.out.println("Time: " + (end.getTime() - init.getTime()));

		Thread.sleep(5000l);
	}

}
