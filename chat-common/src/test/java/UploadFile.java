import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class UploadFile {
	@Test
	public void uploadFile() {
		File file = new File("C:\\Users\\Administrator\\Pictures/skitch.png");
		try {
			FileInputStream is = new FileInputStream(file);
			FileOutputStream os = new FileOutputStream("D:/rxjava.png");
			int len = -1;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				os.write(b, 0, len);
				os.flush();
			}
			is.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
