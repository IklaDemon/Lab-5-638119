package ru.itmo.Lab5.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Set;

import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.enums.DragonCharacter;
import ru.itmo.Lab5.interfaces.Reader;
import ru.itmo.Lab5.interfaces.Writer;

/**
 * Collection Manager.
 * managers the Priority queue made of dragons
 * manages also the IDs with a set
 */
public class CollectionManager {
  private Reader reader;
  private Writer writer;
  private PriorityQueue<Dragon> collection;
  private Set<Long> iDs;
  private Date creationDate;
  private File file;

  // constructor
  public CollectionManager(Reader reader, Writer writer, String filePath) {
    if (reader == null) {
      throw new NullPointerException("Reader is null");
    }
    if (writer == null) {
      throw new NullPointerException("writer is null");
    }
    this.reader = reader;
    this.writer = writer;
    this.creationDate = new Date();
    this.setFilePath(filePath);
    this.importFromExternalSource();
  }

  private void setFilePath(String filePath) {
    if (filePath == null) {
      throw new NullPointerException("filePath is null. Should not be null");
    }
    if (filePath.equals("")) {
      throw new IllegalArgumentException("filePath = " + filePath + ". Should not be empty");
    }
    File file = new File(filePath);
    if (file.exists() && !file.isDirectory()) {
      this.file = file;
    } else {
      throw new IllegalArgumentException("filePath does not exists or is a directory");
    }
  }

  /**
   * Import dragons from xml file
   *
   * @throws IllegalArgumentException
   * @throws RuntimeException
   * @throws NullPointerException
   * @throws ParseException
   */
  private void importFromExternalSource() {
    this.collection = reader.read();
    iDs = reader.getIDs();
  }

  /**
   * Export dragons to xml file
   */
  public boolean exportToExternalSource() {
    return this.writer.write(this.collection);
  }

  /**
   * Returns a new ID
   *
   * @return a new ID
   */
  public long genNewID() {
    if (iDs == null) {
      throw new IllegalArgumentException("iDs is null, method should be called after import and getIDs");
    }
    long newId = 1;
    while (iDs.contains(newId)) {
      newId++;
    }
    return newId;
  }

  /**
   * Returns current date
   *
   * @return current date
   */
  public Date genNewDate() {
    Date now = new Date();
    return now;
  }

  public Dragon findById(long id) {
    for (Dragon d : collection) {
      if (d.getId() == id) {
        return d;
      }
    }
    return null;
  }

  // --- utility methods to make life easier ---
  public void addDragon(Dragon dragon) {
    if (dragon == null)
      throw new IllegalArgumentException("Dragon is null");
    if (this.iDs.add(dragon.getId()) == false)
      throw new IllegalArgumentException("Duplicated ID");
    this.collection.add(dragon);
  }

  public boolean update(long id, Dragon newDragon) {
    if (newDragon == null)
      throw new IllegalArgumentException("Dragon is null");

    if (!this.iDs.contains(newDragon.getId()))
      throw new IllegalArgumentException("ID not found");

    Dragon old = findById(id);
    if (old == null)
      return false;

    collection.remove(old);
    collection.add(newDragon);
    return true;
  }

  public boolean remove(long id) {
    if (!this.iDs.contains(id))
      throw new IllegalArgumentException("ID not found");
    Dragon d = findById(id);
    iDs.remove(id);
    return d != null && collection.remove(d);
  }

  public void clear() {
    this.iDs.clear();
    collection.clear();
  }

  public String info() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("Type: ").append(collection.getClass().getName()).append("\n");
    strBuilder.append("Init time: ").append(this.creationDate).append("\n");
    strBuilder.append("Size: ").append(collection.size()).append("\n");
    strBuilder.append("File: ").append(this.file.getPath()).append("\n");
    if (!collection.isEmpty()) {
      strBuilder.append("Min element (by wingspan and id):\n").append(collection.peek().toString()).append("\n");
      strBuilder.append("Max element (by wingspan and id):\n").append(getMax().toString()).append("\n");
    }
    return strBuilder.toString();
  }

  public String showAscending() {
    PriorityQueue<Dragon> copy = new PriorityQueue<>(collection);
    StringBuilder strBuilder = new StringBuilder();
    while (!copy.isEmpty()) {
      strBuilder.append(copy.poll()).append("\n");
    }
    return strBuilder.toString();
  }

  public Dragon getMax() {
    Dragon max = null;
    for (Dragon dragon : collection) {
      if (max == null || dragon.compareTo(max) > 0) {
        max = dragon;
      }
    }
    return max;
  }

  public Dragon getMin() {
    return collection.peek();
  }

  public int nGreaterThanCave(Double treasures) {
    int cnt = 0;
    for (Dragon dragon : collection) {
      if (dragon.getCave() != null && dragon.getCave().getNumberOfTreasures().doubleValue() > treasures)
        cnt++;
    }
    return cnt;
  }

  public String getDecreasing() {
    StringBuilder stringBuilder = new StringBuilder();
    PriorityQueue<Dragon> copy = new PriorityQueue<>(Comparator.reverseOrder());
    copy.addAll(collection);
    while (!copy.isEmpty())
      stringBuilder.append(copy.poll()).append("\n");
    return stringBuilder.toString();
  }

  public ArrayList<DragonCharacter> getCharacters() {
    ArrayList<DragonCharacter> list = new ArrayList<>();

    for (Dragon dragon : collection) {
      list.add(dragon.getCharacter());
    }

    return list;
  }

  @Override
  public String toString() {
    String str = "";

    for (Dragon dragon : collection) {
      str += dragon.toString() + "\n";
    }

    return str;
  }
}
