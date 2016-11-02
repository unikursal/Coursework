package Util;

import javax.xml.bind.annotation.*;

/**
 * Created by User on 11.10.2016.
 */
@XmlRootElement
public class MySettings {
    private int defaultKodCourt;

    public MySettings(int kod){
        defaultKodCourt = kod;
    }

    public MySettings(){}

    public int getDefaultKodCourt() {
        return defaultKodCourt;
    }

    @XmlElement
    public void setDefaultKodCourt(int defaultKodCourt) {
        this.defaultKodCourt = defaultKodCourt;
    }
}
