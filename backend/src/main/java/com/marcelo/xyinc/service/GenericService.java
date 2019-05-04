package com.marcelo.xyinc.service;

import java.io.Serializable;

import com.marcelo.xyinc.repository.GenericRepository;

public interface GenericService<K extends Serializable, E extends Serializable> extends GenericRepository<K, E> {

    public void setDAO(final GenericRepository<K, E> genericRepository);

    public GenericRepository<K, E> getDAO();
    
    public boolean validPreSave(E object);
}
