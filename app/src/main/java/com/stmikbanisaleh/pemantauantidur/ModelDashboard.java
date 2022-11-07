package com.stmikbanisaleh.pemantauantidur;

public class ModelDashboard {

   public String data_chart;
    public String total_sleep;
    public String bpm;

    public ModelDashboard() {
    }

    public ModelDashboard(String data_chart, String total_sleep, String bpm) {
        this.data_chart = data_chart;
        this.total_sleep = total_sleep;
        this.bpm = bpm;
    }

    public String getData_chart() {
        return data_chart;
    }

    public void setData_chart(String data_chart) {
        this.data_chart = data_chart;
    }

    public String getTotal_sleep() {
        return total_sleep;
    }

    public void setTotal_sleep(String total_sleep) {
        this.total_sleep = total_sleep;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }
}
