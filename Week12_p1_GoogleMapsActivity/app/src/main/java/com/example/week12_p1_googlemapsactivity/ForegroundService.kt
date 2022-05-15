package com.example.week12_p1_googlemapsactivity

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*

class ForegroundService : Service() {

    // 通知頻道的名稱
    private val CHANNEL_ID = "channel_01"

    private val mBinder: IBinder = LocalBinder()

    // 前台服務顯示的通知ID
    private val NOTIFICATION_ID = 12345678

    //  用於檢查綁定的活動是否真的消失了，並且在定向更改中沒有取消綁定。 僅當前者發生時，我們才創建前者服務通知。   Used to check whether the bound activity has really gone away and not unbound as part of an orientation change. We create a foreground service notification only if the former takes place.
    private var mChangingConfiguration = false

    private var mNotificationManager: NotificationManager? = null

    //  FusedLocationProviderApi所需的參數
    private var mLocationRequest: LocationRequest? = null
    //private lateinit var mLocationRequest: LocationRequest

    //  提供權限給 融合位置提供者API (Fused Location Provider API)
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    // 回傳位置改變的訊息
    private var mLocationCallback: LocationCallback? = null

    private var mServiceHandler: Handler? = null

    //  現在位置
    private var mLocation: Location? = null

    companion object {

        private val PACKAGE_NAME = "tw.edu.ntunhs.fusedlocation"
        private val TAG = ForegroundService::class.java.simpleName

        internal val ACTION_BROADCAST = "$PACKAGE_NAME.broadcast"
        internal val EXTRA_LOCATION = "$PACKAGE_NAME.extra.LOCATION"
        private val EXTRA_STARTED_FROM_NOTIFICATION = "$PACKAGE_NAME.started_from_notification"

    }

    override fun onCreate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                onNewLocation(locationResult.lastLocation)
            }
        }
        createLocationRequest()
        getLastLocation()

        val handlerThread = HandlerThread(TAG)
        handlerThread.start()
        mServiceHandler = Handler(handlerThread.looper)
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager?

        // Android 8+ (O)需要一個通知頻道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            // 建立通知頻道
            val mChannel =
                NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            // 為通知管理器設定通知頻道
            mNotificationManager!!.createNotificationChannel(mChannel)
        }
    }

    // Service啟動時會執行    onCreate() --> onStartCommand() --> onDestroy()
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //Log.i(TAG, "Service started");
        val startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false)
        // 若使用者(在通知中)決定移除位置更新，則刪除位置更新。      (應該無作用了，因為已將通知中的選項刪除)
        if (startedFromNotification) {
            removeLocationUpdates()
            stopSelf()
        }
        // 告訴系統在終止服務後不要嘗試重新創建該服務。
        return START_NOT_STICKY
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mChangingConfiguration = true
    }

    override fun onBind(intent: Intent?): IBinder? {
        // 當App(MainActivity)成為前景時，綁定服務。
        // 發生這種情況時，服務應不再是前台服務。 (這句話的意思應該是，當App運作時，服務變成在後面執行) The service should cease to be a foreground service when that happens.
        //Log.i(TAG, "in onBind()");
        stopForeground(true)
        mChangingConfiguration = false
        return mBinder // 回傳在類別中建立的LocalBinder類別的物件
    }

    override fun onRebind(intent: Intent?) {
        // 當App(MainActivity)再次由背景變成前景時
        //Log.i(TAG, "in onRebind()");
        stopForeground(true)
        mChangingConfiguration = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        //Log.i(TAG, "Last client unbound from service");
        // 當App最後與該服務解除綁定時。
        // 如果由於MainActivity中的配置更改而調用了此方法，則我們什麼也不做。 否則，我們將此服務設為前台服務。  (這句話的意思應該是，當App不在前景運作時，服務變成在前景執行通知)    If this method is called due to a configuration change in MainActivity, we do nothing. Otherwise, we make this service a foreground service.
//        if (!mChangingConfiguration && Utils.requestingLocationUpdates(this)) {
//            Log.i(TAG, "Starting foreground service");
//            startForeground(NOTIFICATION_ID, getNotification());
//        }
        return true
    }

    override fun onDestroy() {
        mServiceHandler!!.removeCallbacksAndMessages(null)
    }

    //	發出位置更新的請求。
    //@SuppressLint("MissingPermission")
    fun requestLocationUpdates() {
//        Log.i(TAG, "Requesting location updates");
//        Utils.setRequestingLocationUpdates(this, true);
        startService(Intent(getApplicationContext(), ForegroundService::class.java))

        try {
            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback, Looper.myLooper()
            )
        } catch (unlikely: SecurityException) {
//            Utils.setRequestingLocationUpdates(this, false);
//            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }

    //	移除位置更新的請求。
    fun removeLocationUpdates() {
//        Log.i(TAG, "Removing location updates");
        try {
            mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
            //            Utils.setRequestingLocationUpdates(this, false);
            stopSelf()
        } catch (unlikely: SecurityException) {
//            Utils.setRequestingLocationUpdates(this, true);
//            Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }

    private fun getLastLocation() {
        try {
            mFusedLocationClient!!.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLocation = task.result
                    } else {
                        //Log.w(TAG, "Failed to get location.");
                    }
                }
        } catch (unlikely: SecurityException) {
            //Log.e(TAG, "Lost location permission." + unlikely);
        }
    }

    private fun onNewLocation(location: Location?) {
        //Log.i(TAG, "New location: " + location);
        mLocation = location

        // 通知正在收聽有關新位置廣播的任何人。
        val intent = Intent(ACTION_BROADCAST)
        intent.putExtra(EXTRA_LOCATION, location)
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent)

        // 如果作為前台服務運行，則更新通知內容。
//        if (serviceIsRunningInForeground(this)) {
//            mNotificationManager.notify(NOTIFICATION_ID, getNotification());
//        }
    }

    //  設置位置請求參數
    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000 //  所需的位置更新間隔。 不精確 更新可能會或多或少地頻繁。
        mLocationRequest!!.fastestInterval = 1000 //  活動位置更新的最快速度。 更新永遠不會比此值更頻繁。
        // 定位方式	(0:LocationManager的GPS定位[最準確], 1:LcationRequest的HIGH_ACCURACY, 2:LcationRequest的BALANCED_POWER_ACCURACY, 3:LcationRequest的LOW_POWER, 4:LcationRequest的NO_POWER)
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        //mLocationRequest!!.priority = LocationRequest.PRIORITY_LOW_POWER
        //mLocationRequest!!.priority = LocationRequest.PRIORITY_NO_POWER
    }

    //  類別用於客戶端Binder。 由於此服務與其客戶端在同一進程中運行，因此我們不需要處理IPC。
    inner class LocalBinder : Binder() {
        //val service: ForegroundService
        internal val service: ForegroundService
            get() = this@ForegroundService
    }

    //  如果這是前景服務，則返回true。
    fun serviceIsRunningInForeground(context: Context): Boolean {
        val manager = context.getSystemService(
            ACTIVITY_SERVICE
        ) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (javaClass.name == service.service.className) {
                if (service.foreground) {
                    return true
                }
            }
        }
        return false
    }

}