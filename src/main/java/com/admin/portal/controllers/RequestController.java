package com.admin.portal.controllers;

import com.admin.portal.entities.Request;
import com.admin.portal.models.RequestResponse;
import com.admin.portal.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public List<Request> findAllRequests () {
        return requestService.findAllRequests();
    }


    @GetMapping("/searchByReference/{refNo}")
    public RequestResponse findRequestByReference(@PathVariable("refNo") Long refNo) {
        return this.requestService.findRequestByRefNo(refNo);
    }
}
