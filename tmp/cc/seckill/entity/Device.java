package cc.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jhq
 * @since 2023-05-25
 */
@TableName("tb_device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备UUID
     */
    private Integer deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 通信协议
     */
    private String communicationProtocol;

    /**
     * ipv4通信地址
     */
    private String ipv4;

    /**
     * ipv6通信地址
     */
    private String ipv6;

    /**
     * 通信地址
     */
    private String communicationPath;

    /**
     * 设备状态
     */
    private Byte deviceStatus;

    /**
     * 生产厂商
     */
    private String manufacturer;

    /**
     * 设备位置
     */
    private String location;

    /**
     * 父设备UUID
     */
    private Integer parentDeviceId;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCommunicationProtocol() {
        return communicationProtocol;
    }

    public void setCommunicationProtocol(String communicationProtocol) {
        this.communicationProtocol = communicationProtocol;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getCommunicationPath() {
        return communicationPath;
    }

    public void setCommunicationPath(String communicationPath) {
        this.communicationPath = communicationPath;
    }

    public Byte getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Byte deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getParentDeviceId() {
        return parentDeviceId;
    }

    public void setParentDeviceId(Integer parentDeviceId) {
        this.parentDeviceId = parentDeviceId;
    }

    @Override
    public String toString() {
        return "Device{" +
            "deviceId = " + deviceId +
            ", deviceName = " + deviceName +
            ", deviceType = " + deviceType +
            ", communicationProtocol = " + communicationProtocol +
            ", ipv4 = " + ipv4 +
            ", ipv6 = " + ipv6 +
            ", communicationPath = " + communicationPath +
            ", deviceStatus = " + deviceStatus +
            ", manufacturer = " + manufacturer +
            ", location = " + location +
            ", parentDeviceId = " + parentDeviceId +
        "}";
    }
}
