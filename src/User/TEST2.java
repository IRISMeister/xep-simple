package User;

import java.math.BigDecimal;
import com.intersystems.xep.annotations.Index;
import com.intersystems.xep.annotations.IndexType;

@Index(name="idx1",fields={"id"},type=IndexType.simple)
public class TEST2 {
	public BigDecimal id;
	public String name;
}
