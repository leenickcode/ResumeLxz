package com.demo.nick.resumelxz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PDFActivity extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener{
 @BindView(R.id.pdfview)
    PDFView pdfView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String pdfName = "resume.pdf";
    private static final String TAG = "PDFActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        pdfView.fromAsset(pdfName)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .onLoad(this)
                .onPageChange(this)
                .swipeVertical( true )
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        Log.e(TAG, "loadComplete: "+nbPages );
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        Log.e(TAG, "onPageChanged: "+page +"-----"+pageCount);
    }
}
