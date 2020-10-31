import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class bmp_io {
	
	public static void main(String[] args) throws IOException {
		String inFilename = null;
		String outFilename = null;
		PixelColor pc = null;
		BmpImage bmp = null;
		
		if (args.length < 1) 
			System.out.println("At least one filename specified  (" + args.length + ")"); 
		
		// Zugriff auf Pixel mit bmp.image.getRgbPixel(x, y);		

		// Implementierung bei einem Eingabeparamter
		
		inFilename = args[0];
		InputStream in = new FileInputStream(inFilename);
		bmp = BmpReader.read_bmp(in);
		
		// BGR schreiben horizontal 2.1.	
    		for(int x = 0; x < bmp.image.getWidth(); x++) {
    	

    		// ********* ToDo ***************
		}

		// BGR schreiben vertikal 2.1.	
    		for(int y = 0; y < bmp.image.getHeight(); y++) {

    		// ********* ToDo ***************
    		
    		}

	    if (args.length == 1) 
			System.exit(0);

		
		// Implementierung bei Ein- und Ausgabeparamter

		outFilename = args[1];
		OutputStream out = new FileOutputStream(outFilename);
		
		// erzeuge graustufenbild
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0;x < bmp.image.getWidth(); x++) {
			
				// ********* ToDo ***************
				
			}
		}
		
		// downsampling
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0; x < bmp.image.getWidth(); x++) {
		
				// ********* ToDo ***************
				
			}
		}
		
		// bitreduzierung
		int reduced_bits = 1;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
		
				// ********* ToDo ***************
			
			}
		}
		
		// bitreduzierung differenz
		reduced_bits = 1;
		int bitsPerColor = 8;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {

				// ********* ToDo ***************
				
			}
		}
		
		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
