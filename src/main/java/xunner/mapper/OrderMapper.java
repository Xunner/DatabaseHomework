package xunner.mapper;

import xunner.bean.Order;

/**
 * Order映射接口
 * <br>
 * created on 2018/10/25
 *
 * @author 巽
 **/
public interface OrderMapper {
	Order getById(int orderId);
}
