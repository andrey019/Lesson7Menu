import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBHandler dbHandler = new DBHandler();
        MainMenu mainMenu = new MainMenu(dbHandler, scanner);
        mainMenu.start();
    }
}
