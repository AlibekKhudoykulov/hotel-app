package com.example.task1.Repository;

import com.example.task1.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    boolean existsByName(String name);

}
