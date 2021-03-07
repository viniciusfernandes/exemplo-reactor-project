package br.com.viavarejo.teste;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//- Construcao de um fluxo de dados
//- Como transformar o fluxo de dados e gerar um segundo com o "map"
//- Como logar a sequencia de execucao
//- Erro comum ao nao reatribuir, ou programar imperativamente, os fluxos de dados
//- Dados dois fluxo de dados, como construir um terceiro a partir deles
//- Como construir condicionais.
//- Como tratar excecoes: como implementar o equivalente ao try/catch
//- Mas praticas: multiplos maps, nao tratar os erros,
public class Example {
	private static Logger logger = Logger.getLogger(Example.class.getName());

	public static void main(String[] args) {

		System.out.println("\n****** STREAMING ******");

		var pipeline = Flux.just("a", "b", "c").map(letter -> letter.toUpperCase());
		final var pipe2 = Flux.just("d", "e");

		pipeline = pipeline.concatWith(pipe2);
		pipeline.log().subscribe(result -> System.out.println(result));

		System.out.println("\n***** CONDITIONAL *****");

		// Se estiver EMPTY imprimir EMPTY, senao, concatenar todos os valores em uppercase
		pipeline = findAll();
		var uppercase = pipeline.switchIfEmpty(Flux.just("EMPTY")).map(data -> data.toUpperCase()).collectList()
				.map(list -> {
					final StringBuilder s = new StringBuilder();
					for (final String data : list) {
						s.append(data);
					}
					return s.toString();
				});

		WebFlux(uppercase);

		System.out.println("\n***********************");

		pipeline = findAll();
		uppercase = pipeline.switchIfEmpty(Mono.just("EMPTY")).collectList().map(list -> {
			final StringBuilder s = new StringBuilder();
			for (final String data : list) {
				s.append(data.toUpperCase());
			}
			return s.toString();
		});

		WebFlux(uppercase);

		System.out.println("\n******* COMPOSING *******");
		sum().subscribe(value -> System.out.println("Composing Flux. Total value: " + value));

		anotherSum().subscribe(value -> System.out.println("Composing Mono. Total value: " + value));

		System.out.println("\n****** EXCEPTION ******");

		streamException().onErrorResume(IllegalArgumentException.class, ex -> {
			logger.log(Level.SEVERE, "Fail to exetcute", ex);
			return Flux.just(666);
		}).onErrorResume(IllegalStateException.class, ex -> {
			logger.log(Level.SEVERE, "Fail to exetcute", ex);
			return Flux.just(999);
		}).subscribe(val -> System.out.println("value ok: " + val));

	}

	private static void WebFlux(Mono<String> publisher) {
		publisher.subscribe(result -> System.out.println(result));
	}

	private static Flux<String> findAll() {
		return Flux.just("a", "b", "c");
		// return Flux.empty();
	}

	private static Flux<Integer> sum() {

		final var numbers1 = Flux.just(1, 2, 3).collectList().map(nums -> {
			int tot = 0;
			for (final Integer val : nums) {
				tot += val;
			}

			return tot;
		});

		final var numbers2 = Flux.just(4, 5, 6).collectList().map(nums -> {
			int tot = 0;
			for (final Integer val : nums) {
				tot += val;
			}

			return tot;
		});

		return Flux.zip(numbers1, numbers2).map(tuple -> tuple.getT1() + tuple.getT2());
	}

	private static Mono<Integer> anotherSum() {

		final var numbers1 = Mono.just(List.of(1, 2, 3)).map(nums -> {
			int tot = 0;
			for (final Integer val : nums) {
				tot += val;
			}
			return tot;
		});

		final var numbers2 = Mono.just(List.of(4, 5, 6)).map(nums -> {
			int tot = 0;
			for (final Integer val : nums) {
				tot += val;
			}
			return tot;
		});

		return numbers1.zipWith(numbers2, (x, y) -> x + y);
	}

	private static Flux<Integer> streamException() {
		return Flux.range(1, 10).flatMap(val -> {
			if (val > 222) {
				return Flux.error(new IllegalArgumentException("Wrong value: " + val));
			}
			else if (val > 2) {
				return Flux.error(new IllegalStateException("Wrong state: " + val));
			}
			return Flux.just(2 * val);
		});
	}

}
