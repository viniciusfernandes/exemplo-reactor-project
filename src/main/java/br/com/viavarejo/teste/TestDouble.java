package br.com.viavarejo.teste;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

public class TestDouble {

  private static double doubleTotal = 0;
  private static double kahanDoubleTotal = 0;
  private static BigDecimal bdTotal = BigDecimal.ZERO;

  public static void main(final String[] args) {

    final int iteration = 1000000;
    final double dIntervalo, bInterval;
    final double[] values = construct(Math::random, iteration);
    System.out.printf("Time taken for double calculation: %fms%n", dIntervalo = time(TestDouble::sumToDouble, values));
    System.out.printf("Time taken for Kahan double calculation: %fms%n", time(TestDouble::kahanSumToDouble, values));
    System.out.printf("Time taken for BigDecimal calculation: %fms%n", bInterval = time(TestDouble::sumToBigDecimal, values));
    System.out.printf("Value diff for simple sum: %s%n", bdTotal.subtract(BigDecimal.valueOf(doubleTotal)).abs().toString());
    System.out.printf("Value diff for Kahan sum: %s%n", bdTotal.subtract(BigDecimal.valueOf(kahanDoubleTotal)).abs().toString());
    System.out.printf("Rate bigdecimal/double: %s%n", bInterval / dIntervalo);

    rounding();
  }

  private static double time(final Consumer<double[]> consumer, final double[] values) {

    final long start = System.nanoTime();
    consumer.accept(values);
    final long end = System.nanoTime();

    return 1.0 * (end - start) / (1000 * 1000);
  }

  private static double[] construct(final DoubleSupplier supplier, final int size) {
    return DoubleStream.iterate(supplier.getAsDouble(), operand -> supplier.getAsDouble()).limit(size).toArray();
  }

  private static void kahanSumToDouble(final double[] values) {
    double approxError = 0;
    for (final double value : values) {
      final double adjustedValue = value - approxError;
      final double adjustedSum = kahanDoubleTotal + adjustedValue;
      approxError = adjustedSum - kahanDoubleTotal - adjustedValue;
      kahanDoubleTotal = adjustedSum;
    }
  }

  private static void sumToDouble(final double[] values) {

    for (final double value : values) {
      doubleTotal += value;

    }
  }

  private static void sumToBigDecimal(final double[] values) {

    for (final double value : values) {
      bdTotal = bdTotal.add(BigDecimal.valueOf(value));
    }

  }

  private static void rounding() {
    final double c = 1.1;
    double x = c;
    BigDecimal b = BigDecimal.valueOf(c);
    for (int i = 0; i < 10; i++) {
      x *= c;
      b = b.multiply(BigDecimal.valueOf(c));
    }

    System.out.println("double : " + x);
    System.out.println("double rounded: " + monetary(x));
    System.out.println("Big : " + b);
    System.out.println("Big roundend: " + b.setScale(SCALE_MONETARIA, ROUNDING_MODE));

  }

  public static final int SCALE_MONETARIA = 2;
  public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

  public static BigDecimal monetary(final Number valor) {
    return BigDecimal.valueOf(valor.doubleValue()).setScale(SCALE_MONETARIA, ROUNDING_MODE);
  }

}
