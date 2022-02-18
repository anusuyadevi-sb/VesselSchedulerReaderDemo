package com.apmm.datareader.exception;


import com.apmm.datareader.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(-2)
@Slf4j
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler
{
    public GlobalErrorWebExceptionHandler(DefaultErrorAttributes globalErrorAttributes, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(globalErrorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setMessageWriters(serverCodecConfigurer.getWriters());
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }
    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {

        final Map<String, Object> map = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        log.info("errorPropertiesMap::"+map.toString());
        String pathDetails=String.valueOf(map.get(Constants.PATH));
        String[] splitPathDetails=pathDetails.split("/");
        Map<String,Object> finalMap=new HashMap<>();
        finalMap.put(Constants.STATUS,String.valueOf(map.get(Constants.STATUS)));
        finalMap.put(Constants.RESOURCES,String.valueOf(map.get(Constants.PATH)));

        String statusFromMap= String.valueOf(map.get(Constants.STATUS));
        HttpStatus status;
        switch (statusFromMap) {
            case "500":

                finalMap.put(Constants.MESSAGE, Constants.ERR_MSG_500);
                status=HttpStatus.INTERNAL_SERVER_ERROR;
                break;

            case "404":

                if(splitPathDetails.length==3){
                    finalMap.put(Constants.STATUS,Constants.HTTP_STATUS_BAD_REQUEST);
                    finalMap.put(Constants.MESSAGE, Constants.ERR_MSG_400_ID_NOT_AVAIL);
                    status=HttpStatus.BAD_REQUEST;
                }
                else {
                    if(splitPathDetails[2].equals(Constants.EVENT))
                    finalMap.put(Constants.MESSAGE, Constants.ERR_MSG_404_ID_NOT_FOUND+splitPathDetails[3]);
                   else
                        finalMap.put(Constants.MESSAGE, Constants.ERR_MSG_404);
                    status = HttpStatus.NOT_FOUND;
                }
                break;

            case "405":
                finalMap.put(Constants.MESSAGE, Constants.ERR_MSG_405);
                status=HttpStatus.METHOD_NOT_ALLOWED;
                break;

            default:
                finalMap.put(Constants.MESSAGE, Constants.ERR_MSG_400_ID_NOT_VALID);
                status=HttpStatus.BAD_REQUEST;

        }
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(finalMap));
    }
}



