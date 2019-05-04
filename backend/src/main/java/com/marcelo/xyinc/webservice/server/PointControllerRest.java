package com.marcelo.xyinc.webservice.server;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.model.Points;
import com.marcelo.xyinc.service.PointService;

/**
 * 
 * webservice rest call /rest/point
 */

@RestController
@RequestMapping(GenericControllerRest.REST_VERSION)
public class PointControllerRest {

    private static final Log LOG = LogFactory.getLog(PointControllerRest.class);

    @Autowired
    @Qualifier("pointServiceImpl")
    private PointService poitService;

    /**
     * Info about webservice url: /rest/api/v1/point/info
     * 
     * Request type: GET
     * 
     * @return: xml with functins for webservice /rest/api/v1/point/info
     * 
     */
    @RequestMapping(value = "/point/info", produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public String info() {
	LOG.debug("REST: /rest/api/v1/point/info");
	final StringBuffer sb = new StringBuffer("<info> ");
	sb.append("<name-call>info, more details see documentation </name-call>");
	sb.append("<functions>");
	functionsRest(sb);
	sb.append("</functions>");
	sb.append(" </info>");
	return sb.toString();
    }

    private void functionsRest(final StringBuffer sb) {
	String functions[] = { "createPoint", "updatePoint", "deletePoint", "findAllPoints", "searchForNearbyPoints" };
	for (final String key : functions) {
	    sb.append("<function><name>");
	    sb.append(key);
	    sb.append("</name></function>");
	}
    }

    /**
     * Registray one point url for request /rest/api/v1/point
     * 
     * Request type: POST
     * 
     * @param: Point one point
     * 
     * @return: JSON Point
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/point", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPoint(@RequestBody Point point) {
	final Point result = poitService.save(point);
	final ResponseEntity<?> response = answerRequestOne(result);
	return response;
    }

    /**
     * Update one point url for request /rest/api/v1/point/{id}
     * 
     * Request type: PUT
     * 
     * @param: Point one point
     * 
     * @return: JSON Point
     */
    @PutMapping(value = "/point/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updatePoint(@PathVariable(value = "id") Long id, @Valid @RequestBody Point point) {
	final Point result = poitService.update(point);
	final ResponseEntity<?> response = answerRequestOne(result);
	return response;
    }

    private ResponseEntity<?> answerRequestOne(final Point result) {
	final ResponseEntity<?> response;
	if (result == null) {
	    response = new ResponseEntity<String>(GenericControllerRest.BAD_REQUEST, HttpStatus.BAD_REQUEST);
	} else {
	    response = new ResponseEntity<Point>(result, HttpStatus.OK);
	}
	LOG.info(result);
	return response;
    }

    /**
     * Delete one point url for request /rest/api/v1/point/{id}
     * 
     * Request type: DELETE
     * 
     * @param: Integer id of the point
     * 
     * @return: JSON Point
     */
    @DeleteMapping(value = "/point/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deletePoint(@PathVariable(value = "id") final Integer id) {
	poitService.delete(new Point(id));
	return new ResponseEntity<String>("[id:" + id + ", result: 'Ok']", HttpStatus.OK);
    }

    /**
     * Find all points in database, url for request /rest/api/v1/point
     * 
     * Request type: GET
     * 
     * @return: JSON List<Point>
     */
    @GetMapping(value = "/point", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findAllPoints() {
	final List<Point> result = poitService.findAll(Point.class);
	final ResponseEntity<?> response;
	response = sendListRequest(result);
	return response;
    }

    private ResponseEntity<?> sendListRequest(final List<Point> result) {
	final ResponseEntity<?> response;
	if (result == null) {
	    response = new ResponseEntity<String>(GenericControllerRest.BAD_REQUEST, HttpStatus.BAD_REQUEST);
	} else {
	    response = new ResponseEntity<Points>(new Points(result), HttpStatus.OK);
	}
	LOG.info(result);
	return response;
    }

    /**
     * Search all points near a point configuring your search radius,
     * 
     * url:
     * /rest/api/v1/point/searchForNearbyPoints?coordinationX=1&coordinationY=1&maximumDistanceD=1
     * 
     * Request type: GET
     * 
     * @return: JSON List<Points>
     */
    @RequestMapping(value = "/point/searchForNearbyPoints", method = RequestMethod.GET, produces = {
	    MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> searchForNearbyPoints(
	    @RequestParam(value = "coordinationX", required = true) final Float coordination_x,
	    @RequestParam(value = "coordinationY", required = true) final Float coordination_y,
	    @RequestParam(value = "maximumDistanceD", required = true) final Float maximumDistance_D) {
	final List<Point> result = poitService.searchForNearbyPoints(coordination_x, coordination_y, maximumDistance_D);
	final ResponseEntity<?> response;
	response = sendListRequest(result);
	return response;
    }

}
