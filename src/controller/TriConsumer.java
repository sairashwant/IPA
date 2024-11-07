package controller;

/**
 * A functional interface representing an operation that accepts three input arguments and returns
 * no result. This interface is intended to be used as a target for lambda expressions or method
 * references where an operation needs to be performed on three input values, but no return value is
 * expected.
 *
 * <p>For example, this can be used to process three values in a single operation without returning
 * a result.
 *
 * @param <T> the type of the first input argument
 * @param <U> the type of the second input argument
 * @param <V> the type of the third input argument
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @param v the third input argument
   */
  void accept(T t, U u, V v);
}