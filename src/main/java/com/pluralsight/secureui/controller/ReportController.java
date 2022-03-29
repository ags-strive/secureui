package com.pluralsight.secureui.controller;

import com.pluralsight.secureui.data.TollData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private WebClient webClient;

    @RequestMapping("/")
    public String loadHome() {
        return "home";
    }

    @RequestMapping("/report")
    public String loadReport(Model m) {
        m.addAttribute("tolldata", collectTollData());

        return "report";
    }

    private List<TollData> collectTollData() {
        // call downstream service
        Flux<TollData> response = this.webClient.get()
                .uri("http://localhost:8081/api/tolldata")
                .retrieve()
                .bodyToFlux(TollData.class);

        return response.collectList().block();
    }
}
