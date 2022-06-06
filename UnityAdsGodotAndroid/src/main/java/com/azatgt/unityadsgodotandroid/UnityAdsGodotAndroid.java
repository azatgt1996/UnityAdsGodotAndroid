package com.azatgt.unityadsgodotandroid;


import android.app.Activity;
import android.util.ArraySet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Set;

public class UnityAdsGodotAndroid extends GodotPlugin implements IUnityAdsInitializationListener {

    private final Activity activity; // The main activity of the game
    // This banner view object will be placed at the top/bottom of the screen:
    private BannerView banner;

    private FrameLayout layout = null; // Store the layout
    private FrameLayout.LayoutParams adParams = null; // Store the layout params

    private final SignalInfo onInitializationComplete = new SignalInfo("onInitializationComplete");
    private final SignalInfo onInitializationFailed = new SignalInfo("onInitializationFailed", String.class);

    private final SignalInfo onUnityInterstitialAdsAdLoaded = new SignalInfo("onUnityInterstitialAdsLoaded");
    private final SignalInfo onUnityInterstitialAdsFailedToLoad = new SignalInfo("onUnityInterstitialAdsFailedToLoad", String.class);
    //private final SignalInfo onUnityInterstitialAdsShowFailure = new SignalInfo("onUnityInterstitialAdsShowFailure", String.class);
    //private final SignalInfo onUnityInterstitialAdsShowStart = new SignalInfo("onUnityInterstitialAdsShowStart");
    //private final SignalInfo onUnityInterstitialAdsShowClick = new SignalInfo("onUnityInterstitialAdsShowClick");
    private final SignalInfo onUnityInterstitialAdsShowComplete = new SignalInfo("onUnityInterstitialAdsShowComplete");

    private final SignalInfo onUnityBannerLoaded = new SignalInfo("onUnityBannerLoaded");
    private final SignalInfo onUnityBannerFailedToLoad = new SignalInfo("onUnityBannerFailedToLoad", String.class);

    private final SignalInfo onUnityRewardedAdsAdLoaded = new SignalInfo("onUnityRewardedAdsLoaded");
    private final SignalInfo onUnityRewardedAdsFailedToLoad = new SignalInfo("onUnityRewardedAdsFailedToLoad", String.class);
    private final SignalInfo onUnityRewardedAdsShowComplete = new SignalInfo("onUnityRewardedAdsShowComplete");

    public UnityAdsGodotAndroid(Godot godot) {
        super(godot);
        this.activity = getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "UnityAdsGodotAndroid";
    }

    @Override
    public View onMainCreate(Activity activity) {
        layout = new FrameLayout(activity);
        return layout;
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(onInitializationComplete);
        signals.add(onInitializationFailed);

        signals.add(onUnityInterstitialAdsAdLoaded);
        signals.add(onUnityInterstitialAdsFailedToLoad);
        //signals.add(onUnityInterstitialAdsShowFailure);
        //signals.add(onUnityInterstitialAdsShowStart);
        //signals.add(onUnityInterstitialAdsShowClick);
        signals.add(onUnityInterstitialAdsShowComplete);

        signals.add(onUnityBannerLoaded);
        signals.add(onUnityBannerFailedToLoad);

        signals.add(onUnityRewardedAdsAdLoaded);
        signals.add(onUnityRewardedAdsFailedToLoad);
        signals.add(onUnityRewardedAdsShowComplete);

        return signals;
    }

    @UsedByGodot
    public void initialise(String appId, boolean testMode) {
        UnityAds.initialize(activity, appId, testMode, this);
    }

    @Override
    public void onInitializationComplete() {
        emitSignal(onInitializationComplete.getName());
    }

    @Override
    public void onInitializationFailed(UnityAds.UnityAdsInitializationError unityAdsInitializationError, String s) {
        emitSignal(onInitializationComplete.getName(), "Unity Ads initialization failed with error: [" + unityAdsInitializationError + "] " + s);
    }

    // Interstitial Ad
    @UsedByGodot
    public void loadInterstitialAd(String placementId) {
        UnityAds.load(placementId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                //UnityAds.show((Activity)getApplicationContext(), adUnitId, new UnityAdsShowOptions(), showListener);
                emitSignal(onUnityInterstitialAdsAdLoaded.getName());
                //showInterstitialAd(placementId);
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                //Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
                emitSignal(onUnityInterstitialAdsFailedToLoad.getName(),
                        "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
            }
        });
    }

    @UsedByGodot
    public void showInterstitialAd(String placementId) {
        UnityAds.show(activity, placementId, new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                //Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
                //emitSignal(onUnityInterstitialAdsShowFailure.getName(),"Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                //Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
                //emitSignal(onUnityInterstitialAdsShowStart.getName());
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                //Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
                //emitSignal(onUnityInterstitialAdsShowClick.getName());
            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                //Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
                emitSignal(onUnityInterstitialAdsShowComplete.getName());
            }
        });
    }

    // Banner Ad
    @UsedByGodot
    public void showBanner(final String placementID, final boolean isOnTop) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                banner = new BannerView(activity, placementID, new UnityBannerSize(320, 50));
                banner.setListener(new BannerView.IListener() {
                    @Override
                    public void onBannerLoaded(BannerView bannerAdView) {
                        // Called when the banner is loaded.
                        //Log.v("UnityAdsExample", "onBannerLoaded: " + bannerAdView.getPlacementId());
                        emitSignal(onUnityBannerLoaded.getName());
                    }

                    @Override
                    public void onBannerClick(BannerView bannerAdView) {
                        // Called when a banner is clicked.
                        //Log.v("UnityAdsExample", "onBannerClick: " + bannerAdView.getPlacementId());
                    }

                    @Override
                    public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {
                        //Log.e("UnityAdsExample", "Unity Ads failed to load banner for " + bannerView.getPlacementId() + " with error: [" + bannerErrorInfo.errorCode + "] " + bannerErrorInfo.errorMessage);
                        // Note that the BannerErrorInfo object can indicate a no fill (see API documentation).
                        emitSignal(onUnityBannerFailedToLoad.getName(),
                                "Unity Ads failed to load banner for " + bannerView.getPlacementId() +
                                        " with error: [" + bannerErrorInfo.errorCode + "] " + bannerErrorInfo.errorMessage);
                    }

                    @Override
                    public void onBannerLeftApplication(BannerView bannerAdView) {
                        // Called when the banner links out of the application.
                        //Log.v("UnityAdsExample", "onBannerLeftApplication: " + bannerAdView.getPlacementId());
                    }
                });

                layout = (FrameLayout)activity.getWindow().getDecorView().getRootView();
                adParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                adParams.gravity = isOnTop ? Gravity.TOP : Gravity.BOTTOM;
                banner.load();
                layout.addView(banner, adParams);
            }
        });
    }

    @UsedByGodot
    public void hideBanner() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (layout == null || adParams == null)	{
                    return;
                }
                layout.removeView(banner);
            }
        });
    }

    // Rewarded Ad
    @UsedByGodot
    public void loadRewardedAd(String placementId) {
        UnityAds.load(placementId, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                //UnityAds.show((Activity)getApplicationContext(), adUnitId, new UnityAdsShowOptions(), showListener);
                emitSignal(onUnityRewardedAdsAdLoaded.getName());
                //showRewardedAd(placementId);
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                //Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
                emitSignal(onUnityRewardedAdsFailedToLoad.getName(),
                        "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
            }
        });
    }

    @UsedByGodot
    private void showRewardedAd(String placementId) {
        UnityAds.show(activity, placementId, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                //Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                //Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                //Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
                if (state.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED)) {
                    // Reward the user for watching the ad to completion
                    emitSignal(onUnityRewardedAdsShowComplete.getName());
                }
            }
        });
    }
}
