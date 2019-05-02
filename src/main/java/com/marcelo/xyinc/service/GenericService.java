package com.marcelo.xyinc.service;

import java.io.Serializable;

import com.marcelo.xyinc.repository.GenericRepository;

public interface GenericService<E extends Serializable, K extends Serializable> extends GenericRepository<E, K> {

    public void setDAO(final GenericRepository<E, K> genericRepository);

    public GenericRepository<E, K> getDAO();
    
    public boolean validPreSave(E object);
}
