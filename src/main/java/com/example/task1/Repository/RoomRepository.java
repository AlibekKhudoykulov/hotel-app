package com.example.task1.Repository;

import com.example.task1.Entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    boolean existsByNumber(Integer number);
    Page<Room> findAllByHotel_Id(Integer hotel_id, Pageable pageable);
    boolean existsByNumberAndHotelId(Integer number, Integer hotel_id);

}
