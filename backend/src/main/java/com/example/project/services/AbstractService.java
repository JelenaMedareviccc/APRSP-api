package com.example.project.services;

import java.util.List;

public interface AbstractService <T, P, K> {
	
	List<T> findAll();
	T create(P p);
	void delete (K k);
	T findById (K k);
	T update(P p);
}
