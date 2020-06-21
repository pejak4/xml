//package com.example.demo.client;
//
//import com.example.carService.model.Car;
//import com.example.carService.repository.AdvertisementRepository;
//import com.example.carService.repository.RentalRequestRepository;
//import com.soapserveryt.api.soap.ClientRequestRentalRequest;
//import com.soapserveryt.api.soap.ServerRespond;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.function.ServerResponse;
//import org.springframework.ws.client.core.WebServiceTemplate;
//
///**
// * SoapClient
// */
//@Service
//public class SoapClient {
//    @Autowired
//    private Jaxb2Marshaller marshaller;
//
//    @Autowired
//    private AdvertisementRepository advertisementRepository;
//
//    @Autowired
//    private RentalRequestRepository rentalRequestRepository;
//
//    private WebServiceTemplate template;
//
//    public ServerResponse respond(ClientRequestRentalRequest request) {
//        template = new WebServiceTemplate(marshaller);
//        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8080/ws", request);
//
//        Car c = this.advertisementRepository.findOneById(Long.parseLong(request.getUserId()));
//        c.setSecondId(respond.getId());
//        this.advertisementRepository.save(c);
//
//        return respond;
//    }
//
//}