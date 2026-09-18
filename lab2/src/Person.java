import java.util.Objects;

public final class Person {
    final String name;
    final String surname;
    final int age;

    Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Person other = (Person) obj;

        return age == other.age &&
                Objects.equals(name, other.name) &&
                Objects.equals(surname, other.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, surname);
    }
}
