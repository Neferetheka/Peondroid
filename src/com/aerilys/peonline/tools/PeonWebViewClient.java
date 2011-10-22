package com.aerilys.peonline.tools;

import com.aerilys.peonline.Main;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PeonWebViewClient extends WebViewClient{
	ProgressDialog dialog;
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	view.loadUrl(url);
	return true;
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon)
	{
		if(!url.contains("connexion.php"))
		dialog = ProgressDialog.show(view.getContext(), "", 
                "Chargement de la page en cours...", true);
	}
	
	@Override
	public void onPageFinished(WebView view, String url)
	{
		dialog.cancel();
	}
	
	@Override 
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
	{
		dialog.cancel();
		Main.alert(view.getContext(), "Une erreur est survenue lors du chargement de la page. Veuillez vérifier votre connexion internet !", 3000);
	}
}
