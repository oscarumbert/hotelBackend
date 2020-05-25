package com.online.hotel.arlear.service;

import java.util.List;


public interface ServiceGeneric<T> {
	public boolean create(T entity);
	public boolean delete(Long id);
	public List<T> find();
	public boolean update(T entity);
	public T find(Long id);
	
}
