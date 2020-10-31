import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class bmp_io_ue6 {
	
	public static void main(String[] args) throws IOException {
		String inFilename = null;
		String inFilename2 = null;
		
		PixelColor pc = null;
		PixelColor pc_f = null;
		
		BmpImage bmp = null;
		BmpImage bmp_f = null;
		
		String outFilename = null;
		OutputStream out = null;
		
		if(args.length < 3) {
			System.out.println("At least three filename specified  (" + args.length + ")");
			System.exit(0);
		}
					
		inFilename = args[0];
		InputStream in = new FileInputStream(inFilename);
		bmp = BmpReader.read_bmp(in);
		
		inFilename2 = args[1];
		InputStream in2 = new FileInputStream(inFilename2);
		bmp_f = BmpReader.read_bmp(in2);
		
		outFilename = args[2];
		out = new FileOutputStream(outFilename);
		
		// filter
		for(int y = 1; y < bmp.image.getHeight()-1; y++) {
			for(int x = 1;x < bmp.image.getWidth()-1; x++) {
				
				
			}
		}

		
		try {
			BmpWriter.write_bmp(out,bmp_f);
		} finally {
			out.close();
		}
	}
}
