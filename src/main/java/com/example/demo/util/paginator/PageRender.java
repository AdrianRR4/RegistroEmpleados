package com.example.demo.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	
	private int totalPages;

	private int numElementByPage;
	
	private int actualPage;
	
	private List<PageItem>pages;
	
	public PageRender(String url, Page<T> page) {
		
		this.url = url;
		this.page = page;
		this.pages=new ArrayList<PageItem>();
		
		numElementByPage= page.getSize();
		totalPages=page.getTotalPages();
		actualPage=page.getNumber()+1;
		
		int start;
		int end;
		
		if(totalPages<=numElementByPage) {
			
			start=1;
			end=totalPages;
		}else {
			
			if(actualPage<=numElementByPage/2) {
				
				start=1;
				end= numElementByPage;
			}else if( actualPage>=totalPages-numElementByPage/2){
				start= totalPages- numElementByPage+1;
				end= numElementByPage;
				
			}else {
				start=actualPage - numElementByPage/2;
				end=numElementByPage;
			}
			
		}
		for(int i=0; i<end; i++) {
			
			pages.add( new PageItem(start +i, actualPage==start+i));
		}
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getActualPage() {
		return actualPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	public boolean isFirst() {
		return page.isFirst();
	}
	 
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
}
