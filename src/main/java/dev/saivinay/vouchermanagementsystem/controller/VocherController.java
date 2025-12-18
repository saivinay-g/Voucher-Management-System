package dev.saivinay.vouchermanagementsystem.controller;

import dev.saivinay.vouchermanagementsystem.model.Voucher;
import dev.saivinay.vouchermanagementsystem.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class VocherController {
    @Autowired
    private VoucherService service;

    @GetMapping("vouchers")
    public ResponseEntity<List<Voucher>> getAllVouchers(){
        return new ResponseEntity<>(service.getAllVouchers(), HttpStatus.OK);
    }
    @PostMapping("voucher")
    public ResponseEntity<Voucher> addVoucher(@RequestBody Voucher voucher){
        return new ResponseEntity<>(service.addOrUpdateVoucher(voucher), HttpStatus.CREATED);
    }

    @GetMapping("isAvailable/{voucher}")
    public ResponseEntity<String> isVoucherValid(@PathVariable String voucher){
        return new ResponseEntity<>(service.isValid(voucher), HttpStatus.OK);
    }

    @GetMapping("claim/{voucher}")
    public ResponseEntity<String> claimVoucher(@PathVariable String voucher){
        String res = service.claimVoucher(voucher);
        if(res.contains("not valid"))
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("voucher")
    public ResponseEntity<Voucher> updateVoucher(@RequestBody Voucher voucher){
        Voucher updatedVoucher = service.addOrUpdateVoucher(voucher);
        return new ResponseEntity<>(updatedVoucher, updatedVoucher.getVid()>0?HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("voucher/{voucher}")
    public ResponseEntity<String> deleteVoucher(@PathVariable String voucher){
        String res = service.deleteVoucher(voucher);
        return new ResponseEntity<>(res, res.toLowerCase().contains("success") ? HttpStatus.OK:HttpStatus.NOT_FOUND);
    }
}
