package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */

    final String TAG = "Whack-A-Mole 2.0!";
    private int advancedScore = 0;
    CountDownTimer readyCountdown;
    CountDownTimer moleTimer;
    TextView advancedPoints;
    boolean lvl2 = false;
    final Toast[] mtoast = {null};
    ArrayList<Button> buttonList = new ArrayList<Button>();

    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        readyCountdown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/1000);
                if (mtoast[0] != null) {
                    mtoast[0].cancel();
                }
                mtoast[0] = Toast.makeText(getApplicationContext(), "Get Ready In " + millisUntilFinished/1000 + " seconds", Toast.LENGTH_SHORT);
                mtoast[0].show();
            }

            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                if (mtoast[0] != null) {
                    mtoast[0].cancel();
                }
                mtoast[0] = Toast.makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT);
                mtoast[0].show();
                placeMoleTimer();
                lvl2 = true;
            }
        };
        readyCountdown.start();
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        moleTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "New mole location!");
                setNewMole();
            }

            @Override
            public void onFinish() {
                moleTimer.start();
            }
        };
        moleTimer.start();
    }

    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receiveData = getIntent();
        advancedScore = receiveData.getIntExtra("Score", 10);

        Log.v(TAG, "Current User Score: " + advancedScore);

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button buttons = findViewById(id);
            buttonList.add(buttons);
        }
        advancedPoints = findViewById(R.id.score);
    }

    public void updateScore(){
        advancedPoints.setText(String.valueOf((advancedScore)));
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateScore();
        readyTimer();
    }

    public void OnClickButton(View v){
        if (lvl2){
            Button button = (Button) v;
            if (doCheck(button)) {
                advancedScore++;
                Log.v(TAG, "Hit, score added!");
            }
            else if (advancedScore != 0){
                advancedScore--;
                Log.v(TAG, "Missed, point deducted!");
            }
            else{
                Log.v(TAG, "Missed, point deducted!");
            }
            updateScore();
        }
    }

    private boolean doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if (checkButton.getText() == "*"){
            return true;
        }
        else {
            return false;
        }
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button b = buttonList.get(randomLocation);
        for (Button c : buttonList){
            if (b == c){
                b.setText("*");
            }
            else{
                b.setText("O");
            }
        }
    }
}


