package com.Quan.TryJWT.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Quan.TryJWT.model.Order;
import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.model.User;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	
	@Query("SELECT SUM(totalPrice)\n"
			+ "FROM Order\r\n"
			+ "WHERE year(date) = ?1\r\n"
			+ "AND statusId = 4\r\n"
			+ "group by month(date)"
			+ "ORDER BY month(date)")
	public List<Float> getPrice(int year);
	
	@Query(value ="select month(date)\r\n"
			+ "from orders o\r\n"
			+ "where year(date) = ?1\r\n"
			+ "and status_id = 4\r\n"
			+ "group by month(date)\r\n"
			+ "ORDER BY month(date)", nativeQuery = true)
	public List<Integer> getMonthByYearOrderDate(int year);
	
	@Query(value ="select year(date)\r\n"
			+ "from orders o\r\n"
			+ "where status_id = 4\r\n"
			+ "group by year(date)\r\n"
			+ "ORDER BY year(date)", nativeQuery = true)
	public List<Integer> getYear();
	
	public List<Order> findAllByStatusId(OrderStatus status);

	public List<Order> findByUser(User user);
	
	public long countByDate(Date date);
	public long countByStatusId(OrderStatus statusId);
	
	public List<Order> findByUserAndStatusIdOrderByDateDesc(User user, OrderStatus orderStatus);
	public List<Order> findByUserOrderByDateDesc(User user);
	public List<Order> findByStatusIdOrderByDateDesc(OrderStatus orderStatus);
	
	@Query(value = "Select * from orders U where convert(varchar(10),U.date, 120) = convert(varchar(10),GETDATE(), 120) ", nativeQuery = true)
	public List<Order> getAllOrdeToday();
}
