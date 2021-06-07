package org.typ.model;

import java.util.TimerTask;

public class ChronometerTask extends TimerTask {
    private ClassicStatistics stats;
    public ChronometerTask(ClassicStatistics stats){
        this.stats = stats;
    }

    @Override
    public void run() {
        //this.stats.incrementChronometer();
    }
}
