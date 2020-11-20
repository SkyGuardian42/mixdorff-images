import java.io.*;

public class UE6 {
    public static void main(String[] args) throws IOException{
        String inFilename = "nature_04.bmp";
        String outFilename = "modified.bmp";

        // Read file
        System.out.println("Datei: " + inFilename);
        InputStream in = new FileInputStream(inFilename);
        BmpImage bmp = BmpReader.read_bmp(in);

        OutputStream out = new FileOutputStream(outFilename);

        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                PixelColor col = bmp.image.getRgbPixel(x,y);
//                bmp.image.setRgbPixel(x,y, new PixelColor(0, 0, col.b));
                double yy = (0.299 * col.r) + (0.587 * col.g) + (0.114 * col.b);
                double cb = ((-0.169 * col.r) + (-0.331 * col.g) + (0.5 * col.b)) + 128;
                double cr = ((0.5 * col.r) + (-0.419 * col.g) + (-0.081 * col.b)) + 128;


                bmp.image.setRgbPixel(x,y, new PixelColor((int) cb, (int) cb, (int) cb));


//                System.out.println(yy + " " + cb + " " + cr);
            }
        }


        try {
            BmpWriter.write_bmp(out, bmp);
        } finally {
            out.close();
        }
    }

}
