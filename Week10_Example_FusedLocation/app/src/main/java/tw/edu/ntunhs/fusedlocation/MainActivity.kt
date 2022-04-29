package tw.edu.ntunhs.fusedlocation

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

private lateinit var textView1: TextView

class MainActivity : AppCompatActivity() {

    // Android 10+ 定位需要改放在前景服務中
    /// 廣播接收器 用於偵聽來自服務的廣播。
    private lateinit var myReceiver: MyReceiver
    /// 對用於獲取位置更新的服務的引用。
    var mService: ForegroundService? = null
    /// 跟踪服務的綁定狀態。
    var ServiceFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 從資源類別R中取得介面元件
        textView1 = findViewById<TextView>(R.id.textView1)
        textView1.text = getString(R.string.no_location_text)

        // 檢查是否已取得GPS定位權限
        if (!foregroundPermissionApproved()) {
            requestForegroundPermissions()
        }

        myReceiver = MyReceiver()
    }

    override fun onStart() {
        super.onStart()

        // 綁定服務。 如果服務處於前景模式，則會向服務發出信號，因為此App處於前景，所以服務為背景。
        val serviceIntent = Intent(this, ForegroundService::class.java)
        bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        if (ServiceFlag) {
            // 取消綁定服務。 這向服務發出信號，通知此App不再為前景，服務可以通過將其升級為前景服務來進行響應。
            unbindService(mServiceConnection)
            ServiceFlag = false
        }
        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, IntentFilter(ForegroundService.ACTION_BROADCAST))
    }


    override fun onPause() {
        super.onPause()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
    }


// Begin ForeGround Service   Android 10+ 定位需要改放在前景服務中	/////////////
    // 監視服務的連接狀態。
    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as ForegroundService.LocalBinder
            mService = binder.service
            mService!!.requestLocationUpdates()
            ServiceFlag = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mService = null
            ServiceFlag = false
        }
    }

    // 主程式由此接收來自ForeGround Service 的Location資料
    public class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val location = intent.getParcelableExtra<Location>(ForegroundService.EXTRA_LOCATION)
            if (location != null) {
                //Toast.makeText(context, "("+location.longitude+", "+location.latitude+")", Toast.LENGTH_SHORT).show();
                textView1.text = "("+location.longitude+", "+location.latitude+ ", " + location.altitude + ", " + location.accuracy + ", " + convertLongToTime(location.time) +  ")"
                // https://developer.android.com/reference/kotlin/android/location/Location#gettime
            }
        }

        private fun convertLongToTime(time: Long): Any? {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            return format.format(date)
        }
        // https://stackoverflow.com/questions/49551461/how-can-i-convert-a-long-value-to-date-time-and-convert-current-time-to-long-kot
    }
// End ForeGround Service   Android 10+ 定位需要改放在前景服務中	/////////////

// Begin Permission	//////////////////////////////////////////////////////////////////////
    private val TAG = "MainActivity"
    private val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
    // 檢查是否已取得GPS定位權限
    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // 請求GPS定位權限
    private fun requestForegroundPermissions() {
        val provideRationale = foregroundPermissionApproved()

        if (provideRationale) {
            Snackbar.make(findViewById(R.id.activity_main), R.string.permission_rationale, Snackbar.LENGTH_LONG)
                .setAction(R.string.ok) {
                    // Request permission
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE)
                }.show()
        }
        else {
            Log.d(TAG, "Request foreground only permission")
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE)
        }
    }

    // 請求定位權限的結果(回覆)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "onRequestPermissionResult")

        when (requestCode) {
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE -> when {
                grantResults.isEmpty() ->
                    // 如果請求權限時，用戶中斷對話方塊，則權限請求將被取消，並收到空陣列。
                    Log.d(TAG, "請求權限中斷。")

                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    // 權限被核准
                    mService?.requestLocationUpdates()

                else -> {
                    // 權限被拒絕
                    Snackbar.make(findViewById(R.id.activity_main), R.string.permission_denied_explanation, Snackbar.LENGTH_LONG)
                        .setAction(R.string.settings) {
                            // 在下方彈跳顯示一設定的對話列
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", getPackageName(), null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }.show()
                }
            }
        }
    }
// End Permission	////////////////////////////////////////////////////////////////////////

}