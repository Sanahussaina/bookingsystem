package com.example.bookingsystem;

import com.example.bookingsystem.model.Room;
import com.example.bookingsystem.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookingsystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingsystemApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(RoomRepository roomRepository) {
        return args -> {
            if (roomRepository.count() == 0) {
                for (int floor = 1; floor <= 10; floor++) {
                    for (int roomNum = 1; roomNum <= 10; roomNum++) {
                        Room room = new Room();
                        room.setFloorNumber(floor);
                        room.setRoomName("Room " + roomNum + " - Floor " + floor);
                        room.setCapacity(20);
                        roomRepository.save(room);
                    }
                }
            }
        };
    }
}