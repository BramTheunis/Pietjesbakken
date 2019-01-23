package com.example.android.pietjesbakken;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    // declareren variabelen voor shakesensor
    private SensorManager sm;
    private float acelVal;
    private float acelLast;
    private float shake;

    // variabelen voor usernames
    TextView name1;
    String st1;
    TextView name2;
    String st2;

    // variabelen voor dobbelen
    public static final Random RANDOM = new Random();
    private Button rollDice;
    private ImageView dice1, dice2, dice3;

    // variabelen voor vastzetten dobbelstenen
    CheckBox c1, c2, c3;

    // variabelen voor spelers
    TextView scoreOne, scoreTwo, tempScore, turnPlayer1;

    private static int SCORE_PLAYER_ONE = 0;
    private static int SCORE_PLAYER_TWO = 0;

    private static int DICE_ROLL = 0;
    private static int TEMP_SCORE = 0;

    private Button passTurn;

    ImageView playerOneIndicator, playerTwoIndicator;

    boolean playerOne = true;

    Integer value1;
    Integer value2;
    Integer value3;

    Integer score1;
    Integer score2;
    Integer score3;

    Integer totalScore1;
    Integer totalScore2;

    Integer tp1_number;
    Integer tp2_number;

    Integer sp1_number;
    Integer sp2_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        initializeWidgets();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        name1 = findViewById(R.id.namePlayer1);
        name2 = findViewById(R.id.namePlayer2);

        st1 = getIntent().getExtras().getString("Value1");
        name1.setText(st1);
        st2 = getIntent().getExtras().getString("Value2");
        name2.setText(st2);

        rollDice = findViewById(R.id.rollDice);
        dice1 = findViewById(R.id.dice1);
        dice2 = findViewById(R.id.dice2);
        dice3 = findViewById(R.id.dice3);
        passTurn = findViewById(R.id.passTurn);

        c1 = findViewById(R.id.checkBox1);
        c2 = findViewById(R.id.checkBox2);
        c3 = findViewById(R.id.checkBox3);

        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c1.isChecked() && c2.isChecked()) {
                    value3 = randomDiceValue();

                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbakken");

                    dice3.setImageResource(res3);

                } else if (c1.isChecked() && c3.isChecked()) {
                    value2 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbakken");

                    dice2.setImageResource(res2);

                } else if (c2.isChecked() && c3.isChecked()) {
                    value1 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbakken");

                    dice1.setImageResource(res1);

                } else if (c1.isChecked()) {
                    value2 = randomDiceValue();
                    value3 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbakken");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbakken");

                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);

                } else if (c2.isChecked()) {
                    value1 = randomDiceValue();
                    value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbakken");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbakken");

                    dice1.setImageResource(res1);
                    dice3.setImageResource(res3);

                } else if (c3.isChecked()) {
                    value1 = randomDiceValue();
                    value2 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbakken");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbakken");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);

                } else {
                    value1 = randomDiceValue();
                    value2 = randomDiceValue();
                    value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbakken");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbakken");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbakken");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);
                }

                howMuch1();
                howMuch2();
                howMuch3();

                // beurt mechanisme
                TextView turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
                String tp1 = turnPlayer1.getText().toString();
                tp1_number = Integer.parseInt(tp1);

                TextView turnPlayer2 = (TextView) findViewById(R.id.turnPlayer2);
                String tp2 = turnPlayer2.getText().toString();
                tp2_number = Integer.parseInt(tp2);

                if (playerOne == true) {
                    tp1_number = tp1_number+1;
                    String tp1_new_text = tp1_number + "";
                    turnPlayer1.setText(tp1_new_text);

                    totalScore1 = score1 + score2 + score3;
                    String score1Text = totalScore1 + "";
                    TextView scorePlayer1 = (TextView) findViewById(R.id.scoreSpeler1);
                    scorePlayer1.setText(score1Text);

                    if (tp1_number == 3) {
                        playerOne = false;
                        playerOneIndicator.setVisibility(View.INVISIBLE);
                        playerTwoIndicator.setVisibility(View.VISIBLE);
                    }
                } else if (playerOne == false) {
                    tp2_number = tp2_number+1;
                    String tp2_new_text = tp2_number + "";
                    turnPlayer2.setText(tp2_new_text);
                    tp1_number = tp1_number-1;

                    totalScore2 = score1 + score2 + score3;
                    String score2Text = totalScore2 + "";
                    TextView scorePlayer2 = (TextView) findViewById(R.id.scoreSpeler2);
                    scorePlayer2.setText(score2Text);

                    if (tp2_number-1 == tp1_number) {
                        // resetfunctie in de plaats zetten
                        playerOneIndicator.setVisibility(View.VISIBLE);
                        playerTwoIndicator.setVisibility(View.INVISIBLE);
                        newRound();
                        winner();
                    }
                }
            }
        });

        passTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerOne == true) {
                    playerOne = false;
                    playerOneIndicator.setVisibility(View.INVISIBLE);
                    playerTwoIndicator.setVisibility(View.VISIBLE);
                } else {
                    playerOne = true;
                    playerOneIndicator.setVisibility(View.VISIBLE);
                    playerTwoIndicator.setVisibility(View.INVISIBLE);
                    newRound();
                    winner();
                }
            }
        });


    }

    private void initializeWidgets() {
        playerOneIndicator = (ImageView) findViewById(R.id.imageViewPlayerOne);
        playerTwoIndicator = (ImageView) findViewById(R.id.imageViewPlayerTwo);
        passTurn = (Button) findViewById(R.id.passTurn);
        turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
        scoreOne = (TextView) findViewById(R.id.scoreSpeler1);
        scoreTwo = (TextView) findViewById(R.id.scoreSpeler2);
    }



    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;



            if (shake > 12) {
                if (c1.isChecked() && c2.isChecked()) {
                    value3 = randomDiceValue();

                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice3.setImageResource(res3);

                } else if (c1.isChecked() && c3.isChecked()) {
                    value2 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");

                    dice2.setImageResource(res2);

                } else if (c2.isChecked() && c3.isChecked()) {
                    value1 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);

                } else if (c1.isChecked()) {
                    value2 = randomDiceValue();
                    value3 = randomDiceValue();

                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);

                } else if (c2.isChecked()) {
                    value1 = randomDiceValue();
                    value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice3.setImageResource(res3);

                } else if (c3.isChecked()) {
                    value1 = randomDiceValue();
                    value2 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);

                } else {
                    value1 = randomDiceValue();
                    value2 = randomDiceValue();
                    value3 = randomDiceValue();

                    int res1 = getResources().getIdentifier("dice_" + value1, "drawable", "com.example.android.pietjesbak");
                    int res2 = getResources().getIdentifier("dice_" + value2, "drawable", "com.example.android.pietjesbak");
                    int res3 = getResources().getIdentifier("dice_" + value3, "drawable", "com.example.android.pietjesbak");

                    dice1.setImageResource(res1);
                    dice2.setImageResource(res2);
                    dice3.setImageResource(res3);
                }

                howMuch1();
                howMuch2();
                howMuch3();

                TextView turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
                String tp1 = turnPlayer1.getText().toString();
                tp1_number = Integer.parseInt(tp1);

                TextView turnPlayer2 = (TextView) findViewById(R.id.turnPlayer2);
                String tp2 = turnPlayer2.getText().toString();
                tp2_number = Integer.parseInt(tp2);

                if (playerOne == true) {
                    tp1_number = tp1_number+1;
                    String tp1_new_text = tp1_number + "";
                    turnPlayer1.setText(tp1_new_text);

                    totalScore1 = score1 + score2 + score3;
                    String score1Text = totalScore1 + "";
                    TextView scorePlayer1 = (TextView) findViewById(R.id.scoreSpeler1);
                    scorePlayer1.setText(score1Text);

                    if (tp1_number == 3) {
                        playerOne = false;
                        playerOneIndicator.setVisibility(View.INVISIBLE);
                        playerTwoIndicator.setVisibility(View.VISIBLE);
                    }
                } else if (playerOne == false) {
                    tp2_number = tp2_number+1;
                    String tp2_new_text = tp2_number + "";
                    turnPlayer2.setText(tp2_new_text);
                    tp1_number = tp1_number-1;

                    totalScore2 = score1 + score2 + score3;
                    String score2Text = totalScore2 + "";
                    TextView scorePlayer2 = (TextView) findViewById(R.id.scoreSpeler2);
                    scorePlayer2.setText(score2Text);

                    if (tp2_number-1 == tp1_number) {
                        // resetfunctie in de plaats zetten
                        playerOneIndicator.setVisibility(View.VISIBLE);
                        playerTwoIndicator.setVisibility(View.INVISIBLE);
                        newRound();
                        winner();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static int randomDiceValue() {
        return RANDOM.nextInt(6) + 1;
    }

    public int howMuch1() {
        if (value1 == 1) {
            score1 = 100;
        } else if (value1 == 2) {
            score1 = 2;
        } else if (value1 == 3) {
            score1 = 3;
        } else if (value1 == 4) {
            score1 = 4;
        } else if (value1 == 5) {
            score1 = 5;
        } else if (value1 == 6) {
            score1 = 60;
        }
        return score1;
    }

    public int howMuch2() {
        if (value2 == 1) {
            score2 = 100;
        } else if (value2 == 2) {
            score2 = 2;
        } else if (value2 == 3) {
            score2 = 3;
        } else if (value2 == 4) {
            score2 = 4;
        } else if (value2 == 5) {
            score2 = 5;
        } else if (value2 == 6) {
            score2 = 60;
        }
        return score2;
    }

    public int howMuch3() {
        if (value3 == 1) {
            score3 = 100;
        } else if (value3 == 2) {
            score3 = 2;
        } else if (value3 == 3) {
            score3 = 3;
        } else if (value3 == 4) {
            score3 = 4;
        } else if (value3 == 5) {
            score3 = 5;
        } else if (value3 == 6) {
            score3 = 60;
        }
        return score3;
    }

    public void newRound() {
        TextView streepjesPlayer1 = (TextView) findViewById(R.id.streepjesSpeler1);
        String sp1 = streepjesPlayer1.getText().toString();
        sp1_number = Integer.parseInt(sp1);

        TextView streepjesPlayer2 = (TextView) findViewById(R.id.streepjesSpeler2);
        String sp2 = streepjesPlayer2.getText().toString();
        sp2_number = Integer.parseInt(sp2);

        if (totalScore1 > totalScore2) {
            sp1_number = sp1_number - 1;
            String sp1_new_text = sp1_number + "";
            streepjesPlayer1.setText(sp1_new_text);
        } else if (totalScore1 < totalScore2) {
            sp2_number = sp2_number - 1;
            String sp2_new_text = sp2_number + "";
            streepjesPlayer2.setText(sp2_new_text);
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                reset();
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 3000);

    }

    public void reset() {

        totalScore1 = 0;
        String score1Text = totalScore1 + "";
        TextView scorePlayer1 = (TextView) findViewById(R.id.scoreSpeler1);
        scorePlayer1.setText(score1Text);

        tp1_number = 0;
        String tp1_new_text = tp1_number + "";
        TextView turnPlayer1 = (TextView) findViewById(R.id.turnPlayer1);
        turnPlayer1.setText(tp1_new_text);

        totalScore2 = 0;
        String score2Text = totalScore2 + "";
        TextView scorePlayer2 = (TextView) findViewById(R.id.scoreSpeler2);
        scorePlayer2.setText(score2Text);

        tp2_number = 0;
        String tp2_new_text = tp2_number + "";
        TextView turnPlayer2 = (TextView) findViewById(R.id.turnPlayer2);
        turnPlayer2.setText(tp2_new_text);

        playerOne = true;
    }

    public void winner() {

        if (sp1_number == 0) {
            Toast.makeText(HomeActivity.this, "Congratulations player 1", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (sp2_number == 0) {
            Toast.makeText(HomeActivity.this, "Congratulations player 2", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        
    }
}
