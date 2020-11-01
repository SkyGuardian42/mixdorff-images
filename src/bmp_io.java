import java.io.*;

public final class bmp_io {

    public static void main(String[] args) throws IOException {
        String inFilename = "manmade_04.bmp";
        String outFilename = "modified.bmp";
        PixelColor pc = null;
        BmpImage bmp = null;

        // Read file
        System.out.println("Datei: " + inFilename);
        InputStream in = new FileInputStream(inFilename);
        bmp = BmpReader.read_bmp(in);

//        PrintWriter writer = new PrintWriter("ascii-V11.txt", "UTF-8");

        // BGR schreiben horizontal 2.1.
        //        for (int x = 0; x < bmp.image.getWidth(); x++) {
        //            PixelColor px = bmp.image.getRgbPixel(x, 0);
        //            writer.println(px.r + " " + px.g + " " + px.b);
        //        }



        // BGR schreiben vertikal 2.1.
        //        for (int y = 0; y < bmp.image.getHeight(); y++) {
        //            PixelColor px = bmp.image.getRgbPixel(0, y);
        //            writer.println(px.r + " " + px.g + " " + px.b);
        //        }

        //        writer.close();

        //        if (args.length == 1)
        //            System.exit(0);


        // Implementierung bei Ein- und Ausgabeparamter

        //		outFilename = args[1];
        OutputStream out = new FileOutputStream(outFilename);

        // erzeuge graustufenbild
        //        for (int y = 0; y < bmp.image.getHeight(); y++) {
        //            for (int x = 0; x < bmp.image.getWidth(); x++) {
        //                PixelColor col = bmp.image.getRgbPixel(x,y);
        //                int y_val = (int) (col.r * 0.3 + col.g * 0.6 + col.b * 0.1);
        //
        //                bmp.image.setRgbPixel(x,y, new PixelColor(y_val, y_val, y_val));
        //            }
        //        }

        // downsampling
        //        for (int y = 0; y < bmp.image.getHeight(); y++) {
        //            for (int x = 0; x < bmp.image.getWidth(); x++) {
        ////                if(y%10!=0) {
        ////                    bmp.image.setRgbPixel(x,y,bmp.image.getRgbPixel(x,y-1));
        ////                }
        //                if(x%11!=0) {
        //                    bmp.image.setRgbPixel(x,y,bmp.image.getRgbPixel(x-1,y));
        //                }
        //            }
        //        }

        // bitreduzierung

        int reduced_bits = 6;
//        for (int y = 0; y < bmp.image.getHeight(); y++) {
//            for (int x = 0; x < bmp.image.getWidth(); x++) {
//                PixelColor col = bmp.image.getRgbPixel(x,y);
//                int r = ((col.r>>reduced_bits)<<reduced_bits);
//                int g = ((col.g>>reduced_bits)<<reduced_bits);
//                int b = ((col.b>>reduced_bits)<<reduced_bits);
//
//                bmp.image.setRgbPixel(x,y, new PixelColor(r,g,b));
//            }
//        }

        // bitreduzierung differenz
//        int bitsPerColor = 8;
        reduced_bits = 4;
        for (int y = 0; y < bmp.image.getHeight(); y++) {
            for (int x = 0; x < bmp.image.getWidth(); x++) {
                PixelColor col = bmp.image.getRgbPixel(x,y);
                int r = ((col.r>>reduced_bits)<<reduced_bits);
                int g = ((col.g>>reduced_bits)<<reduced_bits);
                int b = ((col.b>>reduced_bits)<<reduced_bits);

                r = (col.r - r)<<reduced_bits;
                g = (col.g - g)<<reduced_bits;
                b = (col.b - b)<<reduced_bits;

                bmp.image.setRgbPixel(x,y, new PixelColor(r,g,b));
            }
        }

        try {
            BmpWriter.write_bmp(out, bmp);
        } finally {
            out.close();
        }
    }
}
