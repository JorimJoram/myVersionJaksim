package com.twinkle.JakSim.controller.inbody;

import com.twinkle.JakSim.model.dto.inbody.InbodyDto;
import com.twinkle.JakSim.model.service.inbody.InbodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inbody/api")
public class InbodyRestApi {
    private final InbodyService inbodyService;

    @GetMapping("/chart-data")
    public List<InbodyDto> getInbodyList(@AuthenticationPrincipal User user){
        return inbodyService.getInbodies(user.getUsername());
    }

    @GetMapping("/total-page")
    public int getTotalPage(@AuthenticationPrincipal User user){
        return inbodyService.getTotalPage(user.getUsername());
    }

    @GetMapping("/{page}")
    public List<InbodyDto> getPageData(@AuthenticationPrincipal User user, @PathVariable("page") int page){
        return inbodyService.getInbodiesByPage(user.getUsername(), page);
    }

    @PostMapping("/create")
    public int createInbody(@AuthenticationPrincipal User user, @RequestBody InbodyDto data){
        return inbodyService.create(user.getUsername(), data);
    }

    @DeleteMapping("/remove/{id}")
    public int deleteInbody(@PathVariable("id") int id){
        return inbodyService.delete(id);
    }
}
