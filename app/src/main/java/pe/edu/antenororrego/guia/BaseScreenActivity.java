package pe.edu.antenororrego.guia;
import android.app.Activity;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.HashMap;

public abstract class BaseScreenActivity extends Activity {
 protected abstract String screenId();
 private static final int NAVY=0xFF10355F, BLUE=0xFF1769AA, WHITE=Color.WHITE, BG=0xFFF4F8FD, INK=0xFF263C50, MUTED=0xFF58718B;
 private JSONObject content;
 private LinearLayout body;
 private final Map<String,Class<? extends Activity>> screens=new HashMap<>();
 private int dp(int x){return (int)(x*getResources().getDisplayMetrics().density+0.5f);}
 private LinearLayout col(){LinearLayout c=new LinearLayout(this);c.setOrientation(1);return c;}
 private LinearLayout row(){LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);return c;}
 private LinearLayout.LayoutParams lp(int w,int h){return new LinearLayout.LayoutParams(w<0?w:dp(w),h<0?h:dp(h));}
 private GradientDrawable shape(int color,int radius,boolean border){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(dp(radius));if(border)d.setStroke(dp(1),0xFFE1EAF3);return d;}
 private TextView label(String text,int size,int color,boolean bold){TextView v=new TextView(this);v.setText(text);v.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);v.setTextColor(color);if(bold)v.setTypeface(Typeface.DEFAULT,Typeface.BOLD);return v;}
 private void space(LinearLayout b,int h){b.addView(new View(this),lp(1,h));}
 private void txt(LinearLayout b,String content,int size,int color,boolean bold,int gap){TextView v=label(content,size,color,bold);b.addView(v,lp(-1,-2));if(gap>0)space(b,gap);}
 private void register(){screens.put("INICIO",InicioActivity.class);screens.put("DIRECTORIO",DirectorioActivity.class);screens.put("NOSOTROS",NosotrosActivity.class);screens.put("CARRERAS",CarrerasActivity.class);screens.put("COMPUTACION",ComputacionActivity.class);screens.put("GESTION",GestionActivity.class);screens.put("CONTABILIDAD",ContabilidadActivity.class);screens.put("ENFERMERIA",EnfermeriaActivity.class);screens.put("ELECTRONICA",ElectronicaActivity.class);screens.put("FARMACIA",FarmaciaActivity.class);screens.put("AUTOMOTRIZ",AutomotrizActivity.class);screens.put("INFORMES",InformesActivity.class);screens.put("ADMISION",AdmisionActivity.class);screens.put("REQUISITOS",RequisitosActivity.class);screens.put("CRONOGRAMA",CronogramaActivity.class);screens.put("MATRICULA",MatriculaActivity.class);screens.put("BENEFICIOS",BeneficiosActivity.class);screens.put("PREGUNTAS",PreguntasActivity.class);screens.put("CONTACTO",ContactoActivity.class);screens.put("SERVICIOS",ServiciosActivity.class);screens.put("NOVEDADES",NovedadesActivity.class);}
 @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(NAVY);getWindow().setNavigationBarColor(NAVY);register();
 try(InputStream in=getAssets().open("content.json");ByteArrayOutputStream out=new ByteArrayOutputStream()){
 byte[] bytes=new byte[8192];int n;while((n=in.read(bytes))!=-1)out.write(bytes,0,n);
 content=new JSONObject(out.toString("UTF-8")).getJSONObject(screenId());draw();
 }catch(Exception e){TextView v=label("No se pudo abrir esta sección: "+e.getMessage(),16,NAVY,false);v.setPadding(25,25,25,25);setContentView(v);}}
 private void draw(){
 LinearLayout page=col();page.setBackgroundColor(BG);
 LinearLayout top=row();top.setPadding(dp(15),dp(8),dp(15),dp(8));top.setBackgroundColor(WHITE);
 TextView back=label("‹",34,BLUE,true);back.setGravity(Gravity.CENTER);top.addView(back,lp(40,50));back.setOnClickListener(v->{if("INICIO".equals(screenId()))navigate("DIRECTORIO");else finish();});
 TextView brand=label("ANTENOR ORREGO  ·  GUÍA",13,NAVY,true);brand.setGravity(Gravity.CENTER_VERTICAL);top.addView(brand,new LinearLayout.LayoutParams(0,dp(50),1));
 TextView menu=label("☰",25,BLUE,true);menu.setGravity(Gravity.CENTER);top.addView(menu,lp(40,50));menu.setOnClickListener(v->navigate("DIRECTORIO"));page.addView(top,lp(-1,66));
 ScrollView scroll=new ScrollView(this);scroll.setVerticalScrollBarEnabled(false);body=col();body.setPadding(dp(18),dp(17),dp(18),dp(24));scroll.addView(body);page.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
 LinearLayout banner=col();banner.setPadding(dp(20),dp(18),dp(20),dp(18));banner.setBackground(shape(NAVY,15,false));
 txt(banner,"IESTP   ·   FORMACIÓN TÉCNICA",12,0xFFB7DAFF,true,10);
 txt(banner,content.optString("title"),22,WHITE,true,10);txt(banner,content.optString("subtitle"),15,0xFFDAEAFA,false,0);body.addView(banner,lp(-1,-2));space(body,18);
 JSONArray sections=content.optJSONArray("sections");if(sections!=null)for(int i=0;i<sections.length();i++){
 JSONObject s=sections.optJSONObject(i);if(s==null)continue;LinearLayout panel=col();panel.setPadding(dp(16),dp(15),dp(16),dp(14));panel.setBackground(shape(WHITE,13,true));
 txt(panel,s.optString("heading"),17,BLUE,true,9);txt(panel,s.optString("content"),15,INK,false,0);body.addView(panel,lp(-1,-2));space(body,12);}
 JSONArray cards=content.optJSONArray("cards");if(cards!=null)for(int i=0;i<cards.length();i++){
 JSONObject c=cards.optJSONObject(i);if(c==null)continue;LinearLayout row=row();row.setPadding(dp(15),dp(12),dp(12),dp(12));row.setBackground(shape(WHITE,13,true));
 LinearLayout info=col();txt(info,c.optString("title"),16,NAVY,true,6);txt(info,c.optString("detail"),13,MUTED,false,0);row.addView(info,new LinearLayout.LayoutParams(0,-2,1));
 TextView arrow=label("›",27,BLUE,true);arrow.setGravity(Gravity.CENTER);row.addView(arrow,lp(27,55));
 String target=c.optString("target");row.setOnClickListener(v->navigate(target));body.addView(row,lp(-1,-2));space(body,10);}
 JSONArray actions=content.optJSONArray("actions");if(actions!=null)for(int i=0;i<actions.length();i++){
 JSONObject a=actions.optJSONObject(i);if(a==null)continue;boolean primary=i==0;
 TextView button=label(a.optString("title"),14,primary?WHITE:BLUE,true);button.setGravity(Gravity.CENTER);button.setPadding(dp(12),dp(14),dp(12),dp(14));button.setBackground(shape(primary?BLUE:WHITE,11,!primary));
 String target=a.optString("target"),href=a.optString("href");
 button.setOnClickListener(v->{if(!target.isEmpty())navigate(target);else if(!href.isEmpty())external(href);});
 body.addView(button,lp(-1,-2));space(body,10);}
 space(body,8);txt(body,"ⓘ  Información referencial. Confirma fechas, vacantes y procedimientos en los canales oficiales.",12,MUTED,false,0);
 LinearLayout nav=row();nav.setBackgroundColor(WHITE);nav.setPadding(dp(3),dp(3),dp(3),dp(4));
 for(String key:new String[]{"INICIO","CARRERAS","INFORMES","CONTACTO"}){
 TextView b=label(key,11,screenId().equals(key)?BLUE:MUTED,true);b.setGravity(Gravity.CENTER);b.setPadding(dp(1),dp(13),dp(1),dp(13));
 nav.addView(b,new LinearLayout.LayoutParams(0,-2,1));b.setOnClickListener(v->navigate(key));}
 page.addView(nav,lp(-1,56));setContentView(page);
 }
 private void navigate(String id){Class<? extends Activity> target=screens.get(id);if(target==null){Toast.makeText(this,"Sección no disponible",Toast.LENGTH_SHORT).show();return;}if(id.equals(screenId()))return;Intent i=new Intent(this,target);if(id.equals("INICIO"))i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);startActivity(i);}
 private void external(String address){try{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(address)));}catch(ActivityNotFoundException|SecurityException e){Toast.makeText(this,"No se puede abrir el enlace",Toast.LENGTH_LONG).show();}}
}
