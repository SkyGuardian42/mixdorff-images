import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class bmp_io_ue6 {
	
	public static void main(String[] args) throws IOException {
		String input = "manmade_04_y";
		String modified = input + "_mittel";
		String diffName = modified + "_diff";

		BmpImage bmp = BmpReader.read_bmp(new FileInputStream(input + ".bmp"));
		BmpImage bmp_f = bmp;
		BmpImage bmp_diff = bmp;

		OutputStream out = new FileOutputStream(modified + ".bmp");
		OutputStream diff = new FileOutputStream(diffName + ".bmp");

		// filter
		for(int y = 1; y < bmp.image.getHeight()-1; y++) {
			for(int x = 1;x < bmp.image.getWidth()-1; x++) {
				// Mittelwertfilter
				int added = 0;
				for(int i = -1; i<2; i++){
					for(int j = -1; j<2; j++){
						added += bmp.image.getRgbPixel(x+ i, y+j).r;
					}
				}
				added /= 9;
				bmp_f.image.setRgbPixel(x,y,new PixelColor(added, added, added));
			}
		}

		// differenzbild
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0; x < bmp.image.getHeight(); x++) {
				bmp_diff =
			}
		}
		
		try {
			BmpWriter.write_bmp(out,bmp_f);
			BmpWriter.write_bmp(diff,bmp_diff);
		} finally {
			out.close();
		}
	}
}
