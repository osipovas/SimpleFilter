package ca.osipovas.simplefilter;

import android.graphics.Bitmap;

public abstract class Filter {

	private int filterSize;
	
	protected int getFilterSize() {
		return filterSize;
	}

	protected void setFilterSize(int filterSize) {
		this.filterSize = filterSize;
	}

	private FilterTask filterTask;
	private Double pixelsGenerated = 0.0;
	private Double totalPixels = 0.0;
	private Integer progress = 0;
	private Integer oldProgress = 0;

	/**
	 * @param filterSize
	 */
	public Filter(int filterSize) {
		this.filterSize = filterSize;
	}

	/**
	 * Reassigns the value of x if it is less than min or greater than max
	 * 
	 * @param x
	 * @param min
	 * @param max
	 * @return the clamped value of x
	 */
	public int clamp(int x, int min, int max) {
		if (x > max) {
			return max;
		} else if (x < min) {
			return min;
		} else {
			return x;
		}
	}

	public Bitmap perform(Bitmap bitmap) {
		int adjustedSize = (int) Math.floor((filterSize - 1) / 2);
		int bitmapWidth = bitmap.getWidth();
		int bitmapHeight = bitmap.getHeight();
		totalPixels = (double) (bitmapWidth*bitmapHeight);

		Bitmap newBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
				bitmap.getConfig());

		for (int i = 0; i < bitmapWidth; i++) {
			for (int j = 0; j < bitmapHeight; j++) {
				/**
				 * Computing start and end points of the convolution mask
				 */
				int startX = i - adjustedSize;
				int startY = j - adjustedSize;
				int endX = i + adjustedSize;
				int endY = j + adjustedSize;

				int[] neighbours = null;
				if (startX < 0 || startY < 0 || endX >= bitmapWidth
						|| endY >= bitmapHeight) {
					/**
					 * Edge/Corner Case
					 */

					// Clamp Values Appropriately
					startX = clamp(startX, 0, bitmapWidth - 1);
					startY = clamp(startY, 0, bitmapHeight - 1);
					endX = clamp(endX, 0, bitmapWidth - 1);
					endY = clamp(endY, 0, bitmapHeight - 1);

					int width = (endX - startX) + 1;
					int height = (endY - startY) + 1;
					// neighbours has a different size
					neighbours = new int[width * height];

				}

				else {
					/**
					 * Normal Case
					 */
					neighbours = new int[filterSize * filterSize];
				}

				int m = 0;
				for (int k = startX; k <= endX; k++) {
					for (int l = startY; l <= endY; l++) {
						try {
							neighbours[m++] = bitmap.getPixel(k, l);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();

						}
					}
				}

				generateNewPixel(i, j, neighbours, newBitmap);
				pixelsGenerated++;
				computeProgress();
			}
		}

		return newBitmap;

	}

	private void computeProgress() {
		oldProgress = progress;
		progress = ((Double) ((pixelsGenerated/totalPixels)*100)).intValue();
		if (oldProgress != progress){
			this.filterTask.doProgress(progress);
		}
	}

	protected abstract void generateNewPixel(int i, int j, int[] neighbours,
			Bitmap newBitmap);

	public FilterTask getFilterTask() {
		return filterTask;
	}

	public void setFilterTask(FilterTask filterTask) {
		this.filterTask = filterTask;
	}

}
