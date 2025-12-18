package dev.saivinay.vouchermanagementsystem.repo;

import dev.saivinay.vouchermanagementsystem.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, Integer> {
    @Query("SELECT v.isActive FROM Voucher v WHERE v.vcode=:voucher")
    String findByVoucherAvailable(String voucher);

    @Query("SELECT v FROM Voucher v WHERE v.vcode=:vcode")
    Voucher findByVcode(String vcode);
}
