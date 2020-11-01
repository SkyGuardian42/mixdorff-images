/* 
 * BMP I/O library (Java)
 * 
 * Copyright (c) Project Nayuki
 * https://www.nayuki.io/page/bmp-io-library-java
 * 
 * (MIT License)
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * - The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 * - The Software is provided "as is", without warranty of any kind, express or
 *   implied, including but not limited to the warranties of merchantability,
 *   fitness for a particular purpose and noninfringement. In no event shall the
 *   authors or copyright holders be liable for any claim, damages or other
 *   liability, whether in an action of contract, tort or otherwise, arising from,
 *   out of or in connection with the Software or the use or other dealings in the
 *   Software.
 */

import java.io.IOException;
import java.io.OutputStream;

public final class BmpWriter {
	
	public static void write_bmp(OutputStream out, BmpImage bmp) throws IOException {
		LittleEndianDataOutput out1 = new LittleEndianDataOutput(out);
		
		RgbImage image = bmp.image;
		int width = image.getWidth();
		int height = image.getHeight();
		int bytesPerPixel = image.getBitsPerPixel() / 8;
		
		int rowSize = (width * 3 + 3) / 4 * 4;  // 3 bytes per pixel in RGB888, round up to multiple of 4
		int imageSize = rowSize * height;
		
		// BITMAPFILEHEADER
		out1.writeBytes(new byte[]{'B', 'M'});  // FileType
		out1.writeInt32(14 + 40 + imageSize);   // FileSize
		out1.writeInt16(0);                     // Reserved1
		out1.writeInt16(0);                     // Reserved2
		out1.writeInt32(14 + 40);               // BitmapOffset
		
		// BITMAPINFOHEADER
		out1.writeInt32(40);                        // Size
		out1.writeInt32(width);                     // Width
		out1.writeInt32(height);                    // Height
		out1.writeInt16(1);                         // Planes
		out1.writeInt16(24);                        // BitsPerPixel
		out1.writeInt32(0);                         // Compression
		out1.writeInt32(imageSize);                 // SizeOfBitmap
		out1.writeInt32(bmp.horizontalResolution);  // HorzResolution
		out1.writeInt32(bmp.verticalResolution);    // VertResolution
		out1.writeInt32(0);                         // ColorsUsed
		out1.writeInt32(0);                         // ColorsImportant
		
		// Image data
		byte[] row = new byte[rowSize];
		for (int y = height - 1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				PixelColor pixels = image.getRgbPixel(x, y);
				row[x * bytesPerPixel + 2] = (byte) (pixels.r);  // rot
				row[x * bytesPerPixel + 1] = (byte) (pixels.g);  // grün
				row[x * bytesPerPixel + 0] = (byte) (pixels.b);  // blau
			}
			out1.writeBytes(row);
		}
		out1.flush();
	}
	
	
	// Not instantiable
	private BmpWriter() {}
	
}
