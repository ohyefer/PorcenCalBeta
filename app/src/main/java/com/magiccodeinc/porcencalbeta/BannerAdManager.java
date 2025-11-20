package com.magiccodeinc.porcencalbeta;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BannerAdManager {
    private AdView adView;
    private final Activity activity;
    private final FrameLayout adContainer;

    public BannerAdManager(Activity activity, FrameLayout adContainer) {
        this.activity = activity;
        this.adContainer = adContainer;
    }

    public void loadAd() {
        if (adView != null) {
            adContainer.removeView(adView);
        }
        adView = new AdView(activity);
        adView.setAdUnitId("ca-app-pub-3987601077442804/3639545882"); // Cambia por tu ID real
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adContainer.addView(adView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Obtiene el ancho de la pantalla en píxeles
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);

        // Retorna el tamaño adaptativo recomendado
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public void onResume() {
        if (adView != null) {
            adView.resume();
        }
    }

    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
    }

    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
    }
}
