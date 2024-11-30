package com.compass.ms_order.exeptions.handler;

import com.compass.ms_order.exeptions.EntityNotFoundException;
import com.compass.ms_order.exeptions.ErrorQuantityBelowZero;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ErrorDecoderClient implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    log.info("Error Decoder: {}, {}", methodKey, response);

    if(methodKey.contains("consultEmailUser")) {
        return new EntityNotFoundException("error: Not found client by email");
    }
    if(methodKey.contains("findProductByName")) {
      return new EntityNotFoundException("error: Not found product by name");
    }
    if(methodKey.contains("removeQuantity")) {
      return new ErrorQuantityBelowZero("error: The quantity is not below zero");
    }

    return null;
  }
}
