package at.aau.se2.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final AAUApiConnector aauApiConnector = new AAUApiConnector();
    private final AlternatingDigitSumCalculator alternatingDigitSumCalculator = new AlternatingDigitSumCalculator();

    private Handler responseHandler;

    private Button btnSend;
    private Button btnDigitSum;
    private TextView textViewResult;
    private EditText editTextMatNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseHandler = new Handler(getMainLooper());

        btnSend = findViewById(R.id.btnSend);
        btnDigitSum = findViewById(R.id.btnDigitSum);
        textViewResult = findViewById(R.id.textViewResult);
        editTextMatNr = findViewById(R.id.editTextMatNr);

        btnDigitSum.setOnClickListener((view) -> {
            try {
                int input = Integer.parseInt(editTextMatNr.getText().toString());
                int result = alternatingDigitSumCalculator.alternatingDigitSum(input);
                boolean isEven = result % 2 == 0;
                textViewResult.setText(isEven ? R.string.alternating_digit_sum_even : R.string.alternating_digit_sum_odd);
            } catch (IllegalArgumentException ex) {
                textViewResult.setText(R.string.invalid_input);
            }
        });

        btnSend.setOnClickListener((view) -> {
            setInputsEnabled(false);
            textViewResult.setText(R.string.loading);

            aauApiConnector.getStudyTime(
                    editTextMatNr.getText().toString(),
                    responseHandler,
                    (resultStr) -> {
                        setInputsEnabled(true);
                        textViewResult.setText(resultStr);
                    },
                    (error) -> {
                        setInputsEnabled(true);
                        textViewResult.setText(error.getLocalizedMessage());
                    }
            );
        });
    }

    private void setInputsEnabled(boolean value) {
        this.btnSend.setEnabled(value);
        this.btnDigitSum.setEnabled(value);
        this.editTextMatNr.setEnabled(value);
    }
}