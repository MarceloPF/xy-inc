package com.marcelo.xyinc.webservice.server;

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
import com.marcelo.xyinc.service.PointService;

@RestController
@RequestMapping(value = GenericControllerRest.ROOT_POIT)
public class PointControllerRest {

    private static final Log LOG = LogFactory.getLog(PointControllerRest.class);

    private static final String BAD_REQUEST = "{error:['code': 400, description: 'Bad request']}";

    @Autowired
    @Qualifier("pointServiceImpl")
    private PointService poitService;

    @RequestMapping(value = GenericControllerRest.INFO, method = RequestMethod.GET, produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public String info() {
	LOG.debug("REST: " + GenericControllerRest.ROOT_POIT + GenericControllerRest.INFO);
	final StringBuffer sb = new StringBuffer("<info> ");
	sb.append("<name-call>info</name-call>");
	sb.append(" </info>");
	return sb.toString();
    }

    @RequestMapping(value = GenericControllerRest.ADD_POINT, method = RequestMethod.POST, produces = {
	    MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<?> addPoint(@RequestParam(value = "name", required = false) final String name,
	    @RequestParam(value = "pointX", required = false) final Float poitX,
	    @RequestParam(value = "pointY", required = false) final Float poitY) {
	final Point point = new Point(name, poitX, poitY);
	final Point result = poitService.save(point);
	final ResponseEntity<?> response;
	if (result == null) {
	    response = new ResponseEntity<String>(BAD_REQUEST, HttpStatus.BAD_REQUEST);
	} else {
	    response = new ResponseEntity<Point>(point, HttpStatus.OK);
	}
	LOG.info(result);
	return response;
    }

}
