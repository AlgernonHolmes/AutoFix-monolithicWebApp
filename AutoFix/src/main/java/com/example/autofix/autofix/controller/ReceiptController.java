package com.example.autofix.autofix.controller;


import com.example.autofix.autofix.entities.ReceiptEntity;
import com.example.autofix.autofix.services.ReceiptService;
import com.example.autofix.autofix.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipt")
@CrossOrigin("*")
public class ReceiptController {

    /* API endpoints */

    /* Service layer methods */

    @Autowired
    ReceiptService receiptService;

    @GetMapping("/")
    public List<ReceiptEntity> getAllReceipts() {
        return receiptService.getReceipts();
    }


    @PostMapping("/{plate}")
    public ReceiptEntity createReceipt(@PathVariable String plate) {
        return receiptService.createReceipt(plate);
    }

}
