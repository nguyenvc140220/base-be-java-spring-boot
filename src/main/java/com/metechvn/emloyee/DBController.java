package com.metechvn.emloyee;

import com.metechvn.common.BaseResponse;
import com.metechvn.common.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/db")
public class DBController {
    private final DBService dbService;
    @GetMapping("/create")
    public ResponseEntity<BaseResponse<Integer>> createDB(@RequestParam(name="dbname") String dbname) {
        System.out.println("Log create");
        try {
            return ResponseBuilder.build(BaseResponse.onOk(dbService.createDB(dbname)));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseBuilder.build((BaseResponse.onError(null, 400, e.getMessage())));
        }
    }
}
