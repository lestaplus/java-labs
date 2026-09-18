import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Anatolii", "Shevtsov", 19);

        Gson gson = new Gson();
        String json = gson.toJson(person);

        Person convertedPerson = gson.fromJson(json, Person.class);

        boolean isEqual = person.equals(convertedPerson);
        System.out.print(isEqual);
    }
}