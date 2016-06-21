import java.util.List;
import java.util.Scanner;

public class MainMenu extends Thread {
    private DBHandler dbHandler;
    private Scanner scanner;

    public MainMenu(DBHandler dbHandler, Scanner scanner) {
        this.dbHandler = dbHandler;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        mainMenu();
    }

    private void mainMenu() {
        instructionsOnScreen();
        while (!isInterrupted()) {
            switch (scanner.nextLine()) {
                case "1":
                    add();
                    break;
                case "2":
                    delete();
                    break;
                case "3":
                    findByName();
                    break;
                case "4":
                    getByPriceRange();
                    break;
                case "5":
                    getWithDiscount();
                    break;
                case "6":
                    getAll();
                    break;
                case "7":
                    getRandomSet();
                    break;
                case "8":
                    System.exit(0);
                    break;
            }
        }
    }

    private String getStringInput() {
        String string = scanner.nextLine();
        while (string.isEmpty()) {
            System.out.println("Field can't be empty! Try again");
            string = scanner.nextLine();
        }
        return string;
    }

    private int getIntInput() {
        int integer = -1;
        while (integer == -1) {
            try {
                integer = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Wrong type! Try again");
            }
        }
        return integer;
    }

    private double getDoubleInput() {
        double number = -1;
        while (number == -1) {
            try {
                number = Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Wrong type! Try again");
            }
        }
        return number;
    }

    private static void instructionsOnScreen() {
        System.out.println("1) Add dish");
        System.out.println("2) Delete dish by id");
        System.out.println("3) Find dish by name");
        System.out.println("4) Show dishes with price range from-to");
        System.out.println("5) Show all dishes with discount");
        System.out.println("6) Show all dishes");
        System.out.println("7) Show random dishes set with total weight no more then");
        System.out.println("8) Exit");
        System.out.println();
    }

    private void add() {
        System.out.print("Type in dish name: ");
        String name = getStringInput();
        System.out.print("Type in dish price: ");
        double price = getDoubleInput();
        System.out.print("Type in dish weight in grams: ");
        int weight = getIntInput();
        System.out.print("Type in dish dicount (may be zero): ");
        int discount = getIntInput();
        Menu menu = new Menu(name, price, weight, discount);
        if (dbHandler.addEntityToDB(menu)) {
            System.out.println("Added successfully!\r\n");
        } else {
            System.out.println("Internal error!\r\n");
        }
    }

    private void delete() {
        System.out.print("Type in dish Id: ");
        int id = getIntInput();
        Menu menu = (Menu) dbHandler.getEntityById(Menu.class, id);
        if (menu == null) {
            System.out.println("No dish with this id!\r\n");
            return;
        } else {
            if (dbHandler.deleteEntityFromBD(menu)) {
                System.out.println("Deleted successfully!\r\n");
            } else {
                System.out.println("Internal error!\r\n");
            }
        }
    }

    private void findByName() {
        System.out.print("Type in dish name: ");
        String name = getStringInput();
        List<Menu> menus = dbHandler.getEntityByName(Menu.class, name);
        if ( (menus == null) || (menus.isEmpty()) ) {
            System.out.println("No dishes with such name!");
        } else {
            for (Menu menu : menus) {
                System.out.println(menu);
            }
        }
        System.out.println();
    }

    private void getByPriceRange() {
        System.out.print("Type in minimum price: ");
        double min = getDoubleInput();
        System.out.print("Type in maximum price: ");
        double max = getDoubleInput();
        List<Menu> menus = dbHandler.getEntitiesByPrice(Menu.class, min, max);
        if ( (menus == null) || (menus.isEmpty()) ) {
            System.out.println("No dishes in this price range!");
        } else {
            for (Menu menu : menus) {
                System.out.println(menu);
            }
        }
        System.out.println();
    }

    private void getWithDiscount() {
        List<Menu> menus = dbHandler.getEntitiesWithDiscount(Menu.class);
        if ( (menus == null) || (menus.isEmpty()) ) {
            System.out.println("No dishes with discount was found!");
        } else {
            System.out.println("Dishes with discount: ");
            for (Menu menu : menus) {
                System.out.println(menu);
            }
        }
        System.out.println();
    }

    private void getAll() {
        List<Menu> menus = dbHandler.getAllEntities(Menu.class);
        if ( (menus == null) || (menus.isEmpty()) ) {
            System.out.println("No dishes in database!");
        } else {
            System.out.println("List of all dishes: ");
            for (Menu menu : menus) {
                System.out.println(menu);
            }
        }
        System.out.println();
    }

    private void getRandomSet() {
        System.out.print("Type in maximum total weight: ");
        int maxWeight = getIntInput();
        List<Menu> menus = dbHandler.randomEntitiesByWeight(Menu.class, maxWeight);
        if ( (menus == null) || (menus.isEmpty()) ) {
            System.out.println("No dishes below this weight!");
        } else {
            System.out.println("Your set of dishes: ");
            for (Menu menu : menus) {
                System.out.println(menu);
            }
        }
        System.out.println();
    }
}
