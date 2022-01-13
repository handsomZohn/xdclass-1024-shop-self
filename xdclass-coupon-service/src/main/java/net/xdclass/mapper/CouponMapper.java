package net.xdclass.mapper;

import net.xdclass.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * 优惠券超发
 * 单人优惠券超领的问题
 *
 * 分布式锁设计要点：
 *  第一点：排他性 在分布式应用集群中，同一个方法在同一时间只能被一台机器上的一个线程执行
 *  第二点：容错性 分布式锁一定能得到释放，比如客户端崩溃或者网络中断
 *  第三点：满足可重入、高性能、高可用
 *  第四点：注意分布式锁的开销、锁力度。
 *
 *  原子加锁：设置过期时间 防止宕机死锁
 *  原子解锁：需要判断是不是自己加的锁
 *
 * @author viy7664
 * @since 2021-12-06
 */
public interface CouponMapper extends BaseMapper<CouponDO> {


    /**
     * 第一种方案
     */
    // update product set stock=stock-1 where id = 1 and stock > 0
    // id是主键索引的前提下，如果每次只是减少1个库存，则可以采取此种方式，只做数据安全校验，可以有效减库存
    // 性能更高，避免大量无用sql，只要有库存就可以操作成功
    // 适用场景：高并发场景下的取号器，优惠券发放扣减库存等等场景

    /**
     * 第二种方案
     */
    // update product set stock=stock-1 where stock=#{原库存} and id = 1 and stock > 0
    // 使用业务自身的条件作为乐观锁，但是存在ABA问题，对比方案三的好处是不用增加version字段。如果只是扣减库存且不在意
    // ABA问题，则可以采用上面的方式，但是业务性能相对方案一就差了一点，因为库存变动后，sql就会失效

    /**
     * 第三种方案
     */
    // update product set stock=stock-1, version=version+1  where id = 1 and stock > 0
    // and version =#{原先查询的版本号}
    // 增加版本号主要是为了解决ABA问题，数据读取后，更新前数据被人篡改过，version只能递增


    /**
     * 扣减库存
     * @param couponId
     * @return
     */
    int reduceStock(@Param("couponId") long couponId);
}
