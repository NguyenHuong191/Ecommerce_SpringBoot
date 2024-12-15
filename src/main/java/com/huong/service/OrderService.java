package com.huong.service;

import java.util.List;

import com.huong.model.OrderRequest;
import com.huong.model.ProductOrder;

public interface OrderService {
	
	public void saveOrder(Integer userId, OrderRequest orderRequest);

	public List<ProductOrder> getOrderByuser(Integer userId);

    public Boolean UpdateOrderStatus(Integer id, String status);
    
	public List<ProductOrder> getAllOrder();
	
	public List<ProductOrder> getOrdersFromSearch(String searchText);

}
