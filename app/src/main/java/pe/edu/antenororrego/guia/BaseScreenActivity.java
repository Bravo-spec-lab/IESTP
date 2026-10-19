package pe.edu.antenororrego.guia;
import android.app.Activity;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import org.json.*;
import java.io.*;
import java.util.*;

public abstract class BaseScreenActivity extends Activity {
 protected abstract String screenId();
 final int BLUE=0xFF1764B2, NAVY=0xFF10355F, WHITE=Color.WHITE, INK=0xFF273B50, MUTED=0xFF66768A, BG=0xFFF5F8FC;
 JSONObject data; LinearLayout body; final Map<String,Class<? extends Activity>> classes=new HashMap<>();
 int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
 LinearLayout column(){LinearLayout x=new LinearLayout(this);x.setOrientation(1);return x;}
 LinearLayout row(){LinearLayout x=new LinearLayout(this);x.setGravity(Gravity.CENTER_VERTICAL);return x;}
 LinearLayout.LayoutParams p(int w,int h){return new LinearLayout.LayoutParams(w<0?w:dp(w),h<0?h:dp(h));}
 GradientDrawable round(int color,int radius){GradientDrawable r=new GradientDrawable();r.setColor(color);r.setCornerRadius(dp(radius));return r;}
 GradientDrawable border(int c){GradientDrawable r=round(c,14);r.setStroke(dp(1),0xFFDDE7F1);return r;}
 TextView text(String value,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(value);t.setTextColor(color);t.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);return t;}
 void addText(LinearLayout l,String value,int size,int color,boolean bold){l.addView(text(value,size,color,bold),p(-1,-2));}
 void gap(LinearLayout l,int height){l.addView(new View(this),p(1,height));}
 ImageView picture(String name,int height,ImageView.ScaleType mode){
    if(name==null||name.isEmpty())return null;try(InputStream in=getAssets().open(name)){ImageView img=new ImageView(this);img.setImageBitmap(BitmapFactory.decodeStream(in));img.setScaleType(mode);return img;}catch(Exception ignored){return null;}
 }
 String val(JSONObject obj,String k){return obj.optString(k,"");}
 void register(){
 classes.put("INICIO",InicioActivity.class);
 classes.put("DIRECTORIO",DirectorioActivity.class);
 classes.put("NOSOTROS",NosotrosActivity.class);
 classes.put("CARRERAS",CarrerasActivity.class);
 classes.put("COMPUTACION",ComputacionActivity.class);
 classes.put("GESTION",GestionActivity.class);
 classes.put("CONTABILIDAD",ContabilidadActivity.class);
 classes.put("ENFERMERIA",EnfermeriaActivity.class);
 classes.put("FARMACIA",FarmaciaActivity.class);
 classes.put("ELECTRONICA",ElectronicaActivity.class);
 classes.put("AUTOMOTRIZ",AutomotrizActivity.class);
 classes.put("INFORMES",InformesActivity.class);
 classes.put("ADMISION",AdmisionActivity.class);
 classes.put("REQUISITOS",RequisitosActivity.class);
 classes.put("CRONOGRAMA",CronogramaActivity.class);
 classes.put("MATRICULA",MatriculaActivity.class);
 classes.put("BENEFICIOS",BeneficiosActivity.class);
 classes.put("PREGUNTAS",PreguntasActivity.class);
 classes.put("CONTACTO",ContactoActivity.class);
 classes.put("SERVICIOS",ServiciosActivity.class);
 classes.put("NOVEDADES",NovedadesActivity.class);
 }
 @Override public void onCreate(Bundle state){super.onCreate(state);getWindow().setStatusBarColor(NAVY);getWindow().setNavigationBarColor(NAVY);register();try(InputStream in=getAssets().open("content.json");ByteArrayOutputStream out=new ByteArrayOutputStream()){byte[] b=new byte[4096];int n;while((n=in.read(b))!=-1)out.write(b,0,n);data=new JSONObject(out.toString("UTF-8")).getJSONObject(screenId());draw();}catch(Exception e){TextView t=text("No se pudo abrir esta sección: "+e.getMessage(),16,NAVY,false);t.setPadding(20,20,20,20);setContentView(t);}}
 void draw(){
 LinearLayout page=column();page.setBackgroundColor(BG);
 LinearLayout top=row();top.setPadding(dp(12),dp(8),dp(12),dp(8));top.setBackgroundColor(WHITE);
 TextView menu=text("☰",25,NAVY,true);menu.setGravity(Gravity.CENTER);top.addView(menu,p(42,48));menu.setOnClickListener(v->open("DIRECTORIO"));
 TextView head=text("ISTP AOE",17,NAVY,true);head.setGravity(Gravity.CENTER);top.addView(head,new LinearLayout.LayoutParams(0,dp(48),1));
 TextView alert=text("◉",22,BLUE,true);alert.setGravity(Gravity.CENTER);top.addView(alert,p(42,48));alert.setOnClickListener(v->open("NOVEDADES"));page.addView(top,p(-1,64));
 ScrollView scroll=new ScrollView(this);scroll.setVerticalScrollBarEnabled(false);body=column();body.setPadding(dp(14),dp(13),dp(14),dp(22));scroll.addView(body);page.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
 if("INICIO".equals(screenId()))home();else standard();
 navigation(page);setContentView(page);
 }
 void home(){
  LinearLayout hero=column();hero.setBackground(round(WHITE,16));
  ImageView image=picture(val(data,"image"),185,ImageView.ScaleType.CENTER_CROP);if(image!=null)hero.addView(image,p(-1,185));
  LinearLayout overlay=column();overlay.setBackgroundColor(NAVY);overlay.setPadding(dp(16),dp(14),dp(16),dp(18));
  addText(overlay,"Bienvenido al",14,WHITE,false);gap(overlay,5);addText(overlay,"ISTP Antenor Orrego Espinoza",21,WHITE,true);gap(overlay,5);addText(overlay,val(data,"subtitle"),13,0xFFDAEAFA,false);
  hero.addView(overlay,p(-1,-2));body.addView(hero,p(-1,-2));gap(body,16);
  JSONArray cards=data.optJSONArray("cards");if(cards!=null)for(int i=0;i<cards.length();i+=2){
    LinearLayout group=row();group.setGravity(Gravity.CENTER);JSONObject one=cards.optJSONObject(i),two=cards.optJSONObject(i+1);
    if(one!=null)group.addView(tile(one,i),new LinearLayout.LayoutParams(0,dp(122),1));
    gap(group,10);
    if(two!=null)group.addView(tile(two,i+1),new LinearLayout.LayoutParams(0,dp(122),1));
    body.addView(group,p(-1,-2));gap(body,12);
  }
  JSONArray actions=data.optJSONArray("actions");if(actions!=null)for(int i=0;i<actions.length();i++)action(actions.optJSONObject(i),i==0);
 }
 LinearLayout tile(JSONObject v,int i){
  int[] shades={0xFFD9ECFF,0xFFE2E6FF,0xFFD9F4E5,0xFFFFE8D6};
  String[] symbols={"⌂","✦","▤","✉"};
  LinearLayout a=column();a.setGravity(Gravity.CENTER);a.setPadding(dp(8),dp(12),dp(8),dp(10));a.setBackground(round(shades[i%4],13));
  TextView symbol=text(symbols[i%4],37,BLUE,true);symbol.setGravity(Gravity.CENTER);a.addView(symbol,p(-1,52));gap(a,5);
  TextView name=text(val(v,"title"),15,NAVY,true);name.setGravity(Gravity.CENTER);a.addView(name,p(-1,-2));a.setOnClickListener(q->open(val(v,"target")));return a;
 }
 void standard(){
  TextView back=text("‹  "+val(data,"title"),21,NAVY,true);back.setOnClickListener(v->{if("DIRECTORIO".equals(screenId()))open("INICIO");else finish();});body.addView(back,p(-1,-2));gap(body,12);
  ImageView image=picture(val(data,"image"),170,ImageView.ScaleType.CENTER_CROP);
  if(image!=null){image.setBackground(round(WHITE,12));body.addView(image,p(-1,screenId().equals("CARRERAS")?100:168));gap(body,13);}
  String subtitle=val(data,"subtitle");if(!subtitle.isEmpty()){addText(body,subtitle,14,MUTED,false);gap(body,13);}
  JSONArray sections=data.optJSONArray("sections");if(sections!=null)for(int i=0;i<sections.length();i++)section(sections.optJSONObject(i));
  JSONArray cards=data.optJSONArray("cards");if(cards!=null)for(int i=0;i<cards.length();i++)list(cards.optJSONObject(i),i);
  JSONArray actions=data.optJSONArray("actions");if(actions!=null)for(int i=0;i<actions.length();i++)action(actions.optJSONObject(i),i==0);
  gap(body,4);addText(body,"ⓘ  Guía informativa; confirma los datos y trámites en los canales oficiales.",12,MUTED,false);
 }
 void section(JSONObject o){if(o==null)return;LinearLayout box=column();box.setBackground(border(WHITE));box.setPadding(dp(15),dp(14),dp(15),dp(13));addText(box,val(o,"heading"),17,BLUE,true);gap(box,9);JSONArray paragraphs=o.optJSONArray("paragraphs");if(paragraphs!=null)for(int i=0;i<paragraphs.length();i++){addText(box,paragraphs.optString(i),15,INK,false);gap(box,8);}body.addView(box,p(-1,-2));gap(body,12);}
 void list(JSONObject o,int i){if(o==null)return;LinearLayout item=row();item.setPadding(dp(9),dp(8),dp(9),dp(8));int[] bg={0xFFDFEDFF,0xFFE3F5E8,0xFFFFEDDB,0xFFFCE4E5,0xFFEAE3FF,0xFFD9F0FA,0xFFE0F4EE};item.setBackground(border(bg[i%bg.length]));ImageView pic=picture(val(o,"image"),60,ImageView.ScaleType.CENTER_CROP);if(pic!=null)item.addView(pic,p(81,65));else{TextView ico=text("▣",24,BLUE,true);ico.setGravity(Gravity.CENTER);item.addView(ico,p(46,54));}
 LinearLayout labels=column();labels.setPadding(dp(9),0,dp(5),0);addText(labels,val(o,"title"),15,NAVY,true);gap(labels,4);addText(labels,val(o,"text"),12,MUTED,false);item.addView(labels,new LinearLayout.LayoutParams(0,-2,1));TextView chevron=text("›",25,NAVY,true);item.addView(chevron,p(18,48));item.setOnClickListener(v->open(val(o,"target")));body.addView(item,p(-1,-2));gap(body,9);}
 void action(JSONObject o,boolean main){if(o==null)return;TextView b=text(val(o,"label"),14,main?WHITE:BLUE,true);b.setGravity(Gravity.CENTER);b.setPadding(dp(12),dp(14),dp(12),dp(14));b.setBackground(main?round(BLUE,12):border(WHITE));String id=val(o,"target"),url=val(o,"url");b.setOnClickListener(v->{if(!id.isEmpty())open(id);else if(!url.isEmpty())external(url);});body.addView(b,p(-1,-2));gap(body,10);}
 void navigation(LinearLayout page){LinearLayout row=row();row.setBackgroundColor(WHITE);row.setPadding(dp(5),dp(6),dp(5),dp(7));String[] id={"INICIO","CARRERAS","INFORMES","CONTACTO"}, names={"Inicio","Especialidades","Informes","Contacto"}, symbols={"⌂","✦","▤","✉"};for(int i=0;i<4;i++){final String target=id[i];LinearLayout b=column();b.setGravity(Gravity.CENTER);TextView ico=text(symbols[i],22,id[i].equals(screenId())?BLUE:MUTED,true);ico.setGravity(Gravity.CENTER);b.addView(ico,p(-1,29));TextView t=text(names[i],11,id[i].equals(screenId())?BLUE:MUTED,false);t.setGravity(Gravity.CENTER);b.addView(t,p(-1,-2));row.addView(b,new LinearLayout.LayoutParams(0,-2,1));b.setOnClickListener(v->open(target));}page.addView(row,p(-1,66));}
 void open(String id){Class<? extends Activity> c=classes.get(id);if(c==null){Toast.makeText(this,"Sección no disponible",Toast.LENGTH_SHORT).show();return;}if(id.equals(screenId()))return;Intent it=new Intent(this,c);if(id.equals("INICIO"))it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);startActivity(it);}
 void external(String address){try{startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(address)));}catch(ActivityNotFoundException|SecurityException e){Toast.makeText(this,"No se puede abrir el enlace",Toast.LENGTH_SHORT).show();}}
}
