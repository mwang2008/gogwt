package com.gogwt.apps.tracking.data;

import java.util.HashSet;
import java.util.Set;

public class Stock implements java.io.Serializable {

	private Integer stockId;
	private String stockCode;
	private String stockName;
	private Set<StockDailyRecord> stockDailyRecords = new HashSet<StockDailyRecord>(
			0);

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Set<StockDailyRecord> getStockDailyRecords() {
		return stockDailyRecords;
	}

	public void setStockDailyRecords(Set<StockDailyRecord> stockDailyRecords) {
		this.stockDailyRecords = stockDailyRecords;
	}

	
	public String toString() {

		StringBuilder sbuf = new StringBuilder();
		sbuf.append(Stock.class.getSimpleName() + "=[");
		sbuf.append("stockId=" + stockId);
		sbuf.append(",stockCode=" + stockCode);
		sbuf.append(",stockName=" + stockName);
		if (stockDailyRecords != null && !stockDailyRecords.isEmpty()) {
			int i=0; 
			for (StockDailyRecord stockDailyRecord : stockDailyRecords) {
				sbuf.append("i=" + i++ + ","+ stockDailyRecord.toString());
			}
		}
		sbuf.append("]");

		return sbuf.toString();

	}
	
}
