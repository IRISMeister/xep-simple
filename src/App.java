import User.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.intersystems.xep.*;

public class App {
    public static void main(String[] args) throws Exception {
	    try {
	        // Databaseに接続
	        EventPersister xepPersister = PersisterFactory.createPersister();
	        xepPersister.connect("localhost", 1972, "mydb", "superuser", "SYS");
			System.out.println("InterSystems IRISに接続しました。");

	        xepPersister.deleteExtent("User.TEST2");
	        xepPersister.importSchema("User.TEST2");
	       
	        // Eventを作成
	        Event xepEvent = xepPersister.getEvent("User.TEST2");
	        TEST2 o = new TEST2();
	        o.id=BigDecimal.valueOf(123);
	        o.name="テスト";

	        // Eventを保続
	        xepEvent.store(o);
			System.out.println("InterSystems IRISに保存しました。");
			
			// JDBC経由でアクセス
			System.out.println("========================");
	        PreparedStatement stmt = xepPersister.prepareStatement("select * from TEST2");
	        ResultSet rs = stmt.executeQuery();
			while (rs.next()) 
			{
				String n=rs.getString("name");
				System.out.println(rs.getBigDecimal("id") + " " + n);
	        }	        
	
	        xepEvent.close();
	        xepPersister.close();
		} catch (XEPException e) { 
			System.out.println("Interactive prompt failed:\n" + e); 
		}
	   } 
}
