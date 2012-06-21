package jim.h.common.android.zxingjar.demo;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ZXingJarDemoActivity extends Activity {
    private Handler  handler = new Handler();
    private TextView txtScanResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txtScanResult = (TextView) findViewById(R.id.scan_result);
        
        View btnScan = findViewById(R.id.scan_button);

        // Scan button
        btnScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the last parameter to true to open front light if available
                IntentIntegrator.initiateScan(ZXingJarDemoActivity.this, R.layout.capture,
                        R.id.viewfinder_view, R.id.preview_view, true);
                
                
                // deve precisar desse outro aqui, pra realizar uma segunda vez
                // o qr code
                IntentIntegrator.initiateScan(ZXingJarDemoActivity.this, R.layout.capture2,
                        R.id.viewfinder_view2, R.id.preview_view2, true);
            }
        });
    }

    
    // pega o resultado da leitura (retorno do intent acima)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                
                final String result = scanResult.getContents();
                Log.i("resultado: ", result);
                
                if (result != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtScanResult.setText(result);
                        }
                    });
                }
                break;
            default:
        }
    }
}
