package com.example.task1.Controller;

import com.example.task1.Entity.Hotel;
import com.example.task1.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/all")
    public List<Hotel> getall(){
        return hotelRepository.findAll();
    }
    @GetMapping("/one/{id}")
    public Hotel getOne(@PathVariable Integer id){
        Optional<Hotel> byId = hotelRepository.findById(id);
        if (!byId.isPresent()) return new Hotel();
        return byId.get();
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        hotelRepository.deleteById(id);
        return "Deleted succesfully";
    }

    @PostMapping
    public String add(@RequestBody Hotel hotel){
        if (hotelRepository.existsByName(hotel.getName())) return "Allready exist";

        Hotel hotel1=new Hotel();
        hotel1.setName(hotel.getName());
        hotelRepository.save(hotel1);
        return "Added succesfully";

    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody Hotel hotel){
        Optional<Hotel> byId = hotelRepository.findById(id);
        if (!byId.isPresent()) return "Not found";
        if (hotelRepository.existsByName(hotel.getName())) return "Already exist";

        Hotel hotel1 = byId.get();
        hotel1.setName(hotel.getName());
        hotelRepository.save(hotel1);
        return "Changed succesfully";


    }
}
