package com.bessasparis.mike.sscp;


import android.graphics.Color;
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
    View V;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        V = inflater.inflate(R.layout.fragment_question, container, false);

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

    //accept json question object, displays question and choices
    public void displayQuestion(JSONObject qObject) throws JSONException {

        questionText.setText(qObject.getString("questiontext"));
        choice1.setText(qObject.getString("answerchoice1"));
        choice2.setText(qObject.getString("answerchoice2"));
        choice3.setText(qObject.getString("answerchoice3"));
        choice4.setText(qObject.getString("answerchoice4"));
        feedbackText.setText("");

        V.findViewById(R.id.answerchoice1).setOnClickListener(feedbackWrong);
        V.findViewById(R.id.answerchoice2).setOnClickListener(feedbackWrong);
        V.findViewById(R.id.answerchoice3).setOnClickListener(feedbackWrong);
        V.findViewById(R.id.answerchoice4).setOnClickListener(feedbackWrong);

        switch(qObject.getInt("answer")) {
            case 1:
                V.findViewById(R.id.answerchoice1).setOnClickListener(feedbackCorrect);
                break;
            case 2:
                V.findViewById(R.id.answerchoice2).setOnClickListener(feedbackCorrect);
                break;
            case 3:
                V.findViewById(R.id.answerchoice3).setOnClickListener(feedbackCorrect);
                break;
            case 4:
                V.findViewById(R.id.answerchoice4).setOnClickListener(feedbackCorrect);
                break;
        }
    }

    private View.OnClickListener feedbackCorrect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView feedbackText = (TextView) V.findViewById(R.id.feedback);
            feedbackText.setText("CORRECT");
            feedbackText.setTextColor(Color.GREEN);
        }
    };
    private View.OnClickListener feedbackWrong = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView feedbackText = (TextView) V.findViewById(R.id.feedback);
            feedbackText.setText("WRONG");
            feedbackText.setTextColor(Color.RED);
        }
    };
}
