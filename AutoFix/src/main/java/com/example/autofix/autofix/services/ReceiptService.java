package com.example.autofix.autofix.services;

import com.example.autofix.autofix.repositories.ReceiptRepository;
import com.example.autofix.autofix.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    /* Repository layer methods */

    @Autowired
    ReceiptRepository receiptRepository;
}
