public class Main {
    public static void main(String[] args) {
        Translator translator = new Translator();
        Menu menu = new Menu(translator);
        menu.execute();
    }
}
