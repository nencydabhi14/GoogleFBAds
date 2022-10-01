package com.example.adds

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.adds.databinding.ActivityFbaddBinding
import com.facebook.ads.*


class FBAdd_Activity : AppCompatActivity() {

    lateinit var fbaddActivity: ActivityFbaddBinding
    private val TAG = "NativeAdActivity".javaClass.simpleName
    private var nativeAd: NativeAd? = null
    private var nativeAdLayout: NativeAdLayout? = null
    private var adView: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        fbaddActivity = ActivityFbaddBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(fbaddActivity.root)



        NativeAdds()
        bannerFB()
        Banner_rectangle()

        fbaddActivity.interstitialAdFB.setOnClickListener {
            intertitialFB()
        }
        fbaddActivity.rewardVideoAddFB.setOnClickListener {
            rewardVideoFB()
        }

    }

    fun Banner_rectangle() {

        var adView = AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.RECTANGLE_HEIGHT_250)
        fbaddActivity.bannerContainerRectangle.addView(adView)
        adView.loadAd()
    }

    fun NativeAdds() {
        AudienceNetworkAds.initialize(this)
        nativeAd = NativeAd(this, "643814243608173_643816223607975")

        val nativeAdListener: NativeAdListener = object : NativeAdListener {
            override fun onMediaDownloaded(ad: Ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.")
            }

            override fun onError(ad: Ad?, adError: AdError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!")
            }

            override fun onAdClicked(ad: Ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!")
            }
        }
        nativeAd!!.loadAd(
            nativeAd!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .build()
        )

    }


//    private fun inflateAd(nativeAd: NativeAd) {
//        nativeAd.unregisterView()
//        val inflater = LayoutInflater.from(this)
//
//        adView =
//            inflater.inflate(R.layout.simple_list_item_activated_1, nativeAdLayout, false) as LinearLayout
//        nativeAdLayout!!.addView(adView)
//
//
//        val adChoicesContainer = findViewById<LinearLayout>(R.id.ad_choices_container)
//        val adOptionsView = AdOptionsView(this, nativeAd, nativeAdLayout)
//        adChoicesContainer.removeAllViews()
//        adChoicesContainer.addView(adOptionsView, 0)
//
//
//        val nativeAdIcon: MediaView = adView!!.findViewById(R.id.native_ad_icon)
//        val nativeAdTitle = adView!!.findViewById<TextView>(R.id.native_ad_title)
//        val nativeAdMedia: MediaView = adView!!.findViewById(R.id.native_ad_media)
//        val nativeAdSocialContext = adView!!.findViewById<TextView>(R.id.native_ad_social_context)
//        val nativeAdBody = adView!!.findViewById<TextView>(R.id.native_ad_body)
//        val sponsoredLabel = adView!!.findViewById<TextView>(R.id.native_ad_sponsored_label)
//        val nativeAdCallToAction: Button = adView!!.findViewById(R.id.native_ad_call_to_action)
//
//
//        nativeAdTitle.text = nativeAd.advertiserName
//        nativeAdBody.text = nativeAd.adBodyText
//        nativeAdSocialContext.text = nativeAd.adSocialContext
//        nativeAdCallToAction.setVisibility(if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE)
//        nativeAdCallToAction.setText(nativeAd.adCallToAction)
//        sponsoredLabel.text = nativeAd.sponsoredTranslation
//
//
//        val clickableViews: MutableList<View> = ArrayList()
//        clickableViews.add(nativeAdTitle)
//        clickableViews.add(nativeAdCallToAction)
//
//
//        nativeAd.registerViewForInteraction(
//            adView, nativeAdMedia, nativeAdIcon, clickableViews
//        )
//    }

    fun bannerFB() {
        var adView = AdView(
            this,
            "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
            AdSize.BANNER_HEIGHT_90
        )
        fbaddActivity.bannerContainer.addView(adView)

        adView.loadAd()
    }

    fun intertitialFB() {
        AudienceNetworkAds.initialize(this)


        var interstitialAd = InterstitialAd(this, "643814243608173_643816053607992")

        val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                Log.e(TAG, "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad) {
                Log.e(TAG, "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad?, adError: AdError) {
                Log.e(TAG, "Interstitial ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad) {
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
                // Show the ad
                interstitialAd.show()
            }

            override fun onAdClicked(ad: Ad) {
                Log.d(TAG, "Interstitial ad clicked!")
            }

            override fun onLoggingImpression(ad: Ad) {
                Log.d(TAG, "Interstitial ad impression logged!")
            }
        }

        interstitialAd.loadAd(
            interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build()
        )
    }

    fun rewardVideoFB() {
        AudienceNetworkAds.initialize(this)
        var rewardedVideoAd = RewardedVideoAd(this, "643814243608173_643816393607958")
        var rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                TODO("Not yet implemented")
            }

            override fun onAdLoaded(p0: Ad?) {
                rewardedVideoAd.show()
            }

            override fun onAdClicked(p0: Ad?) {
                TODO("Not yet implemented")
            }

            override fun onLoggingImpression(p0: Ad?) {}
            override fun onRewardedVideoCompleted() {
                TODO("Not yet implemented")
            }

            override fun onRewardedVideoClosed() {
                TODO("Not yet implemented")
            }
        }
        rewardedVideoAd.loadAd(
            rewardedVideoAd.buildLoadAdConfig().withAdListener(rewardedVideoAdListener).build()
        )
    }
}