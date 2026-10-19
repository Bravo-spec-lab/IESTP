package pe.edu.antenororrego.guia;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.InputStream;
public final class SplashActivity extends Activity {
 final Handler handler=new Handler(Looper.getMainLooper());
 final Runnable next=()->{startActivity(new Intent(this,InicioActivity.class));finish();};
 int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
 @Override public void onCreate(Bundle state){super.onCreate(state);getWindow().setStatusBarColor(0xFF10355F);getWindow().setNavigationBarColor(0xFF10355F);
 LinearLayout all=new LinearLayout(this);all.setOrientation(1);all.setGravity(Gravity.CENTER);all.setPadding(dp(22),dp(16),dp(22),dp(18));all.setBackgroundColor(Color.WHITE);
 ImageView emblem=new ImageView(this);emblem.setScaleType(ImageView.ScaleType.FIT_CENTER);
 try(InputStream in=getAssets().open("logo.jpg")){emblem.setImageBitmap(BitmapFactory.decodeStream(in));}catch(Exception ignored){}
 all.addView(emblem,new LinearLayout.LayoutParams(dp(224),dp(224)));
 TextView motto=new TextView(this);motto.setText("Formación Técnica\npara un Mejor Futuro.");motto.setTextColor(0xFF10355F);motto.setTextSize(18);motto.setGravity(Gravity.CENTER);motto.setPadding(0,dp(30),0,dp(32));all.addView(motto);
 ProgressBar progress=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);progress.setMax(100);progress.setProgress(0);all.addView(progress,new LinearLayout.LayoutParams(dp(195),dp(10)));
 TextView loading=new TextView(this);loading.setText("Cargando...");loading.setTextColor(0xFF10355F);loading.setTextSize(13);loading.setGravity(Gravity.CENTER);loading.setPadding(0,dp(12),0,0);all.addView(loading);
 setContentView(all);for(int i=1;i<=10;i++){final int step=i*10;handler.postDelayed(()->progress.setProgress(step),i*110L);}handler.postDelayed(next,1450);all.setOnClickListener(v->{handler.removeCallbacksAndMessages(null);next.run();});}
 @Override protected void onDestroy(){handler.removeCallbacksAndMessages(null);super.onDestroy();}
}
