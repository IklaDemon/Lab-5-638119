package ru.itmo.Lab5.interfaces;

import java.util.PriorityQueue;
import ru.itmo.Lab5.dragon.Dragon;

/**
 * Defines writing operations for saving dragons to a data source.
 *
 * <p>
 * An implementation of this interface must be able to persist
 * a collection of dragons.
 */
public interface Writer {
  /**
   * Writes the specified collection of dragons to the data source.
   *
   * @param input collection to write
   * @return {@code true} if the collection was written successfully,
   *         {@code false} otherwise
   */
  public boolean write(PriorityQueue<Dragon> input);
}
