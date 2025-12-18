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
        boolean res = Boolean.parseBoolean(repo.findByVoucherAvailable(voucher));
        if(res){
            return "Valid";
        }
        return "InValid";
    }

    public Voucher getByVoucherCode(String vcode){
        return repo.findByVcode(vcode);
    }

    public String claimVoucher(String voucher) {
        if(isValid(voucher).equals("Valid")){

            Voucher v = repo.findByVcode(voucher);
            v.setClaimLimit(v.getClaimLimit()-1);
            if(v.getClaimLimit()==0){
                v.setActive(false);
            }
            repo.save(v);
            return "Claimed Successfully";
        }
        return "Voucher is not valid";
    }

    public String deleteVoucher(String vcode) {
        Voucher voucher = getByVoucherCode(vcode);
        repo.delete(voucher);
        if(voucher.getVid()>0){
            return "Successfully Deleted";
        }
        return "Voucher Not Found";
    }
}
