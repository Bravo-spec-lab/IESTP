package pe.edu.antenororrego.guia;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
public final class SplashActivity extends Activity {
 private final Handler handler=new Handler(Looper.getMainLooper());
 private final Runnable next=()->{startActivity(new Intent(this,InicioActivity.class));finish();};
 @Override public void onCreate(Bundle state){super.onCreate(state);getWindow().setStatusBarColor(0xFF10355F);
 LinearLayout box=new LinearLayout(this);box.setOrientation(1);box.setGravity(Gravity.CENTER);box.setBackgroundColor(Color.WHITE);box.setPadding(30,25,30,25);
 TextView t=new TextView(this);t.setText("IESTP");t.setTextSize(43);t.setTextColor(0xFF1769AA);t.setTypeface(null,1);t.setGravity(Gravity.CENTER);box.addView(t);
 TextView a=new TextView(this);a.setText("ANTENOR ORREGO ESPINOZA");a.setTextSize(19);a.setTypeface(null,1);a.setTextColor(0xFF10355F);a.setGravity(Gravity.CENTER);a.setPadding(0,20,0,10);box.addView(a);
 TextView b=new TextView(this);b.setText("Tu guía institucional");b.setTextSize(16);b.setTextColor(0xFF5B7081);b.setGravity(Gravity.CENTER);box.addView(b);setContentView(box);handler.postDelayed(next,1250);box.setOnClickListener(v->{handler.removeCallbacks(next);next.run();});}
 @Override protected void onDestroy(){handler.removeCallbacks(next);super.onDestroy();}
}
