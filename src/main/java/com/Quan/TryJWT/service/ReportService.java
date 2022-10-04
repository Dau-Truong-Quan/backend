package com.Quan.TryJWT.service;

import java.util.Date;
import java.util.List;

import com.Quan.TryJWT.dto.MyItem;

public interface ReportService {
	public List<MyItem> reportReceipt(Date date, int limit);
	public Date addDays(Date date, int days);
    public Date subDays(Date date, int days);
    public String covertD2S(Date date);
}
