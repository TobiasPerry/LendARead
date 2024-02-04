package ar.edu.itba.paw.webapp.miscellaneous;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class StaticCache {
    public static int MAX_TIME = 31536000;
    public static void setUnconditionalCache(Response.ResponseBuilder responseBuilder) {
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(MAX_TIME);
        cacheControl.getCacheExtension().put("public", "");
        cacheControl.getCacheExtension().put("immutable", "");
        cacheControl.setNoTransform(true);
        responseBuilder.cacheControl(cacheControl);
    }
    public static Response.ResponseBuilder getConditionalCacheResponse(Request request, EntityTag eTag) {
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response != null) {
            final CacheControl cacheControl = new CacheControl();
            cacheControl.setNoCache(true);
        }
        return response;
    }
}
