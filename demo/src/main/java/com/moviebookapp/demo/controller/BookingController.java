package com.moviebookapp.demo.controller;

import com.moviebookapp.demo.model.User;
import com.moviebookapp.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mba/booking")

public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/bookMovie/{movieId}")
    public Map<Object,Object> bookTicket(@PathVariable Long movieId,@RequestParam Integer noOfTickets,@RequestParam Long userId){
        return bookingService.bookTicket(movieId,noOfTickets,userId);
    }
    @DeleteMapping("/cancelMovie/{bookingCode}")
    public Map cancelTicket(@PathVariable String bookingCode){
        return bookingService.cancelTicket(bookingCode);
    }


}
