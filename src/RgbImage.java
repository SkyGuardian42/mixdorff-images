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

public final class RgbImage {
	
	protected int width;
	protected int height;
	protected int bitsPerPixel; 
	private PixelColor[] pixels;
	
	public RgbImage(int width, int height, int bitsPerPixel) {
		this.width = width;
		this.height = height;
		this.bitsPerPixel = bitsPerPixel;
		
		if (width > Integer.MAX_VALUE / height)
			throw new IllegalArgumentException("Image dimensions too large");
	
		pixels = new PixelColor[width * height];
	}
	
	public PixelColor getRgbPixel(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			throw new IndexOutOfBoundsException();
		
//		System.out.println("schreibe " 
//							+ " blau " + pixels[y * width + x].b  
//							+ " grün " + pixels[y * width + x].g  
//							+ " rot " + pixels[y * width + x].r);
		return pixels[y * width + x];
	}
	
	public void setRgbPixel(int x, int y, PixelColor pixel) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			throw new IndexOutOfBoundsException();
		
		pixels[y * width + x] = pixel;
//		System.out.println("lese " 
//							+ " blau " + pixels[y * width + x].b  
//							+ " grün " + pixels[y * width + x].g  
//							+ " rot " + pixels[y * width + x].r);
	}	
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public int getBitsPerPixel() {
		return this.bitsPerPixel;
	}
}



