
import java.net.FileNameMap;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import orm.Employees;
import orm.InternalCommunication;
import orm.Users;
import util.HibernateUtil;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dhirajj
 */
public class testChar {

    public testChar() {
    }

    public static void main(String args[]) throws SocketException { 
        
        
        String str = "Employee Doc.pdf";
        
        System.out.println(str.replaceAll(" ", "_"));
        
        char c = 0;
        

//        testChar t1 = new testChar();
//        InternalCommunication selectedInterCommFrom;
//        InternalCommunication toaddIntrcomm;
//        Employees from;
//        Employees to;
//        List<Users> allUsers;
//        List<InternalCommunication> allInterCommTo;
//
//
//        selectedInterCommFrom = new InternalCommunication();
//        toaddIntrcomm = new InternalCommunication();
//        allInterCommTo = new ArrayList<InternalCommunication>();
//        allUsers = new ArrayList<Users>();
//        from = new Employees();
//        to = new Employees();
//
//
//        Session session = HibernateUtil.getSession();
//        Transaction trx = session.beginTransaction();
//
//        Query q = null;
//
//        q = session.createQuery("from Users where id != 1");
//        allUsers.addAll(q.list());
//
//        from.setId(1);
//        selectedInterCommFrom.setRequestFrom(from);
//
//        for (int i = 0; i < allUsers.size(); i++) {
//            Session session1 = HibernateUtil.getSession();
//            Transaction trx1 = session1.beginTransaction();
//            Date date = new Date();
//
//            to.setId(allUsers.get(i).getEmployees().getId());
//
//
//            toaddIntrcomm.setRequestTo(to);
//            toaddIntrcomm.setRequestFrom(from);
//            toaddIntrcomm.setCreatedby(1);
//            toaddIntrcomm.setCreatedon(new Timestamp(date.getTime()));
//            toaddIntrcomm.setMessagetype('R');
//            toaddIntrcomm.setDetails("kjdsfghkdsljhg");
//            toaddIntrcomm.setStatus('O');
//            toaddIntrcomm.setRequestDate(date);
//            toaddIntrcomm.setSubject("kadshjgflkjdshfkjhlkljhkjhkjhkljjk");
//
//
//            session1.save(toaddIntrcomm);
//            trx1.commit();
//
//            toaddIntrcomm = null;
//            toaddIntrcomm = new InternalCommunication();
//            to = null;
//            to = new Employees();
//
//        }

        if('\0'== c){
        c=' ';
        System.out.println("this is"+c+"same charecter.");
    }

    }
}
