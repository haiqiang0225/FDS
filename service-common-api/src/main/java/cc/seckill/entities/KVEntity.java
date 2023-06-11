package cc.seckill.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.entities
 * @Description : KVEntity
 * @Create on : 2023/6/11 09:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class KVEntity<K, V> {
    @JsonIgnore
    @JSONField(serialize = false)
    private Integer id;

    private K key;

    private V value;

}
