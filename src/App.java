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

			// スキーマを作成
	        xepPersister.deleteExtent("User.Person");
	        xepPersister.importSchema("User.Person");
	       
	        Person p = new Person();
	        p.id=BigDecimal.valueOf(123);
	        p.name="テスト";

	        // Eventを作成
			Event xepEvent = xepPersister.getEvent("User.Person");
			
	        // Eventを保続
	        xepEvent.store(p);
			System.out.println("InterSystems IRISに保存しました。");
			
			// JDBC経由でアクセス
			System.out.println("========================");
	        PreparedStatement stmt = xepPersister.prepareStatement("select * from person");
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
