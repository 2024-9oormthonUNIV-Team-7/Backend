package goormthonUniv.floating.controller;

import goormthonUniv.floating.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Apicontroller {

    @GetMapping("/v3/api-docs")
    public Member test(){
        return new Member("Lee",10);
    }

}
