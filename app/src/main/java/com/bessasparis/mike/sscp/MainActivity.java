package com.bessasparis.mike.sscp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    private String dataFile = "ports.json";
    private JSONObject jsonObj;
    JSONArray questionsArray;
    QuestionFragment qFrag;
    int currentQuestionIndex = 0;
    int numberOfQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set quiz preference default
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_main);

        findViewById(R.id.next).setOnClickListener(nextButtonHandler);

    }

    //do this setup here to support changing quiz type preference while running
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        jsonObj = setupJsonObject(getDataFileName());

        qFrag = (QuestionFragment) getFragmentManager().findFragmentById(R.id.question_fragment);

        try {
            questionsArray = jsonObj.getJSONArray("questions");
            numberOfQuestions = questionsArray.length();
            currentQuestionIndex = 0;
            qFrag.displayQuestion(questionsArray.getJSONObject(currentQuestionIndex));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //really a bad KLUDGE.  can't get the list array of values to work in display XML
    private String getDataFileName() {
        switch (getQuizType()) {
            case "access":
                return "access-controls.json";
            case "audit":
                return "auditing.json";
            case "crypto":
                return "cryptography.json";
            case "fund":
                return "fundamentals.json";
            case "networks":
                return "networks-and-telecom.json";
            case "ports":
                return "ports.json";
        }
        return "error";
    }

    private String getQuizType() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean quizTypeAccess = sharedPref.getBoolean("pref_accessControls", false);
        Boolean quizTypeAudit = sharedPref.getBoolean("pref_auditing", false);
        Boolean quizTypeCrypto = sharedPref.getBoolean("pref_cryptography", false);
        Boolean quizTypeFund = sharedPref.getBoolean("pref_fundamentals", false);
        Boolean quizTypeNetworks = sharedPref.getBoolean("pref_netAndTelecom", false);
        Boolean quizTypePorts = sharedPref.getBoolean("pref_ports", false);

        if (quizTypeNetworks) {
            return "networks";
        }
        if (quizTypeAccess) {
            return "access";
        }
        if (quizTypeAudit) {
            return "audit";
        }
        if (quizTypeCrypto) {
            return "crypto";
        }
        if (quizTypeFund) {
            return "fund";
        } else {
            return ("ports");
        }
    }

    private View.OnClickListener nextButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //account for zero-based indexing in array
            if (currentQuestionIndex+1 < numberOfQuestions) {
                ++currentQuestionIndex;
            }
            else {
                currentQuestionIndex = 0;
            }

            try {
                qFrag.displayQuestion(questionsArray.getJSONObject(currentQuestionIndex));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private JSONObject setupJsonObject(String mQfile) {
        try {
            jsonObj = new JSONObject(loadJSONFromAsset(mQfile));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String loadJSONFromAsset(String mQfile) {
        String json = null;
        try {
            InputStream is = getAssets().open(mQfile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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
            // Display the fragment.
            Log.i("mjb", "settings selected");
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
