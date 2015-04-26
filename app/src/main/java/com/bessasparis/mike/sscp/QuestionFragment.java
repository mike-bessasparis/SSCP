package com.bessasparis.mike.sscp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    //define ui elements
    TextView questionText;
    Button choice1;
    Button choice2;
    Button choice3;
    Button choice4;
    TextView feedbackText;


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_question, container, false);

        //get ui element views
        questionText = (TextView) V.findViewById(R.id.question);
        choice1 = (Button) V.findViewById(R.id.answerchoice1);
        choice2 = (Button) V.findViewById(R.id.answerchoice2);
        choice3 = (Button) V.findViewById(R.id.answerchoice3);
        choice4 = (Button) V.findViewById(R.id.answerchoice4);
        feedbackText = (TextView) V.findViewById(R.id.feedback);

        feedbackText.setText("");


        return V;

    }

    //accept json question object
    public void displayQuestion(JSONObject qObject) throws JSONException {

       questionText.setText(qObject.getString("questiontext"));

    }




}
