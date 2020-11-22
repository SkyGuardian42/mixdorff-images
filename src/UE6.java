import java.io.*;

public class UE6 {
    public static void main(String[] args) throws IOException{
        String inFilename = "nature_04_y.bmp";
        String outFilename = "modified.bmp";

        // Read file
        System.out.println("Datei: " + inFilename);
        InputStream in = new FileInputStream(inFilename);
        BmpImage bmp = BmpReader.read_bmp(in);

        OutputStream out = new FileOutputStream(outFilename);

        // store pixel frequency in array, index = color, value = frequency
        int[] pixelColors = new int[256];

        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                PixelColor col = bmp.image.getRgbPixel(x,y);
                int val = (int)(col.r * -1);
                val = (int)(col.r * -1)+255;
                int valClamped = Math.max(0, Math.min(255, val));
                bmp.image.setRgbPixel(x,y, new PixelColor(valClamped, valClamped, valClamped));
                pixelColors[valClamped]++;
            }
        }

        // write to file, newline separated, color value = row number
        PrintWriter writer = new PrintWriter("nature_04_mal_10_0.txt", "UTF-8");
        for(int i : pixelColors) {
            writer.println(i);
        }
        writer.close();

        try {
            BmpWriter.write_bmp(out, bmp);
        } finally {
            out.close();
        }
    }

}
