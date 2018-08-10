package com.example.pritam.airconditionerremote;

import android.app.ProgressDialog;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xw.repo.BubbleSeekBar;

public class MainActivity extends AppCompatActivity {

    private final static String URLON = "http://192.168.4.1:9000/on";
    private final static String URLOFF = "http://192.168.4.1:9000/off";
    StringBuilder UrlSet;
    StringBuilder FanUrlSet;
    private Button set;
    private Switch OnOff;
    private TextView state;
    private RadioGroup fanGroup;
    private RadioButton fanRadio;
    ProgressDialog progressDoalog;
    Handler handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        OnOff = (Switch) findViewById (R.id.OnOff);
        set = (Button) findViewById (R.id.set);
        state = (TextView) findViewById (R.id.state);
        Boolean ToggleButtonState = OnOff.isChecked ();
        BubbleSeekBar bubbleSeekBar= (BubbleSeekBar) findViewById (R.id.seekbar);
        fanGroup = (RadioGroup) findViewById (R.id.fanGroup);

        bubbleSeekBar.setOnProgressChangedListener (new BubbleSeekBar.OnProgressChangedListener () {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
            }
            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

                final ProgressDialog dialog = ProgressDialog.show (MainActivity.this, "Doing something",
                        "Please wait....", true);
                new Thread (new Runnable () {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep (1000);
                            dialog.dismiss ();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace ();
                        }
                    }
                }).start ();

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                UrlSet = new StringBuilder("http://192.168.4.1:9000/");
                UrlSet.append ("tem"+progress);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlSet.toString (),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Temrature set :",UrlSet.toString ()+"");
                                Toast.makeText(getApplicationContext(),("Temprature set")+" ",Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("That didn't work!","No work");
                    }
                });
                queue.add(stringRequest);
            }
            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {
            }
        });

        set.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                int selectedId = fanGroup.getCheckedRadioButtonId();
                fanRadio = (RadioButton) findViewById(selectedId);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                FanUrlSet = new StringBuilder("http://192.168.4.1:9000/");

                if ( fanRadio.getText().equals ("fan1"))
                    FanUrlSet.append ("fan" + 1);
                if (fanRadio.getText().equals ("fan2"))
                    FanUrlSet.append ("fan" + 2);
                if (fanRadio.getText().equals ("fan3"))
                    FanUrlSet.append ("fan" + 3);
                if (fanRadio.getText().equals ("fan4"))
                    FanUrlSet.append ("fan" + 4);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, FanUrlSet.toString (),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Fan set:",FanUrlSet.toString ()+"");
                                Toast.makeText(getApplicationContext(),("Fan set")+" ",Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("That didn't work!","No work");
                    }
                });
                queue.add(stringRequest);
                Toast.makeText(MainActivity.this,fanRadio.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        OnOff.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if (isChecked) {
                    state.setText ("ON");
                    requestOn ();
                } else {
                    state.setText ("Off");
                    requestOff ();
                }
            }
        });
    }
    public void requestOn()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLON,
            new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Log.d("Request On :","Done");
            Toast.makeText(getApplicationContext(),("ON")+" ",Toast.LENGTH_LONG).show();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("That didn't work!","No work");
        }
    });
    queue.add(stringRequest);
}

    public void requestOff()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLOFF,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Request Off :","Done");
                        Toast.makeText(getApplicationContext(),("OFF")+" ",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("That didn't work!","No work");
            }
        });
        queue.add(stringRequest);
    }
}


