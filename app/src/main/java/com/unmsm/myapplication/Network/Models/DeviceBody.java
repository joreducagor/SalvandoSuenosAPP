package com.unmsm.myapplication.Network.Models;

/**
 * Created by rubymobile on 31/05/17.
 */

public class DeviceBody {
    String username;
    DeviceParams device_params;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DeviceParams getDeviceparams() {
        return device_params;
    }

    public void setDeviceparams(DeviceParams device_params) {
        this.device_params = device_params;
    }
}
