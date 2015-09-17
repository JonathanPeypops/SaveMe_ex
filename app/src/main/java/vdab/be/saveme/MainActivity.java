package vdab.be.saveme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private static final String value = "NO";
    private Button saveButton;
    private Button getButton;
    private Button imgButton;
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        File fileDirectory = new File(Environment.getExternalStorageDirectory() + "/Selfiez/");
        fileDirectory.mkdirs();
        File file = new File(fileDirectory, "JP_" + timeStamp + ".jpg");
        final Uri uriSavedImage = Uri.fromFile(file);


        SharedPreferences sharedPref = getSharedPreferences(value, Context.MODE_PRIVATE);
        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(value, String.valueOf(inputText.getText().toString()));
                editor.apply();
                Toast.makeText(MainActivity.this, "SAVED", Toast.LENGTH_SHORT).show();
            }
        });
        getButton = (Button) findViewById(R.id.get);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                String text = sharedPref.getString(value, null);
                inputText.setText(text);
                Toast.makeText(MainActivity.this, "GOT IT", Toast.LENGTH_SHORT).show();
            }
        });

        imgButton = (Button) findViewById(R.id.imgbutton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("HEYHEY", uriSavedImage.getPath());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                MainActivity.this.startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
            }

        }
    }
}