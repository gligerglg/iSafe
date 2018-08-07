package apps.gliger.isafe;


import android.os.Handler;

public class Stopwatch {
    private long total_time;
    private Handler handler;

    public Stopwatch() {
        total_time = 0;
        handler = new Handler();
    }

    public void start(){
        handler.postDelayed(thread,1000);
    }

    public long stop(){
        long time = total_time;
        total_time = 0;
        handler.removeCallbacks(thread);
        return time;
    }

    public void pause(){
        handler.removeCallbacks(thread);
    }

    public long getTotal_time() {
        return total_time;
    }

    Runnable thread = new Runnable() {
        @Override
        public void run() {
            total_time += 1;
            handler.postDelayed(thread,1000);
        }
    };
}
