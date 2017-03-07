package com.vogella.android.fontchooserwithintents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    int style = 0;
    int font = 0;
    float fontSize = 0;
    int redNumber = 0;
    int blueNumber = 0;
    int greenNumber= 0;

    String message = "";

    TextView output;
    TextView styleOutput;
    TextView fontOutput;
    TextView sizeOutput;
    TextView colorOutput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (TextView) findViewById(R.id.output);

        styleOutput = (TextView) findViewById(R.id.styleOutput);
        fontOutput = (TextView) findViewById(R.id.fontOutput);
        sizeOutput = (TextView) findViewById(R.id.sizeOutput);
        colorOutput = (TextView) findViewById(R.id.colorOutput);
        colorOutput.setTypeface(output.getTypeface(), Typeface.BOLD);

    }

    public void startFontChooser(View view){
        Intent intent = new Intent("msud.cs3013.ACTION_RETRIEVE_FONT");

        //Check if an app exists to recieve intent
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;

        //If app exists then launch the app
        if(isIntentSafe){
            startActivityForResult(intent, REQUEST_CODE);
        }
        else{
            String msg = "No app to handle intent";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){

                unpackIntent(data);
                updateText();
            }
        }
    }

    public void unpackIntent(Intent data){
        style = data.getIntExtra("style", -1);
        fontSize = data.getIntExtra("font size", -1);
        font = data.getIntExtra("font", -1);
        redNumber = data.getIntExtra("color red", -1);
        greenNumber = data.getIntExtra("color green", -1);
        blueNumber = data.getIntExtra("color blue", -1);
        message = data.getStringExtra("text");
    }

    public void updateText(){

        if(font != -1){
            if(font == 0){
                output.setTypeface(Typeface.DEFAULT, output.getTypeface().getStyle());
            }else if(font == 1){
                output.setTypeface(Typeface.MONOSPACE, output.getTypeface().getStyle());
            }else if(font == 2){
                output.setTypeface(Typeface.SANS_SERIF, output.getTypeface().getStyle());
            }else if(font == 3){
                output.setTypeface(Typeface.SERIF, output.getTypeface().getStyle());
            }
        }

        if(style != -1){
            output.setTypeface(output.getTypeface(), style);
        }
        Toast.makeText(this, "" + fontSize, Toast.LENGTH_LONG).show();
        if(fontSize != -1){
            output.setTextSize(fontSize);
        }

        output.setTextColor(getTextColor());

        output.setText(message);

        setTextFields();
    }


    public int getTextColor(){
        int red = 0;
        int green = 0;
        int blue = 0;

        if(redNumber != -1){
            red = redNumber;
        }
        if(greenNumber != -1){
            green = greenNumber;
        }
        if(blueNumber != -1){
            blue = blueNumber;
        }

        return Color.rgb(red, green, blue);
    }

    public void setTextFields(){
        sizeOutput.setText(fontSize + "");
        colorOutput.setTextColor(getTextColor());
        if(font != -1){
            if(font == 0){
                fontOutput.setText("Default");
            }else if(font == 1){
                fontOutput.setText("Monospace");
            }else if(font == 2){
                fontOutput.setText("Sans Serif");
            }else if(font == 3){
                fontOutput.setText("Serif");
            }
        }

        if(style != -1){
            if(style == Typeface.BOLD){
                styleOutput.setText("Bold");
            }else if(style == Typeface.BOLD_ITALIC){
                styleOutput.setText("Bold Italic");
            }else if(style == Typeface.ITALIC){
                styleOutput.setText("Italic");
            }else if(style == Typeface.NORMAL){
                styleOutput.setText("Normal");
            }
        }
    }
}
