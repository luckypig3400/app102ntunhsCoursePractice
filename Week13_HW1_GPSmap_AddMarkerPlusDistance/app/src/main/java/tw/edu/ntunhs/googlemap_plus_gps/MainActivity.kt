package tw.edu.ntunhs.googlemap_plus_gps

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private lateinit var textView1: TextView
private lateinit var longtitudeOutput: TextView
private lateinit var latitudeOutput: TextView
private lateinit var altitudeOutput: TextView
private lateinit var addressOutput: TextView
private lateinit var collegeDistanceText: TextView
private lateinit var seniorHighDistanceText: TextView
private lateinit var juniorHighDistanceText: TextView
private lateinit var elementaryDistanceText: TextView
private lateinit var mMap: GoogleMap
private lateinit var gc: Geocoder

// store current Location as Global var
private var currantLocation = LatLng(23.9037, 121.0794)
// Taiwan: 23.9037° N, 121.0794° E

// 學校的經緯度座標
private val dunhuaElementaryLocation = LatLng(25.0492, 121.5481)
private val dunhuaJuniorLocation = LatLng(25.0509, 121.5465)
private val daanLocation = LatLng(25.0320, 121.5430)
private val ntunhsLocation = LatLng(25.11787771539104, 121.52147304364689)

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

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
        longtitudeOutput = findViewById(R.id.longtitudeOutput)
        latitudeOutput = findViewById(R.id.latitudeOutput)
        altitudeOutput = findViewById(R.id.heightOutput)
        addressOutput = findViewById(R.id.addressOutput)
        collegeDistanceText = findViewById(R.id.collegeDistanceOutput)
        seniorHighDistanceText = findViewById(R.id.seniorHighDistanceOutput)
        juniorHighDistanceText = findViewById(R.id.juniorHighDistanceOutput)
        elementaryDistanceText = findViewById(R.id.elementaryDistanceOutput)

        // 檢查是否已取得GPS定位權限
        if (!foregroundPermissionApproved()) {
            requestForegroundPermissions()
        }

        myReceiver = MyReceiver()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap //將googleMap傳給外部變數mMap，讓fun外可以使用地圖

        // 地址顯示繁體中文
        gc = Geocoder(this, Locale.TRADITIONAL_CHINESE)

        moveMapToNTUNHS()
        markDaanOnMap()
        markDunhuaJunior()
        markDunhuaElementary()

        drawLinesOnMap()
    }

    fun moveMapToNTUNHS() {
        // Add a marker in NTUNHS and move the camera
        mMap.addMarker(
            MarkerOptions()
                .position(ntunhsLocation)
                .title("國北護校本部")
                .snippet("裡面居然有資訊管理系耶~")
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on))
                .draggable(false))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ntunhsLocation))
    }

    fun markDaanOnMap() {
        mMap.addMarker(
            MarkerOptions()
                .title("臺北市立大安高級工業職業學校")
                .snippet("簡稱大安高工,又名男子監獄")
                .position(daanLocation)
                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_big_on))
                .anchor(0.5f, 0.5f)
                .draggable(false)
                .flat(true)
        )
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(daanLocation))
    }

    fun markDunhuaJunior(){
        mMap.addMarker(
            MarkerOptions()
                .title("臺北市立敦化國民中學")
                .snippet("簡稱敦化國中,以資源丙班聞名整個北台灣")
                .position(dunhuaJuniorLocation)
                .anchor(0.5f, 0.5f)
                .draggable(false)
        )
    }

    fun markDunhuaElementary(){
        mMap.addMarker(
            MarkerOptions()
                .title("臺北市松山區敦化國民小學")
                .snippet("簡稱敦化國小,以音樂班聞名全台")
                .position(dunhuaElementaryLocation)
                .draggable(false)
        )
    }
    /*
    Google Map 顯示位置範例程式碼
    範例1:
        mMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(25.118, 121.521)))
    範例2:
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(25.118, 121.521)) // Sets the LatLng
            .zoom(14f) // Sets the zoom
            .bearing(0f) // Sets the orientation of the camera to east
            .tilt(0f) // Sets the tilt of the camera to 30 degrees
            .build() // Creates a CameraPosition from the builder
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    // https://developers.google.com/maps/documentation/android-sdk/intro
     */

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

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(myReceiver, IntentFilter(ForegroundService.ACTION_BROADCAST))
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
                textView1.text =
                    "(" + location.longitude + ", " + location.latitude + ", " + location.altitude + ", " + location.accuracy + ", " + convertLongToTime(
                        location.time
                    ) + ")"
                // https://developer.android.com/reference/kotlin/android/location/Location#gettime

                textView1.visibility = View.INVISIBLE
                // https://stackoverflow.com/questions/49402001/how-to-set-visibility-in-kotlin

                longtitudeOutput.text = "經度:" + location.longitude
                latitudeOutput.text = "緯度:" + location.latitude
                altitudeOutput.text = "高度:" + location.altitude

                // Update Global var currentLocation
                currantLocation = LatLng(location.latitude, location.longitude)
                drawLinesOnMap()
                moveMapToCurrentLocation(location.latitude, location.longitude)

                // 自經緯度取得地址
                val lstAddress: List<Address> =
                    gc.getFromLocation(location.latitude, location.longitude, 1)
                // 擷取最主要的地理格式[0]
                addressOutput.text = "地址:" + lstAddress[0].getAddressLine(0) + "\n"

            }
        }

        private fun convertLongToTime(time: Long): Any? {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            return format.format(date)
        }
        // https://stackoverflow.com/questions/49551461/how-can-i-convert-a-long-value-to-date-time-and-convert-current-time-to-long-kot

        fun moveMapToCurrentLocation(in_latitude: Double, in_longtitude: Double) {
            // Add a marker in current location
            val currentLocation = LatLng(in_latitude, in_longtitude)
            mMap.addMarker(MarkerOptions().position(currentLocation).title("您目前所在位置"))

            // 使用另一種寫法，Map的鏡頭縮放等參數均可調整
            val cameraPosition = CameraPosition.Builder()
                .target(currentLocation) // Sets the LatLng
                .zoom(18f) // Sets the zoom
                .bearing(0f) // Sets the orientation of the camera to east
                .tilt(0f) // Sets the tilt of the camera to 30 degrees
                .build() // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }
// End ForeGround Service   Android 10+ 定位需要改放在前景服務中	/////////////

    // Begin Permission	//////////////////////////////////////////////////////////////////////
    private val TAG = "MainActivity"
    private val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

    // 檢查是否已取得GPS定位權限
    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    // 請求GPS定位權限
    private fun requestForegroundPermissions() {
        val provideRationale = foregroundPermissionApproved()

        if (provideRationale) {
            Snackbar.make(
                findViewById(R.id.activity_main),
                R.string.permission_rationale,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.ok) {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
                    )
                }.show()
        } else {
            Log.d(TAG, "Request foreground only permission")
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    // 請求定位權限的結果(回覆)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
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
                    Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_LONG
                    )
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

fun drawLinesOnMap(){
    val yellowlineOpt = PolylineOptions()
        .width(18f)
        .color(Color.YELLOW)
    val orangelineOpt = PolylineOptions()
        .width(15f)
        .color(Color.rgb(255,165,0))
    val cyanlineOpt = PolylineOptions()
        .width(15f)
        .color(Color.CYAN)
    val graylineOpt = PolylineOptions()
        .width(12f)
        .color(Color.GRAY)

    val lineToNtunhs = ArrayList<LatLng>()
    lineToNtunhs.add(currantLocation)
    lineToNtunhs.add(ntunhsLocation)

    val lineToDaan = ArrayList<LatLng>()
    lineToDaan.add(currantLocation)
    lineToDaan.add(daanLocation)

    val lineToDunhuaJunior = ArrayList<LatLng>()
    lineToDunhuaJunior.add(currantLocation)
    lineToDunhuaJunior.add(dunhuaJuniorLocation)

    val lineToDunhuaElementary = ArrayList<LatLng>()
    lineToDunhuaElementary.add(currantLocation)
    lineToDunhuaElementary.add(dunhuaElementaryLocation)

    yellowlineOpt.addAll(lineToNtunhs)
    orangelineOpt.addAll(lineToDaan)
    cyanlineOpt.addAll(lineToDunhuaJunior)
    graylineOpt.addAll(lineToDunhuaElementary)

    mMap.addPolyline(yellowlineOpt)
    mMap.addPolyline(orangelineOpt)
    mMap.addPolyline(cyanlineOpt)
    mMap.addPolyline(graylineOpt)
}

// 平均地球半徑； 長軸半徑6378137m 短軸半徑6356752.314m
var Earth_Radius = 6371000.0
// 計算GPS兩點間之平面距離
// 距離不大下，可用 XY2*Math.PI/180 ，但距離很遠下，用以下公式會比較準
fun TwoGPSPointsDistance(X1: Double, Y1: Double, X2: Double, Y2: Double): Double {
    return Earth_Radius * Math.acos(Math.sin(Y2 * Math.PI / 180) * Math.sin(Y1 * Math.PI / 180)
            + Math.cos(Y2 * Math.PI / 180) * Math.cos(Y1 * Math.PI / 180) * Math.cos(X1 * Math.PI / 180 -
            X2 * Math.PI / 180))
}
