package com.app.companyms.company.messaging;


import com.app.companyms.company.service.CompanyService;
import com.app.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class ReviewMessageConsumer {

    private final CompanyService companyService;
//    public boolean isReady;


    public ReviewMessageConsumer(CompanyService companyService){

        this.companyService = companyService;
//        this.isReady= healthCheckController.healthCheck().hasBody();?

    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        try{
             companyService.updateCompanyRating(reviewMessage);
        }
        catch (Exception e){
            System.err.println("Failed to process : "+e.getMessage());
            throw e;
        }
    }


}
