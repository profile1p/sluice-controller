package bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Lilx
 * @since 2016
 */
public class RadioMsgBean implements Sendable {

    /**
     * 测站地址
     */
    @SerializedName("c")
    private Integer monitorStationAddress = null;

    /**
     * 中心站地址
     */
    @SerializedName("zx")
    private Integer centerStationAddress = null;

    /**
     * 密码
     */
    @SerializedName("m")
    private String password = null;

    /**
     * 电压
     */
    @SerializedName("d")
    private Double voltage = null;

    /**
     * 水位
     */
    @SerializedName("s")
    private Integer waterLevel = null;

    /**
     * 传感器地址
     */
    @SerializedName("cd")
    private Integer gaugeAddress = null;

    /**
     * 配置信息名称
     */
    @SerializedName("pm")
    private String configName = null;

    /**
     * 值
     */
    @SerializedName("z")
    private Integer value = null;

    /**
     * 错误信息
     */
    @SerializedName("cx")
    private String errorMsg = null;

    /**
     * 功能码
     */
    @SerializedName("g")
    private Integer functionCode = null;

    public Integer getMonitorStationAddress() {
        return monitorStationAddress;
    }

    public void setMonitorStationAddress(Integer monitorStationAddress) {
        this.monitorStationAddress = monitorStationAddress;
    }

    public Integer getCenterStationAddress() {
        return centerStationAddress;
    }

    public void setCenterStationAddress(Integer centerStationAddress) {
        this.centerStationAddress = centerStationAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public Integer getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Integer waterLevel) {
        this.waterLevel = waterLevel;
    }

    public Integer getGaugeAddress() {
        return gaugeAddress;
    }

    public void setGaugeAddress(Integer gaugeAddress) {
        this.gaugeAddress = gaugeAddress;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(Integer functionCode) {
        this.functionCode = functionCode;
    }
}
