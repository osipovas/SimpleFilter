package ca.osipovas.simplefilter;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class FilterTask extends AsyncTask<Filter, Integer, Bitmap> {

	private WeakReference<ImageView> imageViewReference;
	private ProgressDialog mProgressDialog;

	public FilterTask(ImageView imageView, ProgressDialog progressDialog) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		mProgressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				cancelTask();
				mProgressDialog.dismiss();
			}
		});

	}
	
	private void cancelTask(){
		this.cancel(true);
	}

	@Override
	protected Bitmap doInBackground(Filter... params) {

		Filter filter = params[0];
		filter.setFilterTask(this);
		return filter.perform(getBitmapFromReference());

	}

	private Bitmap getBitmapFromReference() {
		return ((BitmapDrawable) imageViewReference.get().getDrawable())
				.getBitmap();
	}

	public void doProgress(Integer progress) {
		publishProgress(progress);
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		mProgressDialog.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Bitmap filteredImage) {
		if (imageViewReference != null && filteredImage != null) {
			final ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				imageView.setImageBitmap(filteredImage);
			}
		}
		mProgressDialog.cancel();
	}

}
