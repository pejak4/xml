package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.model.Occupancy;
import com.example.demo.repository.AdvertisementRepository;
import com.example.demo.repository.OccupancyRepository;
import com.soapclient.api.domain.ClirentRequestOccupancy;
import com.soapclient.api.domain.ServerRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class OccupancyService {

    @Autowired
    private OccupancyRepository occupancyRepository;

    @Autowired
    private AdvertisementRepository carRepository;

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public void saveOccupancy(ClirentRequestOccupancy cld) throws ParseException {
        Car c = carRepository.findOneById(Long.parseLong(cld.getCarId()));

        Instant instant1 = Instant.parse(cld.getStartDate());
        Instant instant2 = Instant.parse(cld.getEndDate());

        Date startDate = Date.from(instant1);
        Date endDate = Date.from(instant2);

        Timestamp tss = new Timestamp(startDate.getTime());
        Timestamp tse = new Timestamp(endDate.getTime());


        Occupancy o = Occupancy.builder().startDate(tss).endDate(tse).occupancyCar(c).build();

        cld.setCarId(c.getSecondId().toString());

        template = new WebServiceTemplate(marshaller);
        ServerRespond respond = (ServerRespond) template.marshalSendAndReceive("http://localhost:8081/ws", cld);

        o.setSecondId(respond.getId());


        this.occupancyRepository.save(o);
    }

    public List<Occupancy> findOccupancyAndRequest(Timestamp start_date, Timestamp end_date, Long occupancy_car_id) {
        return this.occupancyRepository.findOccupancyAndRequest(start_date, end_date, occupancy_car_id);
    }
}
