package com.arima.stopwatch;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

public class StopwatchActivity extends Activity {

    //Количество секунд на секундомере.
    private int seconds = 0;
    //Секундомер работает?
    private boolean running;
    // Переменную wasRunning для хранения информации о том, работал ли секундомер перед вызовом метода onStop()
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

//Использование сохраненных данных при изменении конфигурации телефона
        if (savedInstanceState != null) {

            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            //Восстанавливаем состояние переменной wasRunning, если активность создается заново.
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();

    }

    //Сохранение переменных активности для применения при изменении конфигурации
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
//Сохранить состояние переменной wasRunning.
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }


    //Запустить секундомер при щелчке на кнопке Start.
    public void onClickStart(View view) {
        running = true;
    }

    //Остановить секундомер при щелчке на кнопке Stop.
    public void onClickStop(View view) {
        running = false;
    }

    //Сбросить секундомер при щелчке на кнопке Reset.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    //Обновление показаний таймера.
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    //Если активность возобновляет работу, снова запустить отсчет времени, если он происходил до этого.
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    //Если активность приостанавливается, остановить отсчет времени.
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }


}