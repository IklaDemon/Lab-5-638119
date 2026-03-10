// implement a builder pattern for Dragon class - done
//
// check for CRUD pattern. related to CollectionManager - kind of done
//
// the methods related to the commands should be under a common interface
//       Public String describe()... Maybe I should do a class for every command? There's no difference I guess
//       and every command class should have a describe method... - kind of done
//
// Il prodotto finale dovrebbe essere come il jar finale del Lab pokemon che abbiamo importato
//
// nel README.md dovrebbero essere presenti le indicazioni per: versions, compile, install, clean
// non ho ben capito cosa dovrebbe essere install
//
// For the "exit" command it's possible to use just System.exit(), But if modifications were made, probably is also better to ask for confirmation to the user
//
// I should use a proper XML parser.
// 
// NOTA: The commands should be adapted to support TCP/IP in the future
//
// The teacher mentioned the "Java stram API" and the filter method, I should check that.
// -> found some resources: https://www.geeksforgeeks.org/java/stream-filter-java-examples/
//
// Looks like the hardest command is the scrpit one, well, its the most interesting one also.
// If all exceptions are correctly managed, then it should not be hard to implement it.
// Good insight: if in the script is present "execute_script file_name" then my program should not do it, for obvious reasons...

package ru.itmo.Lab5.dragon;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.itmo.Lab5.enums.DragonCharacter;
import ru.itmo.Lab5.enums.DragonType;

/**
 * Represents a Dragon object stored in the collection.
 */
public class Dragon implements Comparable<Dragon> {
  private long id; // The value of this field must be greater than 0, The value of this field must
                   // be unique, The value of this field must be generated automatically
  private String name; // Field cannot be null, String cannot be empty
  private Coordinates coordinates; // The field cannot be null
  private Date creationDate; // The field cannot be null, The value of this field must be generated
                             // automatically
  private Integer age; // The field value must be greater than 0. The field can be null.
  private double wingspan; // The field value must be greater than 0
  private DragonType type; // The field may be null
  private DragonCharacter character; // The field may be null
  private DragonCave cave; // The field may be null

  public Dragon() {
    id = 0;
    name = null;
    coordinates = null;
    creationDate = null;
    age = null;
    wingspan = 0;
    type = null;
    character = null;
    cave = null;
  }

  public Dragon(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.coordinates = builder.coordinates;
    this.creationDate = builder.creationDate;
    this.age = builder.age;
    this.wingspan = builder.wingspan;
    this.type = builder.type;
    this.character = builder.character;
    this.cave = builder.cave;
  }

  // --- BUILDER ---

  public static class Builder {
    private long id;
    private String name;
    private Coordinates coordinates;
    private Date creationDate;
    private Integer age;
    private double wingspan;
    private DragonType type;
    private DragonCharacter character;
    private DragonCave cave;

    public Builder id(long id) {
      if (id <= 0) {
        throw new IllegalArgumentException("id = " + id + ". Shoul be > 0");
      }
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      if (name == null) {
        throw new NullPointerException("name = null. Should not be null");
      }
      if (name.equals("")) {
        throw new IllegalArgumentException("name = ''. Should not be empty");
      }
      this.name = name;
      return this;
    }

    public Builder coordinates(Coordinates coordinates) {
      if (coordinates == null) {
        throw new NullPointerException("coordinates = null. Should not be null");
      }
      this.coordinates = coordinates;
      return this;
    }

    public Builder creationDate(Date creationDate) {
      if (creationDate == null) {
        throw new NullPointerException("creationDate = null. Should not be null");
      }
      this.creationDate = creationDate;
      return this;
    }

    public Builder age(Integer age) {
      if (age == null) {
        this.age = null;
      } else if (age.intValue() <= 0) {
        throw new IllegalArgumentException("age = " + age + ". Should be > 0. Can be null");
      } else {
        this.age = age;
      }
      return this;
    }

    public Builder wingspan(double wingspan) {
      if (wingspan <= 0.0) {
        throw new IllegalArgumentException("wingspan = " + wingspan + ". Should be > 0");
      }
      this.wingspan = wingspan;
      return this;
    }

    public Builder type(String type) {
      if (type == null) {
        this.type = null;
      } else {
        try {
          this.type = DragonType.valueOf(type);
        } catch (IllegalArgumentException e) {
          throw e;
        }
      }
      return this;
    }

    public Builder character(String character) {
      if (character == null) {
        this.character = null;
      } else {
        try {
          this.character = DragonCharacter.valueOf(character);
        } catch (IllegalArgumentException e) {
          throw e;
        }
      }
      return this;
    }

    public Builder cave(DragonCave cave) {
      this.cave = cave;
      return this;
    }

    public Dragon build() {
      Dragon d = new Dragon();
      d.setId(this.id);
      d.setName(this.name);
      d.setCoordinates(this.coordinates);
      d.setCreationDate(this.creationDate);
      d.setAge(this.age);
      d.setWingspan(this.wingspan);
      d.setType(this.type == null ? null : this.type.name());
      d.setCharacter(this.character == null ? null : this.character.name());
      d.setCave(this.cave);
      return d;
    }
  }

  // SETTER

  public void setId(long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("id = " + id + ". Shoul be > 0");
    }
    this.id = id;
  }

  public void setName(String name) {
    if (name == null) {
      throw new NullPointerException("name = null. Should not be null");
    }
    if (name.equals("")) {
      throw new IllegalArgumentException("name = ''. Should not be empty");
    }
    this.name = name;
  }

  public void setCoordinates(Coordinates coordinates) {
    if (coordinates == null) {
      throw new NullPointerException("coordinates = null. Should not be null");
    }
    this.coordinates = coordinates;
  }

  public void setCreationDate(Date creationDate) {
    if (creationDate == null) {
      throw new NullPointerException("creationDate = null. Should not be null");
    }
    this.creationDate = creationDate;
  }

  public void setAge(Integer age) {
    if (age == null) {
      this.age = age;
    } else if (age.intValue() <= 0) {
      throw new IllegalArgumentException("age = " + age + ". Should be > 0. Can be null");
    } else {
      this.age = age;
    }
  }

  public void setWingspan(double wingspan) {
    if (wingspan <= 0.0) {
      throw new IllegalArgumentException("wingspan = " + wingspan + ". Should be > 0");
    }
    this.wingspan = wingspan;
  }

  public void setType(String type) {
    if (type == null) {
      this.type = null;
    } else {
      try {
        this.type = DragonType.valueOf(type);
      } catch (IllegalArgumentException e) {
        throw e;
      }
    }
  }

  public void setCharacter(String character) {
    if (character == null) {
      this.character = null;
    } else {
      try {
        this.character = DragonCharacter.valueOf(character);
      } catch (IllegalArgumentException e) {
        throw e;
      }
    }
  }

  public void setCave(DragonCave cave) {
    this.cave = cave;
  }

  // GETTER

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public Integer getAge() {
    return age;
  }

  public double getWingspan() {
    return wingspan;
  }

  public DragonType getType() {
    return type;
  }

  public DragonCharacter getCharacter() {
    return character;
  }

  public DragonCave getCave() {
    return cave;
  }

  @Override
  public int compareTo(Dragon other) {
    int byWingspan = Double.compare(this.wingspan, other.wingspan);
    if (byWingspan != 0) {
      return byWingspan;
    }
    return Long.compare(this.id, other.id);
  }

  @Override
  public String toString() {
    String age;
    String type;
    String character;
    String cave;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String creatioDate = formatter.format(this.creationDate);

    if (this.age == null)
      age = "null";
    else
      age = this.age.toString();

    if (this.type == null)
      type = "null";
    else
      type = this.type.toString();

    if (this.character == null)
      character = "null";
    else
      character = this.character.toString();

    if (this.cave == null)
      cave = "null";
    else
      cave = this.cave.toString();

    return String
        .format(
            "ID: %,d, name: %s, coordinates: %s, creation date: %s, age: %s, wingspan: %f, type: %s, character: %s, cave: %s",
            id, name, coordinates.toString(), creatioDate, age, wingspan, type.toString(),
            character.toString(),
            cave);
  }
}
