package com.example.autofix.autofix.controller;


import com.example.autofix.autofix.services.ReceiptService;
import com.example.autofix.autofix.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/receipt")
@CrossOrigin("*")
public class ReceiptController {

    /* API endpoints */

    /* Service layer methods */

    @Autowired
    ReceiptService receiptService;
}
