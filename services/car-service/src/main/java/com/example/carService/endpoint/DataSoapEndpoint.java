package com.example.carService.endpoint;

import com.example.carService.service.DataSoapService;
import com.soapserveryt.api.soap.*;
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

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClientRequest")
    @ResponsePayload
    public ServerRespond getLoadstatus(@RequestPayload ClientRequest request) {
        return dataSoapService.checkData(request);
    }

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClirentRequestOccupancy")
    @ResponsePayload
    public ServerRespond getLoadstatusClientRequest(@RequestPayload ClirentRequestOccupancy request) throws ParseException {
        return dataSoapService.addOccupancyReturnId(request);
    }

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClientRequestCars")
    @ResponsePayload
    public ClientRequestCars getAll(@RequestPayload ClientRequestCars request) {
        return dataSoapService.getAll(request);
    }

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClientRequestRentalRequestId")
    @ResponsePayload
    public void setRoleRentalRequest(@RequestPayload ClientRequestRentalRequestId request) {
        dataSoapService.setRoleRentalRequest(request);
    }

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClientRequestComment")
    @ResponsePayload
    public ServerRespond addComment(@RequestPayload ClientRequestComment request) {
        return dataSoapService.addComment(request);
    }

    @PayloadRoot(namespace = "http://www.soapserveryt.com/api/soap", localPart = "ClientRequestSetMileageAndDescription")
    @ResponsePayload
    public void addComment(@RequestPayload ClientRequestSetMileageAndDescription request) {
        dataSoapService.setMileageAndDescription(request);
    }
}