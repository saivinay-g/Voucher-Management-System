package dev.saivinay.vouchermanagementsystem.service;

import dev.saivinay.vouchermanagementsystem.model.Voucher;
import dev.saivinay.vouchermanagementsystem.repo.VoucherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTest {
    @Mock
    VoucherRepo repo;

    @InjectMocks
    VoucherService service;

    Voucher voucher;
    List<Voucher> vouchers;

    @BeforeEach
    void init(){
        voucher = new Voucher();
        voucher.setVid(1);
        voucher.setVcode("SAVE10");
        voucher.setActive(true);
        voucher.setExpiry(LocalDate.now().plusWeeks(1));
        voucher.setDiscountPercentage(10);
        voucher.setClaimLimit(10);

        vouchers = new ArrayList<>();
        vouchers.add(voucher);
    }

    @Test
    void getAllVouchersShouldReturnAllVouchersSuccessfully(){
        when(repo.findAll()).thenReturn(vouchers);

        List<Voucher> voucherList = service.getAllVouchers();
        Voucher v = voucherList.getFirst();
        assertNotNull(v);
        assertEquals(1, v.getVid());
        assertEquals("SAVE10", v.getVcode());
        assertEquals(10, v.getClaimLimit());
        assertEquals(10, v.getDiscountPercentage());
    }

    @Test
    void addVoucherShouldAddTheVoucherSuccessfully(){
        when(repo.save(voucher)).thenReturn(voucher);
        Voucher v = service.addOrUpdateVoucher(voucher);
        Voucher expected = vouchers.getFirst();

        assertNotNull(v);
        assertEquals(expected.getVid(), v.getVid());
        assertEquals(expected.getVcode(), v.getVcode());
        assertEquals(expected.isActive(), v.isActive());
        assertEquals(expected.getDiscountPercentage(), v.getDiscountPercentage());
    }

    @Test
    void isVoucherValidShouldCheckVoucherValid(){
        when(repo.findByVoucherAvailable(voucher.getVcode())).thenReturn(true);
        String res = service.isValid(voucher.getVcode());
        assertEquals( "Valid", res);
    }

    @Test
    void isVoucherValidShouldReturnInvalidForInvalidVouchers(){
        when(repo.findByVoucherAvailable("INVALID")).thenReturn(false);
        String res = service.isValid("INVALID");
        assertEquals( "Invalid", res);
    }

    @Test
    void getByVoucherCodeShouldReturnVoucherSuccessfully(){
        when(repo.findByVcode(voucher.getVcode())).thenReturn(voucher);

        Voucher res = service.getByVoucherCode("SAVE10");

        assertNotNull(res);
        assertEquals(voucher.getVid(), res.getVid());
        assertEquals(voucher.getVcode(), res.getVcode());
        assertEquals(voucher.isActive(), res.isActive());
        assertEquals(voucher.getDiscountPercentage(), res.getDiscountPercentage());
    }

    @Test
    void claimVoucherShouldClaimTheVoucherSuccessfully(){
        when(repo.findByVcode(voucher.getVcode())).thenReturn(voucher);

        when(repo.save(voucher)).thenReturn(voucher);

        String status = service.claimVoucher("SAVE10");

        assertEquals("Claimed Successfully", status);
    }

    @Test
    void deleteVoucherShouldDeleteTheVoucherSuccessfully(){
        when(repo.findByVcode(voucher.getVcode())).thenReturn(voucher);
        doNothing().when(repo).delete(voucher);
        String res = service.deleteVoucher(voucher.getVcode());
        verify(repo, times(1)).delete(voucher);
        assertEquals("Successfully Deleted", res);
    }
}
