package com.marcelo.xyinc.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = { "size", "points" })
public class Points implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Point> points;

    public Points() {

    }

    public Points(List<Point> points) {
	this.points = points;
    }

    @JsonProperty("size")
    public Integer getSize() {
	final Integer result;
	if (CollectionUtils.isNotEmpty(points)) {
	    result = points.size();
	} else {
	    result = 0;
	}
	return result;
    }

    @JsonProperty("points")
    public List<Point> getPoints() {
	return points;
    }

}
