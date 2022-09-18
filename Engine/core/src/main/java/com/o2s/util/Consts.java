package com.o2s.util;

import com.o2s.data.enm.DeviceType;

public class Consts {

    public static String AGENT_FILE_SOURCE_PATH = "D:/DND/ES+/Telegraf";
    public static String AGENT_FILE_NAME_WIN = "telegraf-1.24.0_windows_amd64.zip";
    public static String AGENT_FILE_NAME_LINUX = "telegraf-1.24.0_linux_amd64.tar.gz";
    public static String AGENT_TARGET_FOLDER = "telegraf";

    public static String getAgentFileName(DeviceType type){
        return type == DeviceType.WINDOWS ? AGENT_FILE_NAME_WIN : AGENT_FILE_NAME_LINUX;
    }
}
