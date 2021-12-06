package net.xdclass.controller;


import net.xdclass.model.AddressDO;
import net.xdclass.service.AddressService;
import net.xdclass.vo.AddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/api/address/v1")
public class AdminController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/find/{address_id}")
    public Object detail(@PathVariable("address_id") long address_id){
        AddressVO detail = addressService.detail(address_id);
        return detail;
    }
}

