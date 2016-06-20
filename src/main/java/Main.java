
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setName("bulka");
        menu.setPrice(15.45);
        menu.setWeight(157);
        Object object = menu;
        System.out.println();

        DBHandler dbHandler = new DBHandler();
        dbHandler.addEntityToDB(menu);
        System.exit(0);

        Class objClass = menu.getClass();

    }
}
