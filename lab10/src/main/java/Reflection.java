import java.lang.reflect.Field;
import java.util.Scanner;

public class Reflection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String literalStr = "Literal string";

        System.out.print("Enter string: ");
        String inputStr = sc.nextLine();

        System.out.println("\nBefore changes:");
        System.out.println("Literal: " + literalStr);
        System.out.println("Input: " + inputStr);

        String hackedStr = "Hacked string";

        try {
            Class<String> stringClass = String.class;

            Field valueField = stringClass.getDeclaredField("value");
            valueField.setAccessible(true);

            Field coderField = stringClass.getDeclaredField("coder");
            coderField.setAccessible(true);

            byte[] newBytes = (byte[]) valueField.get(hackedStr);
            byte newCoder = (byte) coderField.get(hackedStr);

            valueField.set(literalStr, newBytes);
            coderField.set(literalStr, newCoder);

            valueField.set(inputStr, newBytes);
            coderField.set(inputStr, newCoder);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nAfter changes:");
        System.out.println("Literal: " + literalStr);
        System.out.println("Input: " + inputStr);

        sc.close();
    }
}
