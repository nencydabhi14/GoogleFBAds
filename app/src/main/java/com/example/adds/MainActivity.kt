package com.example.adds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.adds.databinding.ActivityMainBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var interstitialAd: InterstitialAd? = null
    var mRewardedAd: RewardedAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)


        bannerAdd()

        interstitial()
        interstitialAdOnClickButton()

        RewardAdd()
        RewardClick()

        nativeAdd()
        nativeADDCutome()

        binding.fbSetUp.setOnClickListener {
            FbPage()
        }

    }

    fun FbPage(){

        var intent = Intent(this,FBAdd_Activity::class.java)
        startActivity(intent)

    }

    fun nativeAdd() {
        val builder = AdLoader.Builder(this, "ca-app-pub-3940256099942544/1033173712")
            .forNativeAd { nativeAdd ->
                var view =
                    LayoutInflater.from(this).inflate(R.layout.nativeadd_item, null) as NativeAdView

                var bg_img = view.findViewById<ImageView>(R.id.bg_img)
                var img_logo = view.findViewById<ImageView>(R.id.img_logo)
                var title_txt = view.findViewById<TextView>(R.id.title_txt)
                var subtitle_txt = view.findViewById<TextView>(R.id.subtitle_txt)

                title_txt.setText(nativeAdd.headline)
                subtitle_txt.setText(nativeAdd.body)
                bg_img.setImageDrawable(nativeAdd.images[0].drawable)
                img_logo.setImageDrawable(nativeAdd.icon?.drawable)

                binding.ViewRelative.removeAllViews()
                binding.ViewRelative.addView(view)

            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.e("TAG", "onAdFailedToLoad: ${p0.message}")
                }
            }).build()
        builder.loadAd(AdRequest.Builder().build())
    }


    fun nativeADDCutome() {
        val builder =
            AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd { nativeAD ->
                    var view = LayoutInflater.from(this)
                        .inflate(R.layout.nativeadd_item, null) as NativeAdView

                    var bg_img = view.findViewById<ImageView>(R.id.bg_img)
                    var img_logo = view.findViewById<ImageView>(R.id.img_logo)
                    var title_txt = view.findViewById<TextView>(R.id.title_txt)
                    var subtitle_txt = view.findViewById<TextView>(R.id.subtitle_txt)

                    bg_img.setImageDrawable(nativeAD.images[0].drawable)
                    img_logo.setImageDrawable(nativeAD.icon?.drawable)
                    title_txt.setText(nativeAD.headline)
                    subtitle_txt.setText(nativeAD.body)


                    binding.ViewRelative.removeAllViews()
                    binding.ViewRelative.addView(view)

                }.withAdListener(object : AdListener() {

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)

                    Log.e("TAG,", "onAdFailedToLoad: $p0")
                }
            }).build()

        builder.loadAd(AdRequest.Builder().build())

    }

    private fun RewardAdd() {
        var rewardedReq = AdRequest.Builder().build()

        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            rewardedReq,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {

                }

                override fun onAdLoaded(p0: RewardedAd) {
                    mRewardedAd = p0
                }
            })
    }

    private fun interstitial() {
        var interstitialAdReq = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            interstitialAdReq,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    interstitialAd = p0
                }
            })
    }

    fun bannerAdd() {
        MobileAds.initialize(this) {}

        val BannerReq = AdRequest.Builder().build()
        binding.adView.loadAd(BannerReq)
    }

    fun interstitialAdOnClickButton() {

        binding.interstitialAd.setOnClickListener {

            if (interstitialAd != null) {
                interstitialAd!!.show(this)
            }
            interstitial()
        }

    }

    fun RewardClick() {

        binding.rewardAd.setOnClickListener {
            if (mRewardedAd != null) {
                mRewardedAd!!.show(this, OnUserEarnedRewardListener { rewardItem ->
                    Toast.makeText(this, "${rewardItem.type}", Toast.LENGTH_SHORT).show()
                })
            }
            RewardAdd()
        }
    }
}