package com.aerilys.peonline;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.aerilys.peonline.R;
import com.aerilys.peonline.tools.JavaScriptInterface;
import com.aerilys.peonline.tools.PeonWebViewClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity
{

  public static final String HOST = "http://www.peonline.legendarts.fr/";
  ProgressDialog dialog;
  public static Context context;
  public static NotificationManager mNotificationManager;
  WebView wv;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    String ns = Context.NOTIFICATION_SERVICE;
    mNotificationManager = (NotificationManager) getSystemService(ns);
    context = this;

    wv = (WebView) findViewById(R.id.webView);
    wv.setWebViewClient(new PeonWebViewClient());
    WebSettings webSettings = wv.getSettings();
    webSettings.setUseWideViewPort(true);
    wv.loadUrl(HOST);
    webSettings.setJavaScriptEnabled(true);
    wv.addJavascriptInterface(new JavaScriptInterface(this), "Android");

  }

  /* ALERT */

  public static void alert(Context context, String message, int duration)
  {
    (Toast.makeText(context, message, duration)).show();
  }

  public static void alertAPropos(Context context)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setMessage(" Peonline pour Android \n Version 1.0 \n Par Galaad").setCancelable(false).setPositiveButton(
        "OK", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int id)
          {
            dialog.cancel();
          }
        });
    AlertDialog alert = builder.create();
    alert.show();
  }

  /* MENU */

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle item selection
    switch (item.getItemId())
    {
      case R.id.menuAPropos:
        alertAPropos(this);
        return true;
      case R.id.menuQuit:
        dialogueQuitter();
        return true;
      case R.id.menuPrecedent:
        wv.goBack();
        return true;
      case R.id.menuSuivant:
        wv.goForward();
        return true;
      case R.id.menuActualiser:
        wv.reload();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void dialogueQuitter()
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Voulez-vous vraiment quitter vos péons ?").setCancelable(false).setPositiveButton(
        "Oui", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int id)
          {
            Main.this.finish();
          }
        }).setNegativeButton("Non", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface dialog, int id)
      {
        dialog.cancel();
      }
    });
    AlertDialog alert = builder.create();
    alert.show();
  }

  /* METHODES COMMUNES */

  public static String HttpRequest(String url)
  {

    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpGet httpget = new HttpGet(url);
    // Log.d("debug", url);
    HttpResponse response = null;
    try
    {
      response = httpClient.execute(httpget);
      HttpEntity entity = response.getEntity();
      if (entity != null)
      {
        Log.d("debug", "Entity not null");
        try
        {
          String responseString = EntityUtils.toString(entity);
          return responseString;
        }
        catch (ParseException e)
        {
          e.printStackTrace();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
      else
        Log.d("debug", "Entity null");

    }
    catch (ClientProtocolException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    Log.d("debug", "reponse");

    return null;
  }

  public void UpdateTextView(int textView, String texte)
  {
    TextView view = (TextView) findViewById(textView);
    view.setText(texte);
  }

}