package celestino;

import celestino.inventory.InventoryMain;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static final String initial_card = "add item",
        db_name = "db0321",
        db_password = "",
        db_user = "root",
        db_port = "3306",
        db_host = "localhost"
    ;
    public static final Color theme[] = {
        new Color(255, 255, 255), // light color
        new Color(252, 151, 51), // medium
        new Color(1, 69, 24)  // dark
    };
    
    
    public static Connection db_connection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name,
            db_user,
            db_password
        );
    }


    public static boolean popup_confirm(String message) {
        int choice = JOptionPane.showOptionDialog(
            window, message, "Confirmation", 0, JOptionPane.QUESTION_MESSAGE, null, null, 1
        );
        return choice == 0;
    }


    public static int popup_option(String message, String[] options) {
        int choice = JOptionPane.showOptionDialog(
            window, message, "Choose", 0, JOptionPane.QUESTION_MESSAGE, null, options, 1
        );
        return choice;
    }


    public static void popup_error(String message) {
        JOptionPane.showMessageDialog(window, message, "Error Message", JOptionPane.ERROR_MESSAGE);
    }


    public static void popup_message(String message) {
        JOptionPane.showMessageDialog(window, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }


    public static String popup_input(String message) {
        return JOptionPane.showInputDialog(message);
    }


    public static void add_card(JPanel card, String card_name) {
        main_panel.add(card, card_name);
    }
    
    
    public static void change_card(String card_name) {
        card_layout.show(main_panel, card_name);
    }


    public static Font get_font(int size) {
        return new Font(default_font, Font.BOLD, size);
    }


    public static Integer to_integer(String text) {
        try {
            return Integer.parseInt(text);
        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("\n" + text + " cannot be an Integer");
            return null;
        }
    }
    

    public static Double to_double(String text) {
        try {
            return Double.parseDouble(text);
        } 
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("\n" + text + " cannot be a Double");
            return null;
        }
    }
    

    private static final String default_font = "Segoe UI";
    private static final JFrame window = new JFrame();
    private static final CardLayout card_layout = new CardLayout();
    private static final JPanel main_panel = new JPanel(card_layout); 


    private static void initiliaze_cards() {
        new InventoryMain();
        /*  public InventoryMain() {
                Main.add_card(inventory_card, "inventory");
                Main.add_card(add_item_card, "add item");
            }
        */
    }
    
    
    private static void setup_window() {
        window.setSize(880, 660);
        window.setTitle("AtlasInventory");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);
        window.add(main_panel);
    }


    public static void main(String[] args) throws Exception {
        setup_window();
        initiliaze_cards();
        change_card(initial_card);
        window.setVisible(true);

        try(Connection conn = db_connection()) {
        } catch (SQLException e) {
            e.printStackTrace();
            popup_error(e.getMessage());
            System.exit(0);
        }
    }
}