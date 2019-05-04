package com.marcelo.xyinc.webservice.server;

public interface GenericControllerRest {

    public final static String REST_VERSION = "/api/v1";

    public static final String BAD_REQUEST = "{error:['code': 400, description: 'Bad request']}";

    public final static String INFO = "/info";

}
