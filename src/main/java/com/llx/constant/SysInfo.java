package com.llx.constant;

import java.io.File;

/**
 * @author Lilx
 * @since 2016
 */
public enum SysInfo {
    CONFIG_FILE_PATH {
        @Override
        protected String getValue() {
            // TODO:llx 从System.property中找外部传入的"properties.file.path"，如果没有则返回当前jar包中的根路径
            /** @see com.llx.task.Test */
            String configFilePath = System.getProperty("config.file.path");
            if (null != configFilePath && !"".equals(configFilePath)) {
                if (new File(configFilePath).exists()) {
                    return configFilePath;
                }
            }
            return "/config.properties";
        }
    };

    private final String value;

    SysInfo() {
        this.value = getValue();
    }

    public String value() {
        return value;
    }

    protected abstract String getValue();
}
