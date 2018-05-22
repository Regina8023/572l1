import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Hctry {

	public static void main(String[] args) throws IOException {

		FileInputStream in = null;
		try {
			in = new FileInputStream("/Users/haoxin/Documents/JI/VE572/l1/data_1.bin");
			DataInputStream din = new DataInputStream(in);

			din.skipBytes(0);
			for (int i = 0; i < 3001; i++) {
				double length = din.readDouble();
				System.out.println(length);
			}
			din.close();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}