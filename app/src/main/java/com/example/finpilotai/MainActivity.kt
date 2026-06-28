package com.example.finpilotai

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.example.finpilotai.data.sms.SmsReader
import com.example.finpilotai.ui.dashboard.DashboardScreen
import com.example.finpilotai.ui.theme.FinPilotAITheme

class MainActivity : ComponentActivity() {

    private var smsCount by mutableIntStateOf(0)

    private val smsPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                loadSms()
            } else {
                smsCount = -1
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkSmsPermission()

        setContent {

            FinPilotAITheme {

                Surface {

                    when (smsCount) {
                        -1 -> Text("SMS Permission Denied")
                        else -> DashboardScreen(
                            smsCount = smsCount
                        )
                    }

                }

            }

        }
    }

    private fun checkSmsPermission() {

        when {

            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED -> {

                loadSms()

            }

            else -> {

                smsPermissionLauncher.launch(
                    Manifest.permission.READ_SMS
                )

            }

        }

    }

    private fun loadSms() {

        val smsReader = SmsReader(this)

        smsCount = smsReader.getAllMessages().size

    }
}