import java.util.List;
import java.util.ArrayList;
import java.lang.Runnable;
import java.util.function.Consumer;
/**
 * An object that emits events
 * @param <T> The type of event that is emitted
 */
public class EventEmitter<T> {
  private List<Consumer<T>> listeners = new ArrayList<>();

  /**
   * Runs the provided lambda when an event is emitted
   * @param fn the function to be run
   */
  public final void run(Runnable fn) {
    listeners.add((T drop) -> fn.run());
  }
  public final void run(Consumer<T> fn) {
    listeners.add(fn);
  }

  /**
   * Notifies all listeners with the provided event
   * @param e the event
   */
  protected final void emit(T e) {
    int size = listeners.size();
    if (size == 0) {
      return;
    }
    if (size == 1) {
      listeners.get(0).accept(e);
    } else {
      listeners.stream().forEach((Consumer<T> c) -> c.accept(e));
    }
  }

  /** Removes all listeners from the <code>EventEmitter</code> */
  public final void clearListeners() {
    listeners.clear();
  }
}
