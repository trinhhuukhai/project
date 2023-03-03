package com.project.service;

import com.project.model.Customer;
import com.project.model.Shipping;
import com.project.repository.CustomerRepository;
import com.project.repository.ShippingRepository;
import com.project.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {
    @Autowired
    private ShippingRepository shippingRepository;

    public List<Shipping> getAllShipping(){
        return shippingRepository.findAll();
    }

    public ResponseEntity<ResponseResult> insertShip(Shipping newShip) {

//        List<Shipping> foundShip = shippingRepository.findByName(newShip.getShippingMethod().trim());
//        if(foundShip.size() > 0) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseResult("failed", "Shipping Method name already taken", "")
//            );
//        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Customer successfully", shippingRepository.save(newShip),1)
        );
    }


    public ResponseEntity<ResponseResult> updateShip(@RequestBody Shipping newShip, @PathVariable Long id) {
        Shipping updatedShip = shippingRepository.findById(id)
                .map(shipping -> {
                    shipping.setShippingAddress(newShip.getShippingAddress());
                    shipping.setShippingMethod(newShip.getShippingMethod());
                    shipping.setShippingDate(newShip.getShippingDate());
                    return shippingRepository.save(shipping);
                }).orElseGet(() -> {
                    newShip.setId(id);
                    return shippingRepository.save(newShip);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update shipping successfully", updatedShip,1)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteShip(@PathVariable Long id) {
        boolean exists = shippingRepository.existsById(id);
        if(exists) {
            shippingRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete Shipping successfully", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find Shipping to delete", "",1)
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Shipping> foundShip = shippingRepository.findById(id);
        return foundShip.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query Shipping successfully", foundShip,1)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find Shipping with id = "+id, "",1)
                );
    }
}
