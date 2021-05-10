package com.example.task1.Controller;

import com.example.task1.Entity.Room;
import com.example.task1.Repository.HotelRepository;
import com.example.task1.Repository.RoomRepository;
import com.example.task1.payload.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/all")
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @GetMapping("/one/{id}")
    public Room getOne(@PathVariable Integer id) {
        Optional<Room> byId = roomRepository.findById(id);
        if (!byId.isPresent()) return new Room();
        return byId.get();
    }

    @GetMapping("/forHotel/{id}")
    public Page<Room> getRoomsForHotel(@PathVariable Integer id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAllByHotel_Id(id, pageable);
        return roomPage;

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        roomRepository.deleteById(id);
        return "Deleted succesfully";
    }

    @PostMapping
    public String add(@RequestBody RoomDTO roomDTO) {
        if (roomRepository.existsByNumber(roomDTO.getNumber())) return "This room number already exist";
        if (roomRepository.existsByNumberAndAndHotel_Id(roomDTO.getNumber(), roomDTO.getHotel_id()))
            return "Already exist";
        if (!hotelRepository.findById(roomDTO.getHotel_id()).isPresent()) return "Hotel not found";

        Room room = new Room();
        room.setNumber(roomDTO.getNumber());
        room.setFloor(roomDTO.getFloor());
        room.setSize(roomDTO.getSize());
        room.setHotel(hotelRepository.getOne(roomDTO.getHotel_id()));
        return "Added succesfully";

    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDTO roomDTO) {
        Optional<Room> byId = roomRepository.findById(id);
        if (!byId.isPresent()) return "Not found";
        if (!hotelRepository.findById(roomDTO.getHotel_id()).isPresent()) return "Hotel not found";
        if (roomRepository.existsByNumberAndAndHotel_Id(roomDTO.getNumber(), roomDTO.getHotel_id())) {
            return "Already exist";
        }
        Room room = byId.get();
        if (roomDTO.getNumber()!=null) {
            room.setNumber(roomDTO.getNumber());
        }
        if (roomDTO.getFloor()!=null){
            room.setFloor(roomDTO.getFloor());
        }
        if (roomDTO.getSize() != null){
            room.setSize(roomDTO.getSize());
        }
        if (roomDTO.getHotel_id()!=null){
            room.setHotel(hotelRepository.getOne(roomDTO.getHotel_id()));
        }
        roomRepository.save(room);
        return "Edit succesfully";
    }

}
