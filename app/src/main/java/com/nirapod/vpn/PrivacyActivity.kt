package com.nirapod.vpn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nirapod.vpn.databinding.ActivityPrivacyBinding

class PrivacyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Privacy Policy"

        val policy = """
Nirapod VPN Privacy Policy
Your privacy is extremely important to us. This Privacy Policy explains how Nirapod VPN (Package Name: com.nirapod.vpn) collects, uses, and protects your information. By using our service, you agree to the terms of this policy.

1. We Do Not Collect Personally Identifiable Information
We do not keep any logs of your online activity. This means:

Your browsing history

Your traffic destinations

Your DNS queries

Your real IP address

Your connection timestamps

None of the above information is ever stored on our servers. Our "No-Logs" policy is very strict, and we are committed to upholding it.

2. What Information We Do Collect (and why)
To improve the functionality of your app, we may collect some non-personal or anonymous information. This data does not in any way reveal your personal identity. This information includes:

App Crash Reports: If the app closes unexpectedly, we may collect crash reports to fix errors.

Anonymous Usage Data: We may collect anonymous data to understand which servers are most used or which features are popular. This helps us to improve our service.

This data is never linked to your personal information.

3. Data Security
We implement the highest security measures to protect your privacy. We use industry-standard encryption protocols to encrypt your internet traffic. Since we do not keep any personal logs, your data remains secure.

4. Third-Party Services
Our app does not directly share personal information with any third-party services. However, to ensure the functionality and stability of the app, we may use some services, such as crash reporting tools like Firebase Crashlytics. These services also use anonymous data and have their own privacy policies.

5. Changes to This Policy
We may update this Privacy Policy from time to time. In case of any changes, we will notify you through the app or a notice on our website. If you continue to use our service after the new policy becomes effective, it will be assumed that you have accepted those changes.

6. Contact Us
If you have any questions about our privacy policy, please contact us.

Email: support@nirapodvpn.com
""".trimIndent()

        binding.privacyText.text = policy
    }
}