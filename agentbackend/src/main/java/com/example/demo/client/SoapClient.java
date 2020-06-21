//package com.example.demo.client;
//
//import com.example.demo.model.Car;
//import com.example.demo.repository.AdvertisementRepository;
//import com.soapclient.api.domain.ClientRequest;
//import com.soapclient.api.domain.ServerRespond;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//import org.springframework.stereotype.Service;
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
//    private WebServiceTemplate template;
//
//    public ServerRespond respond(ClientRequest request) {
////        template = new WebServiceTemplate(marshaller);
////        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8081/ws", request);
////
////        Car c = this.advertisementRepository.findOneById(Long.parseLong(request.getUserId()));
////        c.setSecondId(respond.getId());
////        this.advertisementRepository.save(c);
////
////        return respond;
//    }
//
//}