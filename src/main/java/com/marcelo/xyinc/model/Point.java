package com.marcelo.xyinc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "point")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "sequence_all", allocationSize = 1)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "point_x", nullable = false)
    @JsonProperty("pointX")
    private Float point_x;

    @Column(name = "point_y", nullable = false)
    @JsonProperty("pointY")
    private Float point_y;

    public Point() {

    }

    public Point(String name, Float point_x, Float point_y) {
	this.name = name;
	this.point_x = point_x;
	this.point_y = point_y;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Float getPoint_x() {
	return point_x;
    }

    public void setPoint_x(Float point_x) {
	this.point_x = point_x;
    }

    public Float getPoint_y() {
	return point_y;
    }

    public void setPoint_y(Float point_y) {
	this.point_y = point_y;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null || getClass() != obj.getClass())
	    return false;
	Point other = (Point) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Point [id=" + id + ", name=" + name + "]";
    }

}
