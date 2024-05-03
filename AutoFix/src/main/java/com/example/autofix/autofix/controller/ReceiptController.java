package com.example.autofix.autofix.controller;


import com.example.autofix.autofix.entities.ReceiptEntity;
import com.example.autofix.autofix.services.ReceiptService;
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

    /*--------------------------------------------------------------------------------------------------------
     * getReceipts: method to find all receipts in the DB;
     *
     *
     * @return - a list with all receipts;
     --------------------------------------------------------------------------------------------------------*/
    @GetMapping("/")
    public List<ReceiptEntity> getAllReceipts() {
        return receiptService.getReceipts();
    }

    /*--------------------------------------------------------------------------------------------------------
     * createReceipt: method to create a new receipt entity and save it in the database;
     *
     * @param plate - the vehicle plate associated with the receipt;
     * @return - the newly created receipt entity;
     --------------------------------------------------------------------------------------------------------*/
    @PostMapping("/{plate}")
    public ReceiptEntity createReceipt(@PathVariable String plate) {
        return receiptService.createReceipt(plate);
    }



    /* Business layer methods */

    /*--------------------------------------------------------------------------------------------------------
     * applyCoupont: method to update the quantity of coupons for a specific brand and return the updated list;
     *
     * @param plate - plate of the vehicle;
     * @param coupon - a list of lists containing coupon values;
     * @return - the updated list of coupon values after decrementing the quantity of coupons for the specified brand;
     --------------------------------------------------------------------------------------------------------*/
    @PostMapping("/coupon/{plate}")
    public List<List<Double>> applyCoupon(@PathVariable String plate, @RequestBody List<List<Double>> coupons){
        return receiptService.applyCoupon(plate, coupons);
    }

    /*--------------------------------------------------------------------------------------------------------
     * applyRealPayment: method to calculate and apply the real payment for a receipt associated with a specific vehicle plate;
     *
     * @param plate - the vehicle plate for which the real payment is to be applied;
     * @return - the amount of calculated real payment applied to the receipt associated with the specified vehicle plate;
     --------------------------------------------------------------------------------------------------------*/
    @PostMapping("/realpayment/{plate}")
    public ReceiptEntity applyRealPayment(@PathVariable String plate){
        return receiptService.applyRealPayment(plate);
    }
}
