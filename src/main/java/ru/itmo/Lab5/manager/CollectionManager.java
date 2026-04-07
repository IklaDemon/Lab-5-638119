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
 * Manages the collection of dragons stored in a priority queue.
 *
 * <p>
 * This class is responsible for loading and saving the collection,
 * tracking used identifiers, and providing utility operations for
 * adding, updating, removing and querying dragons.
 */
public class CollectionManager {
  private Reader reader;
  private Writer writer;
  private PriorityQueue<Dragon> collection;
  private Set<Long> iDs;
  private Date creationDate;
  private File file;

  /**
   * Creates a collection manager and initializes the collection from an external
   * source.
   *
   * @param reader   reader used to load dragons
   * @param writer   writer used to save dragons
   * @param filePath path to the data file
   * @throws NullPointerException     if {@code reader}, {@code writer}, or
   *                                  {@code filePath} is null
   * @throws IllegalArgumentException if the file path is invalid
   */
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

  /**
   * Sets and validates the file path used by the manager.
   *
   * @param filePath path to the data file
   * @throws NullPointerException     if {@code filePath} is null
   * @throws IllegalArgumentException if {@code filePath} is empty, does not
   *                                  exist,
   *                                  or refers to a directory
   */
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
   * Loads the collection and identifier set from the external source.
   */
  private void importFromExternalSource() {
    this.collection = reader.read();
    iDs = reader.getIDs();
  }

  /**
   * Saves the current collection to the external source.
   *
   * @return {@code true} if the collection was saved successfully, {@code false}
   *         otherwise
   */
  public boolean exportToExternalSource() {
    return this.writer.write(this.collection);
  }

  /**
   * Generates a new unique identifier for a dragon.
   *
   * @return new unique id
   * @throws IllegalArgumentException if the id set is not initialized
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
   * Returns the current date.
   *
   * @return current date
   */
  public Date genNewDate() {
    Date now = new Date();
    return now;
  }

  /**
   * Finds a dragon by its identifier.
   *
   * @param id dragon id
   * @return found dragon, or {@code null} if no dragon with this id exists
   */
  public Dragon findById(long id) {
    for (Dragon d : collection) {
      if (d.getId() == id) {
        return d;
      }
    }
    return null;
  }

  /**
   * Adds a dragon to the collection.
   *
   * @param dragon dragon to add
   * @throws IllegalArgumentException if {@code dragon} is null or its id already
   *                                  exists
   */
  public void addDragon(Dragon dragon) {
    if (dragon == null)
      throw new IllegalArgumentException("Dragon is null");
    if (this.iDs.add(dragon.getId()) == false)
      throw new IllegalArgumentException("Duplicated ID");
    this.collection.add(dragon);
  }

  /**
   * Updates the dragon with the specified id.
   *
   * @param id        id of the dragon to replace
   * @param newDragon new dragon value
   * @return {@code true} if the dragon was updated, {@code false} if no dragon
   *         with the given id was found
   * @throws IllegalArgumentException if {@code newDragon} is null or its id does
   *                                  not exist
   */
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

  /**
   * Removes the dragon with the specified id from the collection.
   *
   * @param id id of the dragon to remove
   * @return {@code true} if the dragon was removed, {@code false} otherwise
   * @throws IllegalArgumentException if the id does not exist
   */
  public boolean remove(long id) {
    if (!this.iDs.contains(id))
      throw new IllegalArgumentException("ID not found");
    Dragon d = findById(id);
    iDs.remove(id);
    return d != null && collection.remove(d);
  }

  /**
   * Clears the collection and the set of used identifiers.
   */
  public void clear() {
    this.iDs.clear();
    collection.clear();
  }

  /**
   * Returns information about the collection.
   *
   * <p>
   * The result includes collection type, initialization date,
   * current size, file path, and, if available, the minimum and maximum elements.
   *
   * @return formatted information string
   */
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

  /**
   * Returns all dragons in ascending order.
   *
   * @return string representation of the collection in ascending order
   */
  public String showAscending() {
    PriorityQueue<Dragon> copy = new PriorityQueue<>(collection);
    StringBuilder strBuilder = new StringBuilder();
    while (!copy.isEmpty()) {
      strBuilder.append(copy.poll()).append("\n");
    }
    return strBuilder.toString();
  }

  /**
   * Returns the maximum dragon in the collection.
   *
   * @return maximum dragon, or {@code null} if the collection is empty
   */
  public Dragon getMax() {
    Dragon max = null;
    for (Dragon dragon : collection) {
      if (max == null || dragon.compareTo(max) > 0) {
        max = dragon;
      }
    }
    return max;
  }

  /**
   * Returns the minimum dragon in the collection.
   *
   * @return minimum dragon, or {@code null} if the collection is empty
   */
  public Dragon getMin() {
    return collection.peek();
  }

  /**
   * Counts dragons whose cave contains more treasures than the specified value.
   *
   * @param treasures number of treasures to compare against
   * @return number of matching dragons
   */
  public int nGreaterThanCave(Double treasures) {
    int cnt = 0;
    for (Dragon dragon : collection) {
      if (dragon.getCave() != null && dragon.getCave().getNumberOfTreasures().doubleValue() > treasures)
        cnt++;
    }
    return cnt;
  }

  /**
   * Returns all dragons in descending order.
   *
   * @return string representation of the collection in descending order
   */
  public String getDecreasing() {
    StringBuilder stringBuilder = new StringBuilder();
    PriorityQueue<Dragon> copy = new PriorityQueue<>(Comparator.reverseOrder());
    copy.addAll(collection);
    while (!copy.isEmpty())
      stringBuilder.append(copy.poll()).append("\n");
    return stringBuilder.toString();
  }

  /**
   * Returns the list of dragon characters contained in the collection.
   *
   * @return list of dragon characters
   */
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
