package ca.osipovas.simplefilter;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.ipaulpro.afilechooser.utils.FileUtils;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Bitmap bitmap;
    private ImageView imageView;
    PhotoViewAttacher mAttacher;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView1);
        mAttacher = new PhotoViewAttacher(imageView);

        
     
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    private static final int REQUEST_CHOOSER = 1234;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHOOSER:   
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);

                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                       
                    }
                    changeImage(path);
                    
                }
                break;
        }
    }
    
    /**
     * 
     * @param String path of the image
     * 
     * Changes the image that the user sees
     */
    public void changeImage(String path){
        Bitmap newImage = BitmapFactory.decodeFile(path);
        if (newImage != null){
            imageView.setImageBitmap(newImage);        	
            this.bitmap = newImage;
            mAttacher.update();
        }
    }
    
    public void changeSettings(MenuItem item){
    	Intent intent = new Intent(this,SettingsActivity.class);    	
        startActivity(intent);
    }
    
    public void chooseImage(MenuItem item){
    	// Create the ACTION_GET_CONTENT Intent
        Intent getContentIntent = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(getContentIntent, "Select an image file");
        startActivityForResult(intent, REQUEST_CHOOSER);
    }
    
    public void performMedianFilter(View button){
    	if (validateFilterSize()){
    		 String meanFilter = "Performing Median Filter (Filter Size: " + getFilterSize()+ ")";
             ProgressDialog mProgressDialog = generateProgressDialog(meanFilter);
             mProgressDialog.show();
             FilterTask filterTask = new FilterTask(imageView, mProgressDialog);
             MedianFilter filter = new MedianFilter(getFilterSize());
             filterTask.execute(filter);
             mAttacher.update();
    	}
    	

    }
    
    public void performMeanFilter(View button){
    	if (validateFilterSize()){
    		String meanFilter = "Performing Mean Filter (Filter Size: " + getFilterSize()+ ")";
            ProgressDialog mProgressDialog = generateProgressDialog(meanFilter);
            mProgressDialog.show();
            FilterTask filterTask = new FilterTask(imageView, mProgressDialog);
            MeanFilter filter = new MeanFilter(getFilterSize());
            filterTask.execute(filter);
            mAttacher.update();
    	}
        

        
    }
    
    private boolean validateFilterSize() {
    	Integer imageHeight = ((BitmapDrawable)imageView.getDrawable()).getBitmap().getHeight();
    	Integer imageWidth = ((BitmapDrawable)imageView.getDrawable()).getBitmap().getWidth();
    	if (getFilterSize() >= imageHeight){
        	showInvalidFilterSizeToast(imageHeight);
    		return false;
    	}
    	else if (getFilterSize() >= imageWidth){
        	showInvalidFilterSizeToast(imageWidth);
    		return false;
    	}
    	else {
    		return true;
    	}
	}


	private void showInvalidFilterSizeToast(Integer dimension) {
		Toast.makeText(this, "Place reduce filter size to less than "  + dimension + " pixels", Toast.LENGTH_SHORT).show();
	}


	private ProgressDialog generateProgressDialog(String message){
    	ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        return progressDialog;
        
    }
    
    public int getFilterSize(){
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	return Integer.parseInt(sharedPreferences.getString("filter_size", "1"));
    }
}
