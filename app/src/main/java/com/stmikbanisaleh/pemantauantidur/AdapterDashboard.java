package com.stmikbanisaleh.pemantauantidur;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;

public class AdapterDashboard {

    private Context context;
    private java.util.List<ModelDashboard> List = new ArrayList<>();
    private View.OnClickListener listener;

    public AdapterDashboard(List<ModelDashboard> list){
        this.List = list;
    }

    public class modelHolder{
        DonutProgress progress;

    }
}
