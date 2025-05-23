package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    
    private static final String
        db_name = "db0321",
        db_password = "",
        db_user = "root",
        db_port = "3306",
        db_host = "localhost";
    public static final String[] db_inventory_columns = 
        {"item_id","barcode","item_name","item_type","descr","location","price","stock"}
    ;
      
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name,
            db_user,
            db_password
        );
    }


    public static ArrayList<ArrayList<String>> getInventoryTable() {
        return DB.getTable("SELECT * FROM inventory", db_inventory_columns.length);
    }


    public static ArrayList<ArrayList<String>> getSearchSortedInventoryTable(String keyword,int column_index, String order) {
        return DB.getTable(
            "SELECT * FROM inventory WHERE "
            +"CAST(item_id AS CHAR) LIKE '%" + keyword
            +"%' OR barcode LIKE '%" + keyword
            +"%' OR item_name LIKE '%" + keyword
            +"%' OR item_type LIKE '%" + keyword
            +"%' OR descr LIKE '%" + keyword
            +"%' OR location LIKE '%" + keyword
            +"%' OR CAST(price AS CHAR) LIKE '%" + keyword
            +"%' OR CAST(stock AS CHAR) LIKE '%" + keyword
            +"%' ORDER BY " + db_inventory_columns[column_index] + " " + order,
            db_inventory_columns.length
        );
    }


    public static ArrayList<ArrayList<String>> getTable(String query, int columns) {
        ArrayList<ArrayList<String>> table = new ArrayList<>();
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ) 
        {
            while (rs.next()) {
                ArrayList<String> new_row = new ArrayList<>();
                for (int i = 1; i <= columns; i++) {
                    new_row.add(rs.getString(i));
                }
                table.add(new_row);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return table;
    }


    public static boolean findBarcode(String barcode) {
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM inventory WHERE barcode = '" + barcode + "';");
            ) 
        {
            return rs.next();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static boolean addStock(String id, String new_stock) {
        String edit_query = "UPDATE inventory SET stock = stock + ? WHERE item_id = " + id + ";";
        try (
            Connection conn = DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(edit_query) 
            ) 
        {
            stmt.setInt(1, Main.toInteger(new_stock));
            if (stmt.executeUpdate() > 0) return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }


    public static boolean checkStock(String item_id, String stock) {
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 FROM inventory WHERE item_id = " + item_id + " AND stock >= "+ stock +";");
            ) 
        {
            return rs.next();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }   


    public static int insertNewSale(String[] sale) {
        String insertSql = """
        INSERT INTO sales (customer_name, employee_name, total_price, payment_method) 
        VALUES (?, ?, ?, ?);
        """;
        try (
            Connection conn = DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {

            for (int i = 0; i < sale.length; i++) {
                stmt.setString(i+1, sale[i]);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return -1;
    }


    public static void insertSaleItems(String sale_id, String item_id, String price, String quantity) {
        String insertSql = """
        INSERT INTO sale_items (sale_id, item_id, price_sold, quantity) 
        VALUES (?, ?, ?, ?);
        """;
        try (
            Connection conn = DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)
            ) {
                
            stmt.setString(1, sale_id);
            stmt.setString(2, item_id);
            stmt.setString(3, price);
            stmt.setString(4, quantity);

            stmt.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public static ArrayList<String> getItem(String barcode) {
        ArrayList<String> item = new ArrayList<>();
        try (
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventory WHERE barcode = '"+barcode+"';");
            ) 
        {
            if (rs.next()) {
                item.add(rs.getString(1));
                item.add(rs.getString(3));
                item.add(rs.getString(4));
                item.add(rs.getString(7));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return item;
    }
}
