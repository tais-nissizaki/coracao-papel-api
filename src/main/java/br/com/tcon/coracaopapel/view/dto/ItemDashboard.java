package br.com.tcon.coracaopapel.view.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemDashboard {
	private String item;
	private List<ItemPorData> itensPorData;
	
	public ItemDashboard(String item) {
		super();
		this.item = item;
		this.itensPorData = new ArrayList<>();
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<ItemPorData> getItensPorData() {
		return itensPorData;
	}

	public void setItensPorData(List<ItemPorData> itensPorData) {
		this.itensPorData = itensPorData;
	}

}
