package com.Quan.TryJWT.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Quan.TryJWT.dto.MyItem;
import com.Quan.TryJWT.model.OrderStatus;
import com.Quan.TryJWT.repository.OrderRepository;
import com.Quan.TryJWT.repository.OrderStatusRepository;
import com.Quan.TryJWT.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository statusRepository;

	@Override
	public List<MyItem> reportReceipt(Date date, int limit) {
		List<MyItem> list = new ArrayList<>();
        for (OrderStatus s: statusRepository.findAll()) {
            MyItem myItem = new MyItem();
            myItem.setLabel(s.getDescription());
            myItem.setValue(orderRepository.countByStatusId(s));
            list.add(myItem);
        }
        return list;
	}

	@Override
	public Date addDays(Date date, int days) {
		GregorianCalendar cal =  new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
	}

	@Override
	public Date subDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
	}

	@Override
	public String covertD2S(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyy");
        return df.format(date);
	}

}
