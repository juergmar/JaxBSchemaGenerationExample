package de.ma.form.jaxbschemagenerationexample;

import com.example.jaxb.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> getReport() {
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(user);
    }
}
