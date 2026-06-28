

package com.example.finpilotai.ui.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DashboardScreen(
    smsCount: Int
) {

    when (smsCount) {

        -1 -> Text("SMS Permission Denied")

        else -> Text("Total SMS : $smsCount")

    }

}