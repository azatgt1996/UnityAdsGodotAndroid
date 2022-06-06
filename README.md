# Unity Ads Plugin for Godot 
This is an Android Unity Ads plugin for Godot Game Engine 3.4.3+. 

### Supported features:
- Banner
- Interstitial ads
- Rewarded ads

## Getting started
- Download `UnityAdsGodotAndroid-release.aar` and `UnityAdsGodotAndroid.gdap` from `Godot` folder.
- Move the plugin configuration file (`UnityAdsGodotAndroid.gdap`) and the binary (`UnityAdsGodotAndroid-release.aar`) downloaded from the previous step to the Godot project's res://android/plugins directory.
- Enable plugin by accessing `Project` -> `Export`, Plugins section. Follow the [image](https://docs.godotengine.org/en/stable/_images/android_export_preset_plugins_section.png).


### How to use
First step is plugin initialization
```gdscript
var unity_ad
# Check if plugin was added to the project
if Engine.has_singleton("UnityAdsGodotAndroid"):
  unity_ad = Engine.get_singleton("UnityAdsGodotAndroid")
  unity_ad.initialise("1234567", true) # project id and TestMode enabled
  unity_ad.connect("onUnityBannerFailedToLoad", self, "_on_banner_failed_to_load")
  
  unity_ad.connect("onUnityInterstitialAdsLoaded", self, "_on_interstitial_loaded")
  unity_ad.connect("onUnityInterstitialAdsFailedToLoad", self, "_on_interstitial_failed_to_load")
  unity_ad.connect("onUnityInterstitialAdsShowComplete", self, "_on_interstitial_closed")
  
  unity_ad.connect("onUnityRewardedAdsLoaded", self, "_on_rewarded_loaded")
  unity_ad.connect("onUnityRewardedAdsFailedToLoad", self, "_on_rewarded_failed_to_load")
  unity_ad.connect("onUnityRewardedAdsShowComplete", self, "_on_rewarded")
  
  
```
After what plugin was initialized you can use supported features
#### Banner

```gdscript
#show banner
unity_ad.showBanner("Banner_Android", false) #false - banner is bottom, true - banner is top

# Callbacks:
func _on_banner_failed_to_load(error_msg: String) -> void:
	print("Banner_failed_to_load: %s" % error_msg)

#hide banner  
unity_ad.hideBanner()

```
##### Interstitial ad
```gdscript
#load interstitial ad
unity_ad.loadInterstitialAd("Interstitial_Android")

# Callbacks:
func _on_interstitial_loaded():
	pass
	
func _on_interstitial_failed_to_load(error_msg):
	print("Interstitial_failed_to_load: %s" % error_msg)

#show interstitial ad
unity_ad.showInterstitialAd("Interstitial_Android")

# Callbacks:
func _on_interstitial_closed():
	pass

```
##### Rewarded ad
```gdscript
#load rewarded ad
unity_ad.loadRewardedAd("Rewarded_Android")

# Callbacks:
func _on_rewarded_loaded():
	pass
	
func _on_rewarded_failed_to_load(error_msg):
	print("Rewarded_failed_to_load: %s" % error_msg)

#show rewarded ad
unity_ad.showRewardedAd("Rewarded_Android")

# Callbacks:
func _rewarded():
	pass

```







