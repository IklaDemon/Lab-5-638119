package ru.itmo.Lab5.interfaces;

import java.util.PriorityQueue;

import ru.itmo.Lab5.dragon.Dragon;

public interface Writer {
  public boolean write(PriorityQueue<Dragon> input);
}
