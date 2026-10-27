package com.rabimi.dadaad

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        setupHourlyWork()
    }

    private fun setupHourlyWork() {
        // Minimum periodic interval for WorkManager is 15 minutes. Here set to 1 Hour.
        val hourlyWorkRequest = PeriodicWorkRequestBuilder<HourlyNotificationWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "HourlyNotificationTask",
            ExistingPeriodicWorkPolicy.KEEP,
            hourlyWorkRequest
        )

        Toast.makeText(this, "1時間毎の通知タイマーをセットしました", Toast.LENGTH_SHORT).show()
    }
}
