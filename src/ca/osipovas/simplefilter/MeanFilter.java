package ca.osipovas.simplefilter;

import android.graphics.Bitmap;
import android.graphics.Color;

public class MeanFilter extends Filter {

	public MeanFilter(int filterSize) {
		super(filterSize);
	}

	/**
	 * 
	 * @param pixels
	 *            - a one dimensional array of pixels
	 * @return a packed int representing the mean pixel based on the pixels
	 *         array
	 */
	public int computeMean(int[] pixels) {
		int sumRed = 0;
		int sumBlue = 0;
		int sumGreen = 0;

		for (int i = 0; i < pixels.length; i++) {
			sumRed += Color.red(pixels[i]);
			sumBlue += Color.blue(pixels[i]);
			sumGreen += Color.green(pixels[i]);
		}

		int meanRed = sumRed / pixels.length;
		int meanBlue = sumBlue / pixels.length;
		int meanGreen = sumGreen / pixels.length;

		return Color.rgb(meanRed, meanGreen, meanBlue);

	}

	@Override
	protected void generateNewPixel(int i, int j, int[] neighbours,
			Bitmap newBitmap) {
		newBitmap.setPixel(i, j, computeMean(neighbours));
	}

	
}
