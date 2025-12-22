package dev.saivinay.vouchermanagementsystem.service;

import dev.saivinay.vouchermanagementsystem.model.Voucher;
import dev.saivinay.vouchermanagementsystem.repo.VoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherRepo repo;

    public Voucher addOrUpdateVoucher(Voucher voucher) {
        return repo.save(voucher);
    }

    public List<Voucher> getAllVouchers() {
        return repo.findAll();
    }

    public String isValid(String voucher) {
        return Boolean.TRUE.equals(repo.findByVoucherAvailable(voucher))
                ? "Valid"
                : "Invalid";
    }

    public Voucher getByVoucherCode(String vcode) {
        return repo.findByVcode(vcode);
    }

    public String claimVoucher(String voucher) {

        Voucher v = repo.findByVcode(voucher);
        if (v == null || !v.isActive() || v.getClaimLimit() <= 0) {
            return "Voucher is not valid";
        }

        v.setClaimLimit(v.getClaimLimit() - 1);

        if (v.getClaimLimit() == 0) {
            v.setActive(false);
        }

        repo.save(v);
        return "Claimed Successfully";
    }

    public String deleteVoucher(String vcode) {
        Voucher voucher = repo.findByVcode(vcode);

        if (voucher == null) {
            return "Voucher Not Found";
        }

        repo.delete(voucher);
        return "Successfully Deleted";
    }
}
