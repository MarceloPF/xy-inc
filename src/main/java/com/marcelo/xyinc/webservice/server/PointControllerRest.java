package com.marcelo.xyinc.webservice.server;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.xyinc.model.Point;
import com.marcelo.xyinc.model.Points;
import com.marcelo.xyinc.service.PointService;

/**
 * 
 * webservice rest call /rest/point
 */

@RestController
@RequestMapping(value = GenericControllerRest.ROOT_POIT)
public class PointControllerRest {

    private static final Log LOG = LogFactory.getLog(PointControllerRest.class);

    private static final String BAD_REQUEST = "{error:['code': 400, description: 'Bad request']}";

    @Autowired
    @Qualifier("pointServiceImpl")
    private PointService poitService;

    /**
     * Info about webservice url: /rest/point/info
     * 
     * Request type: GET
     * 
     * @return: xml with functins for webservice /rest/point
     * 
     */
    @RequestMapping(value = GenericControllerRest.INFO, method = RequestMethod.GET, produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public String info() {
	LOG.debug("REST: " + GenericControllerRest.ROOT_POIT + GenericControllerRest.INFO);
	final StringBuffer sb = new StringBuffer("<info> ");
	sb.append("<name-call>info</name-call>");
	sb.append("<functions>");
	functionsRest(sb);
	sb.append("</functions>");
	sb.append(" </info>");
	return sb.toString();
    }

    private void functionsRest(final StringBuffer sb) {
	String functions[] = { "addPoint", "findAllPoints", "searchForNearbyPoints", "changePoint" };
	for (final String key : functions) {
	    sb.append("<function><name>");
	    sb.append(key);
	    sb.append("</name></function>");
	}
    }

    /**
     * Registray one point url for request /rest/point/addPoint
     * 
     * Request type: POST
     * 
     * @param: String name of the point
     * @param: Float point x
     * @param: Float point y
     * @return: JSON Point
     */
    @RequestMapping(value = GenericControllerRest.ADD_POINT, method = RequestMethod.POST, produces = {
	    MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> addPoint(@RequestParam(value = "name", required = false) final String name,
	    @RequestParam(value = "pointX", required = false) final Float poitX,
	    @RequestParam(value = "pointY", required = false) final Float poitY) {
	final Point point = new Point(name, poitX, poitY);
	final Point result = poitService.save(point);
	final ResponseEntity<?> response = answerRequestOne(result);
	return response;
    }

    /**
     * Update one point url for request /rest/point/changePoint
     * 
     * Request type: POST
     * 
     * @param: String name of the point
     * @param: Float point x
     * @param: Float point y
     * @return: JSON Point
     */
    @RequestMapping(value = GenericControllerRest.UPDATE_POINT, method = RequestMethod.POST, produces = {
	    MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> changePoint(@RequestParam(value = "id", required = true) final Integer id,
	    @RequestParam(value = "name", required = false) final String name,
	    @RequestParam(value = "pointX", required = false) final Float poitX,
	    @RequestParam(value = "pointY", required = false) final Float poitY) {
	final Point point = new Point(id, name, poitX, poitY);
	final Point result = poitService.update(point);
	final ResponseEntity<?> response = answerRequestOne(result);
	return response;
    }

    private ResponseEntity<?> answerRequestOne(final Point result) {
	final ResponseEntity<?> response;
	if (result == null) {
	    response = new ResponseEntity<String>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
	} else {
	    response = new ResponseEntity<Point>(result, HttpStatus.OK);
	}
	LOG.info(result);
	return response;
    }

    /**
     * Find all points in database, url for request /rest/point/findAllPoints
     * 
     * Request type: GET
     * 
     * @return: JSON List<Point>
     */
    @RequestMapping(value = GenericControllerRest.FIND_ALL, method = RequestMethod.GET, produces = {
	    MediaType.APPLICATION_JSON_VALUE })
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
	    response = new ResponseEntity<String>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
	} else {
	    response = new ResponseEntity<Points>(new Points(result), HttpStatus.OK);
	}
	LOG.info(result);
	return response;
    }

    /**
     * Search all points near a point configuring your search radius, url for
     * request /rest/point/searchForNearbyPoints
     * 
     * Request type: GET
     * 
     * @return: JSON List<Points>
     */
    @RequestMapping(value = GenericControllerRest.SEARCH_ALL, method = RequestMethod.GET, produces = {
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
