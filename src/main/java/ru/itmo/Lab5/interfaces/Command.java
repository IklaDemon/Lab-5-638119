package ru.itmo.Lab5.interfaces;

public interface Command {
  public int numberOfArgs();

  public boolean requiresDragon();

  public String usage();

  public String exec(String arg);
}
