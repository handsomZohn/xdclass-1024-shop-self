package net.xdclass.mapper;

import net.xdclass.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author viy7664
 * @since 2021-12-06
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    /**
     * 扣减存储
     * @param couponId
     * @return
     */
    int reduceStock(@Param("couponId") long couponId);
}
