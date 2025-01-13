package projectspm.aliviamente

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas

class VideoChamadaActivity : AppCompatActivity() {


    private val myAppId = "0d052252f38a45c7b204abbc0c4ff5d8"
    private var channelName: String? = null
    private val token = ""

    private var mRtcEngine: RtcEngine? = null

    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            Log.d("Agora", "Entrou no canal: $channel, uid: $uid")
            runOnUiThread {
                Toast.makeText(this@VideoChamadaActivity, "Entrou no canal com sucesso", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.d("Agora", "Usuário entrou: $uid")
            runOnUiThread {
                setupRemoteVideo(uid)
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.d("Agora", "Usuário saiu: $uid, motivo: $reason")
            runOnUiThread {
                Toast.makeText(this@VideoChamadaActivity, "Usuário saiu: $uid", Toast.LENGTH_SHORT).show()
            }
        }
        override fun onError(err: Int) {
            Log.e("Agora", "Erro no SDK: $err")
        }
    }


    private fun initializeAndJoinChannel() {
        try {
            val config = RtcEngineConfig().apply {
                mContext = applicationContext
                mAppId = myAppId
                mEventHandler = mRtcEventHandler
            }
            mRtcEngine = RtcEngine.create(config)
        } catch (e: Exception) {
            throw RuntimeException("Erro ao inicializar o RtcEngine.")
        }

        mRtcEngine?.enableVideo()
        mRtcEngine?.startPreview()

        val container = findViewById<FrameLayout>(R.id.local_video_view_container)
        val surfaceView = SurfaceView(applicationContext)
        container.addView(surfaceView)
        mRtcEngine?.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0))

        val options = ChannelMediaOptions().apply {
            clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
        }

        mRtcEngine?.joinChannel(null, channelName, 0, options)
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = findViewById<FrameLayout>(R.id.remote_video_view_container)
        val surfaceView = SurfaceView(applicationContext).apply {
            setZOrderMediaOverlay(true)
        }
        container.addView(surfaceView)
        mRtcEngine?.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid))
    }

    private val permissionReqId = 22

    private fun getRequiredPermissions(): Array<String> {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
        } else {
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
        }
    }

    private fun checkPermissions(): Boolean {
        for (permission in getRequiredPermissions()) {
            val permissionCheck = ContextCompat.checkSelfPermission(this, permission)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkPermissions()) {
            initializeAndJoinChannel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_chamada)

        channelName = intent.getStringExtra("channelName")
        Log.d("VideoChamadaActivity", "channelName: $channelName")

        if (channelName == null) {

            Toast.makeText(this, "Erro: Nenhum canal especificado", Toast.LENGTH_SHORT).show()
            Log.d("VideoChamadaActivity", "Erro: Nenhum canal especificado")
            return
        }
        if (checkPermissions()) {
            initializeAndJoinChannel()
        } else {
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), permissionReqId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine?.stopPreview()
        mRtcEngine?.leaveChannel()
    }
}
