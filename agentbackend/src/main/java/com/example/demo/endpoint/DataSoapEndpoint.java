package com.example.demo.endpoint;

import com.example.demo.service.DataSoapService;
import com.soapclient.api.domain.ClientRequestRentalRequest;
import com.soapclient.api.domain.ServerRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.ParseException;

/**
 * DataSoapEndpoint
 */
@Endpoint
public class DataSoapEndpoint {

    @Autowired
    private DataSoapService dataSoapService;

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClientRequestRentalRequest")
    @ResponsePayload
    public ServerRespond addRentalRequestSoap(@RequestPayload ClientRequestRentalRequest request) {
        return dataSoapService.clientRentalRequest(request);
    }

}