import java.nio.ByteBuffer;

import org.junit.Test;

import com.qi.chat.common.math.NumberToBinary;
import com.qi.chat.common.net.Head;
import com.qi.chat.common.net.HeadParser;

public class ParserTest {
	@Test
	public void test() {
		byte[] bytes = new byte[]{1,2,3,4,5,6,7,8,9,0};
		ByteBuffer allocate = ByteBuffer.allocate(12);
		allocate.put(bytes);
		byte[] nb = new byte[7];
		allocate.get(nb, 3, 7);
		allocate.flip();
		allocate.clear();
		byte[] array = allocate.array();
		allocate.position(3);
		allocate.clear();
	}
}
