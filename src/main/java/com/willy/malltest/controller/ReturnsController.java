package com.willy.malltest.controller;


import com.willy.malltest.model.Returns;
import com.willy.malltest.repository.ReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ReturnsController {

    @Autowired
    private ReturnsRepository returnsRepo;


    @GetMapping("/returns/list")
    public String findAllReturns(Model model)
    {
        List<Returns> returnsList = returnsRepo.findAll();

        model.addAttribute("returnsList", returnsList);

        return "returns/showPage";
    }


    @PostMapping("/returns/add")
    public String addReturns(@RequestParam Integer orderID, Model model) {

        Returns returns = new Returns();
        returns.setOrderID(orderID);
        returns.setReturnStatus("dealing");

        returnsRepo.save(returns);

        //model.addAttribute();

        return "returns/showPage";
    }

    @PutMapping("/returns/edit")
    public String editReturns(@ModelAttribute("returns") Returns returns ) {

        Returns existing = returnsRepo.findById(returns.getReturnID()).orElse(null);
        if(existing!=null)
        {
            returnsRepo.save(existing);
        }

        return "returns/showPage";
    }

    //for test
    @PutMapping("/returns/edit2")
    public String editReturns2() {

        Returns returns = new Returns();
        returns.setReturnID(2);
        returns.setOrderID(2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = "2024-03-07";
        try {
            Date date = dateFormat.parse(dateString);
            returns.setReturnDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        returns.setReturnStatus("dealing");
        returnsRepo.save(returns);

        return "returns/showPage";
    }



    @DeleteMapping("/returns/delete")
    public String deleteReturns(@RequestParam Integer id) {
        returnsRepo.deleteById(id);

        return "returns/showPage";
    }
}
