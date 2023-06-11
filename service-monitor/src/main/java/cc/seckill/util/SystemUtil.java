package cc.seckill.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Properties;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : SystemUntil
 * @Create on : 2023/6/7 15:55
 */
@Component
public class SystemUtil {
    /*
   CPU: 使用率
   内存：使用率
   磁盘：读写速度，读取速度，读写速度
   网络带宽：接收宽带，发送宽带，网络宽带

    */

    private static final int OSHI_WAIT_SECOND = 1000;
    private static final SystemInfo SYSTEM_INFO = new SystemInfo();
    //获取硬件信息 【硬件抽象层。提供对处理器、内存、电池和磁盘等硬件项目的访问。】
    private static final HardwareAbstractionLayer HARDWARE = SYSTEM_INFO.getHardware();
    //获取操作系统信息
    private static final OperatingSystem OPERATING_SYSTEM = SYSTEM_INFO.getOperatingSystem();


    /**
     * 所有系统信息
     */
    public JSONObject getInfo() throws UnknownHostException {
        JSONObject info = new JSONObject();
        info.put("cpuInfo", getCpuInfo());
        info.put("jvmInfo", getJvmInfo());
        info.put("memInfo", getMemInfo());
        info.put("sysInfo", getSysInfo());
        info.put("sysFileInfo", getSysFileInfo());
        return info;
    }

    /**
     * 获取CPU相关信息
     *
     * @return cpuInfo
     */
    public JSONObject getCpuInfo() {
        // CPU信息
        JSONObject cpuInfo = new JSONObject();
        CentralProcessor processor = HARDWARE.getProcessor();
        // 第一次获取：获取系统范围的 CPU 负载滴答计数器：
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        //等待一段时间
        Util.sleep(OSHI_WAIT_SECOND);
        //第二次获取：获取系统范围的 CPU 负载滴答计数器：
        long[] ticks = processor.getSystemCpuLoadTicks();


        // ============================== 计算相关性能指标: ==========================================.
        //  计算出该时间间隔内的 CPU 负载
        //  Nice 和 IOWait 信息在 Windows 上不可用，
        // 而 IOwait 和 IRQ 信息在 macOS 上不可用，
        // 因此这些滴答始终为零

        //时间段内：用户态的CPU时间
        long user =
                ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        //时间段内：nice值为负的进程所占用的CPU时间
        long nice =
                ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        //时间段内：内核态CPU时间
        long cSys =
                ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        //时间段内：空闲时间，除硬盘IO等待时间以外其它等待时间
        long idle =
                ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        //时间段内：等待I/O的CPU时间
        long iowait =
                ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        //时间段内：硬中断的CPU时间
        long irq =
                ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        //时间段内：软中断的CPU时间
        long softirq =
                ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        //时间段内：当系统运行在虚拟机中的时候，被其他虚拟机占用的CPU时间
        long steal =
                ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];

        //时间段内：CPU时间
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        //cpu供应商
        cpuInfo.put("cpuVendor", processor.getProcessorIdentifier().getVendor());
        //cpu名称
        cpuInfo.put("cpuName", processor.getProcessorIdentifier().getName());
        //cpu核数
        cpuInfo.put("cpuNum", processor.getLogicalProcessorCount());

        cpuInfo.put("user", new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        cpuInfo.put("cSys", new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        cpuInfo.put("idle", new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu));
        cpuInfo.put("iowait", new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));

        cpuInfo.put("nice", new DecimalFormat("#.##%").format(nice * 1.0 / totalCpu));
        cpuInfo.put("irq", new DecimalFormat("#.##%").format(irq * 1.0 / totalCpu));
        cpuInfo.put("softirq", new DecimalFormat("#.##%").format(softirq * 1.0 / totalCpu));
        cpuInfo.put("steal", new DecimalFormat("#.##%").format(steal * 1.0 / totalCpu));
        cpuInfo.put("sysLoad", new DecimalFormat("#.##%").format((user + cSys) * 1.0 / totalCpu));
        return cpuInfo;
    }

    /**
     * 系统jvm信息
     */
    public JSONObject getJvmInfo() {
        JSONObject jvmInfo = new JSONObject();
        Properties props = System.getProperties();

        // ============================== 获取相关性能指标: ==========================================.
        Runtime runtime = Runtime.getRuntime();
        long jvmTotalMemoryByte = runtime.totalMemory();
        long jvmFreeMemoryByte = runtime.freeMemory();
        long jvmUseMemoryByte = jvmTotalMemoryByte - jvmFreeMemoryByte;
        long jvmMaxMemory = runtime.maxMemory();
        //jvm总内存
        jvmInfo.put("total", formatByte(jvmTotalMemoryByte));
        //空闲空间
        jvmInfo.put("free", formatByte(jvmFreeMemoryByte));
        //jvm已使用内存
        jvmInfo.put("user", formatByte(jvmUseMemoryByte));
        //jvm最大可申请
        jvmInfo.put("max", formatByte(jvmMaxMemory));

        //jvm内存使用率
        jvmInfo.put("usageRate", new DecimalFormat("#.##%").format((jvmUseMemoryByte) * 1.0 / jvmTotalMemoryByte));
        //jvm空闲
        jvmInfo.put("freeRate", new DecimalFormat("#.##%").format((jvmFreeMemoryByte) * 1.0 / jvmTotalMemoryByte));

        //jdk版本
        jvmInfo.put("jdkVersion", props.getProperty("java.version"));
        //jdk路径
        jvmInfo.put("jdkHome", props.getProperty("java.home"));
        return jvmInfo;
    }


    /**
     * 系统内存信息
     */
    public JSONObject getMemInfo() {
        JSONObject memInfo = new JSONObject();

        // ============================== 获取相关性能指标: ==========================================.
        GlobalMemory memory = HARDWARE.getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余【当前可用的物理内存量】
        long acaliableByte = memory.getAvailable();
        //已使用
        long useByte = totalByte - acaliableByte;

        //总内存
        memInfo.put("total", formatByte(totalByte));
        //使用
        memInfo.put("used", formatByte(useByte));
        //剩余内存
        memInfo.put("free", formatByte(acaliableByte));

        //空闲率
        memInfo.put("freeRate", new DecimalFormat("#.##%").format((acaliableByte) * 1.0 / totalByte));
        //使用率
        memInfo.put("usageRate", new DecimalFormat("#.##%").format((useByte) * 1.0 / totalByte));


        return memInfo;
    }


    /**
     * 系统盘符信息
     */
    public JSONArray getSysFileInfo() {
        JSONObject diskInfo;
        JSONArray diskInfoArr = new JSONArray();

        // ============================== 获取相关性能指标: ==========================================.
        FileSystem fileSystem = OPERATING_SYSTEM.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores().toArray(new OSFileStore[0]);
        for (OSFileStore fs : fsArray) {
            diskInfo = new JSONObject();
            //盘符路径
            diskInfo.put("dirName", fs.getMount());
            //盘符类型
            diskInfo.put("sysTypeName", fs.getType());
            //文件类型
            diskInfo.put("typeName", fs.getName());
            //总大小
            diskInfo.put("total", formatByte(fs.getTotalSpace()));
            //剩余大小
            diskInfo.put("free", formatByte(fs.getUsableSpace()));
            //已经使用量
            diskInfo.put("used", formatByte(fs.getTotalSpace() - fs.getUsableSpace()));
            if (fs.getTotalSpace() == 0) {
                //资源的使用率
                diskInfo.put("usage", 0);
            } else {
                diskInfo.put("usage",
                        new DecimalFormat("#.##%").format((fs.getTotalSpace() - fs.getUsableSpace()) * 1.0 / fs.getTotalSpace()));
            }
            diskInfoArr.add(diskInfo);
        }
        //排序
        diskInfoArr.sort(Comparator.comparing(stage -> {
            JSONObject stageJson = (JSONObject) stage;
            return stageJson.getString("dirName");
        }));
        return diskInfoArr;
    }


    /**
     * 系统信息
     */
    public JSONObject getSysInfo() throws UnknownHostException {
        JSONObject sysInfo = new JSONObject();

        // ============================== 获取相关性能指标: ==========================================.
        Properties props = System.getProperties();
        //操作系统名
        sysInfo.put("osName", props.getProperty("os.name"));
        //系统架构
        sysInfo.put("osArch", props.getProperty("os.arch"));
        //服务器名称
        sysInfo.put("computerName", InetAddress.getLocalHost().getHostName());
        //服务器Ip
        sysInfo.put("computerIp", InetAddress.getLocalHost().getHostAddress());
        //项目路径
        sysInfo.put("userDir", props.getProperty("user.dir"));
        return sysInfo;
    }


    /**
     * 单位转换
     */
    private String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }
}
