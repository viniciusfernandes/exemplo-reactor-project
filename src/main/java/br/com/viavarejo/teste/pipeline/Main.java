package br.com.viavarejo.teste.pipeline;

import java.util.Date;

public class Main {
	public static void main(String[] args) {
		final Date init = new Date();

		new Pipeline().toDo(printFile()).toDo(downloadFile()).toDo(parseFile())
				.onComplete(() -> System.out.println("Everything is OK!!!"));

		final Date end = new Date();
		System.out.println((end.getTime() - init.getTime()) / 1000);
	}

	private static Runnable downloadFile() {
		return () -> {
			System.out.println("Downloding File Started");
			try {
				Thread.sleep(2000l);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Downloding File Completed");
		};
	}

	private static Runnable printFile() {
		return () -> {
			System.out.println("Printing File Started");
			try {
				Thread.sleep(2000l);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Printing File Completed");
		};
	}

	private static Runnable parseFile() {
		return () -> {
			System.out.println("Parsing File to PDF Started");
			try {
				Thread.sleep(2000l);
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Parsing File to PDF Completed");
		};
	}
}
