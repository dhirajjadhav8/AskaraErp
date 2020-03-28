
import java.sql.*;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author dhirajj
 */
public class ProcForDerby123 {

    public static void selectRows(int p1, int p2, ResultSet[] data1,
            ResultSet[] data2) throws Exception {

        Class.forName("org.apache.derby.jdbc.ClientDriver");

        Connection conn = DriverManager.getConnection("jdbc:derby://192.168.1.104:1527/testing", "testing", "testing"); // get connection
        PreparedStatement ps1 = conn.prepareStatement("select * from EmpManageent.Employees where id = ?");
        ps1.setInt(1, p1);
        data1[0] = ps1.executeQuery();

        PreparedStatement ps2 = conn.prepareStatement("select * from EmpManageent.Employees where id = ?");
        ps2.setInt(1, p2);
        data2[0] = ps2.executeQuery();

        conn.close();
    }
}
