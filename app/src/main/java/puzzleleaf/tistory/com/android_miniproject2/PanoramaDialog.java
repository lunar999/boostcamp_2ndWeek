package puzzleleaf.tistory.com.android_miniproject2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cmtyx on 2017-07-13.
 */

public class PanoramaDialog extends AppCompatActivity {

    @BindView(R.id.panoramaImage) PanoramaImageView panoramaImageView;
    GyroscopeObserver gyroscopeObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item);
        ButterKnife.bind(this);
        panoramaInit(getIntent().getIntExtra("res",0));
    }

    private void panoramaInit(int res){
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);
        if(res==0){
            Toast.makeText(getApplicationContext(),"이미지 로딩 오류",Toast.LENGTH_SHORT).show();
            return;
        }
        panoramaImageView.setImageResource(res);
        panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gyroscopeObserver.unregister();
    }

}
