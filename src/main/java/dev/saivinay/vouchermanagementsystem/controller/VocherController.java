package dev.saivinay.vouchermanagementsystem.controller;

import dev.saivinay.vouchermanagementsystem.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.saivinay.vouchermanagementsystem.service.VoucherService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class VocherController {

    @Autowired
    private VoucherService service;

    @GetMapping("vouchers")
    public ResponseEntity<List<Voucher>> getAllVouchers() {
        return ResponseEntity.ok(service.getAllVouchers());
    }

    @PostMapping("voucher")
    public ResponseEntity<Voucher> addVoucher(@RequestBody Voucher voucher) {
        return new ResponseEntity<>(service.addOrUpdateVoucher(voucher), HttpStatus.CREATED);
    }

    @GetMapping("isAvailable/{voucher}")
    public ResponseEntity<String> isVoucherValid(@PathVariable String voucher) {
        return ResponseEntity.ok(service.isValid(voucher));
    }

    @PostMapping("claim/{voucher}")
    public ResponseEntity<String> claimVoucher(@PathVariable String voucher) {
        String res = service.claimVoucher(voucher);
        return res.contains("not valid")
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(res)
                : ResponseEntity.ok(res);
    }

    @PutMapping("voucher")
    public ResponseEntity<Voucher> updateVoucher(@RequestBody Voucher voucher) {
        return ResponseEntity.ok(service.addOrUpdateVoucher(voucher));
    }

    @DeleteMapping("voucher/{voucher}")
    public ResponseEntity<String> deleteVoucher(@PathVariable String voucher) {
        String res = service.deleteVoucher(voucher);
        return res.contains("Not Found")
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(res)
                : ResponseEntity.ok(res);
    }
}
