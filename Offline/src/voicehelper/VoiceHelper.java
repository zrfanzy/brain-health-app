package voicehelper;

import com.baidu.voicerecognition.android.VoiceRecognitionClient;
import com.baidu.voicerecognition.android.VoiceRecognitionConfig;
import com.baidu.voicerecognition.android.VoiceRecognitionClient.VoiceClientStatusChangeListener;

import android.os.Handler;

/**
 * Created by root on 6/12/15.
 */
public class VoiceHelper {
    /**
     * aplication authorization *
     */
    private String API_KEY = "8MAxI5o7VjKSZOKeBzS4XtxO";
    private String SECRET_KEY = "Ge5GXVdGQpaxOmLzc8fOM8309ATCz9Ha";

    private VoiceRecognitionClient mClient;
    private VoiceRecognitionConfig config;

    private Handler mHandler;
    private boolean IsRecognition = false;

    private VoiceClientStatusChangeListener mListener = new VoiceClientStatusChangeListener() {
        public void onClientStatusChange(int status, Object obj) {
            switch (status) {
                case VoiceRecognitionClient.CLIENT_STATUS_START_RECORDING:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_START:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_AUDIO_DATA:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_SPEECH_END:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_FINISH:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_UPDATE_RESULTS:
                    break;
                case VoiceRecognitionClient.CLIENT_STATUS_USER_CANCELED:
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onNetworkStatusChange(int i, Object o) {

        }

        @Override
        public void onError(int i, int i1) {

        }

    };
}