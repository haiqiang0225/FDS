package cc.seckill.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author jhq
 * @since 2023-05-25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@TableName("tb_device")
public class Device implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备UUID
     */
    @TableId(value = "device_id", type = IdType.AUTO)
    private String deviceId;

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
    private String parentDeviceId;

    @TableField(exist = false)
    private List<Device> childrenList;
}
