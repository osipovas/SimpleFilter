package ca.osipovas.simplefilter;

import java.util.Arrays;

import android.graphics.Bitmap;
import android.graphics.Color;

public class MedianFilter extends Filter {

	public MedianFilter(int filterSize) {
		super(filterSize);
	}
	/**
	 * 
	 * @param pixels
	 *            - a one dimensional array of pixels
	 * @return a packed int representing the median pixel based on the pixels
	 *         array
	 */
	public int computeMedian(int[] pixels) {

		int[] reds = new int[pixels.length];
		int[] greens = new int[pixels.length];
		int[] blues = new int[pixels.length];

		for (int i = 0; i < pixels.length; i++) {
			reds[i] = Color.red(pixels[i]);
			greens[i] = Color.green(pixels[i]);
			blues[i] = Color.blue(pixels[i]);
		}

		int medianRed = getMedian(reds);
		int medianGreen = getMedian(greens);
		int medianBlue = getMedian(blues);

		
		Integer color =  Color.rgb(medianRed, medianGreen, medianBlue);
		return color;
	}

	/**
	 * @param values
	 *            an array of ints
	 * @return the median of the values array
	 */
	private int getMedian(int[] values) {
		Arrays.sort(values);
		double median = 0;
		if (values.length % 2 == 0) {
			median = ((double) values[values.length / 2] + (double) values[values.length / 2 - 1]) / 2;
		} else {
			median = (double) values[values.length / 2];
		}
		return (int) median;
	}
	
	@Override
	protected void generateNewPixel(int i, int j,int[] neighbours, Bitmap newBitmap) {
		newBitmap.setPixel(i, j, computeMedian(neighbours));
	}
}
