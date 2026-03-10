package ru.itmo.Lab5.interfaces;

import java.util.PriorityQueue;
import java.util.Set;

import ru.itmo.Lab5.dragon.Dragon;

public interface Reader {
  public PriorityQueue<Dragon> read();

  public Set<Long> getIDs();
}
