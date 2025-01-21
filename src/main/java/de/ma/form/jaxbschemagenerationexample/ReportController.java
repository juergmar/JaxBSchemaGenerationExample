package de.ma.form.jaxbschemagenerationexample;

import de.ma.form.generated.ReportAuslagerungsanzeige;
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

    private final ReportService reportService;


    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ReportAuslagerungsanzeigeWrapper> getReport() {
        ReportAuslagerungsanzeige report = reportService.generateReport();
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(ReportAuslagerungsanzeigeWrapper.of(report));
    }
}
