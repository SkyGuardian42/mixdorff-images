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
import java.io.InputStream;

public final class BmpReader {
	
	public static BmpImage read_bmp(InputStream in) throws IOException {
		LittleEndianDataInput in1 = new LittleEndianDataInput(in);
		
		// BITMAPFILEHEADER (14 bytes)
		int fileSize;
		int imageDataOffset;
		if(in1.readInt16() != 0x4D42)  // "BM"
			throw new RuntimeException("Invalid BMP signature");
	
		fileSize = in1.readInt32();
		in1.skipFully(4);  // Skip reserved
		imageDataOffset = in1.readInt32();
		
		// BITMAPINFOHEADER
		int headerSize = in1.readInt32();
		int width;
		int height;
		boolean topToBottom;
		int bitsPerPixel;
		int compression;
		int colorsUsed;
		BmpImage bmp = new BmpImage();
		
		if (headerSize == 40) {
			int planes;
			int colorsImportant;
			width  = in1.readInt32();
			height = in1.readInt32();
			topToBottom = height < 0;
			height = Math.abs(height);
			planes = in1.readInt16();
			bitsPerPixel = in1.readInt16();
			compression = in1.readInt32();
			in1.readInt32();  // imageSize
			bmp.horizontalResolution = in1.readInt32();
			bmp.verticalResolution   = in1.readInt32();
			colorsUsed = in1.readInt32();
			colorsImportant = in1.readInt32();
			
			System.out.println("Dateigröße " + fileSize);
			System.out.println("Bits pro Pixel " + bitsPerPixel);
			System.out.println("Breite " + width);
			System.out.println("Höhe " + height);
			
			if (width <= 0)
				throw new RuntimeException("Invalid width: " + width);
			if (height == 0)
				throw new RuntimeException("Invalid height: " + height);
			if (planes != 1)
				throw new RuntimeException("Unsupported planes: " + planes);
			
			if (bitsPerPixel == 1 || bitsPerPixel == 4 || bitsPerPixel == 8) {
				if (colorsUsed == 0)
					colorsUsed = 1 << bitsPerPixel;
				if (colorsUsed > 1 << bitsPerPixel)
					throw new RuntimeException("Invalid colors used: " + colorsUsed);
				
			} else if (bitsPerPixel == 24 || bitsPerPixel == 32) {
				if (colorsUsed != 0)
					throw new RuntimeException("Invalid colors used: " + colorsUsed);
				
			} else
				throw new RuntimeException("Unsupported bits per pixel: " + bitsPerPixel);
			
			if (compression == 0) {
			} else if (bitsPerPixel == 8 && compression == 1 || bitsPerPixel == 4 && compression == 2) {
				if (topToBottom)
					throw new RuntimeException("Top-to-bottom order not supported for compression = 1 or 2");
			} else
				throw new RuntimeException("Unsupported compression: " + compression);
			
			if (colorsImportant < 0 || colorsImportant > colorsUsed)
				throw new RuntimeException("Invalid important colors: " + colorsImportant);
			
		} else
			throw new RuntimeException("Unsupported BMP header format: " + headerSize + " bytes");
		
		// Some more checks
		if (14 + headerSize + 4 * colorsUsed > imageDataOffset)
			throw new RuntimeException("Invalid image data offset: " + imageDataOffset);
		if (imageDataOffset > fileSize)
			throw new RuntimeException("Invalid file size: " + fileSize);
		
		// Read the image data
		in1.skipFully(imageDataOffset - (14 + headerSize + 4 * colorsUsed));
		if (bitsPerPixel == 24 || bitsPerPixel == 8) {
			bmp.image = readRgb24Or32Image(in1, width, height, topToBottom, bitsPerPixel);
		} else {
			System.out.println("Kein 24Bit Bitmap");
			System.exit(0);
		}
		return bmp;
	}
	
	private static RgbImage readRgb24Or32Image(LittleEndianDataInput in, int width, int height, boolean topToBottom, int bitsPerPixel) throws IOException {
		RgbImage image = new RgbImage(width, height, bitsPerPixel);
		
		int bytesPerPixel = bitsPerPixel / 8;
		byte[] row = new byte[(width * bytesPerPixel + 3) / 4 * 4];
		
		int y, end, inc;
		if (topToBottom) {
			y = 0;
			end = height;
			inc = 1;
		} else {
			y = height - 1;
			end = -1;
			inc = -1;
		}
		
		for(; y != end; y += inc) {
			in.readFully(row);
			for (int x = 0; x < width; x++) {
				PixelColor pixel = new PixelColor(
											row[x * bytesPerPixel + 2] & 0xFF,   // rot
											row[x * bytesPerPixel + 1] & 0xFF,   // grün
											row[x * bytesPerPixel + 0] & 0xFF);  // blau
				image.setRgbPixel(x, y, pixel);
			}
		}
		return image;
	}

	// Not instantiable
	private BmpReader() {}
}
