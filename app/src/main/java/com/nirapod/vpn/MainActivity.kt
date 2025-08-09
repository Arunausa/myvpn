package com.nirapod.vpn

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.nirapod.vpn.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvPrivacy.setOnClickListener {
            startActivity(Intent(this, PrivacyActivity::class.java))
        }

        binding.btnConnect.setOnClickListener { onConnectClicked() }
        binding.btnDisconnect.setOnClickListener { onDisconnectClicked() }
    }

    private fun onConnectClicked() {
        binding.tvStatus.text = "Preparing profile..."
        val ovpnFile = copyAssetToCache("client.ovpn")
        if (ovpnFile == null) {
            binding.tvStatus.text = "Profile missing"
            return
        }

        val uri = FileProvider.getUriForFile(this, "com.nirapod.vpn.fileprovider", ovpnFile)
        val viewIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/x-openvpn-profile")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            clipData = ClipData.newRawUri("ovpn", uri)
        }

        if (isPackageInstalled("de.blinkt.openvpn")) {
            try {
                startActivity(viewIntent)
                binding.tvStatus.text = "Handing over to OpenVPN for Android..."
            } catch (t: Throwable) {
                binding.tvStatus.text = "Unable to import profile"
            }
        } else {
            binding.tvStatus.text = "Installing OpenVPN for Android..."
            openPlayStoreForPackage("de.blinkt.openvpn")
        }
    }

    private fun onDisconnectClicked() {
        // Open OpenVPN for Android so user can disconnect
        val launchIntent = packageManager.getLaunchIntentForPackage("de.blinkt.openvpn")
        if (launchIntent != null) {
            startActivity(launchIntent)
        } else {
            openPlayStoreForPackage("de.blinkt.openvpn")
        }
    }

    private fun copyAssetToCache(assetName: String): File? {
        return try {
            val outFile = File(cacheDir, assetName)
            assets.open(assetName).use { input ->
                outFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            outFile
        } catch (e: Exception) {
            null
        }
    }

    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openPlayStoreForPackage(packageName: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }
}