package app.gds.one.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by gerry on 2018/9/4.
 */

public class User  extends DataSupport implements Serializable {


    /**
     * uid : 5
     * phone : 15210104765
     * token : c8cbad8903854705d29931f7078d3ba9
     */

    private int uid;
    private String phone;
    private String token;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", token='" + token + '\'' +
                ", uid=" + uid +
                '}';
    }
}
