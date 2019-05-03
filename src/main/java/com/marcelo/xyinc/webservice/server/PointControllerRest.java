package com.marcelo.xyinc.webservice.server;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
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

    @Autowired
    @Qualifier("pointServiceImpl")
    private PointService poitService;

    @PostConstruct
    public void init() {
	LOG.debug("REST: " + GenericControllerRest.ROOT_POIT + GenericControllerRest.INFO);
    }

    @RequestMapping(value = GenericControllerRest.INFO, method = RequestMethod.GET, produces= MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public String info() {
	final StringBuffer sb = new StringBuffer("<info> ");
	sb.append("<name-call>info</name-call>");
	sb.append(" </info>");
	return sb.toString();
    }

    @RequestMapping(value = GenericControllerRest.ADD_POINT, method = RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Point addPoint(@RequestParam(value = "name", required = false) final String name,
	    @RequestParam(value = "pointX", required = false) final Float poitX,
	    @RequestParam(value = "pointY", required = false) final Float poitY) throws Exception {
	final Point point = new Point(name, poitX, poitY);
	final Point result = poitService.save(point);
	if (result == null) {
	    throw new Exception("error=400," + "text=Bad Request}");
	}
	LOG.info(result);
	return result;
    }

}
