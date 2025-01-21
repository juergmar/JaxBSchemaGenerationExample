package de.ma.form.jaxbschemagenerationexample;

import de.ma.form.generated.ReportAuslagerungsanzeige;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wrapper class for ReportAuslagerungsanzeige to provide proper XML serialization.
 * This class serves as the root element for XML serialization.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "reportAuslagerungsanzeige", namespace = "http://mvp.bafin.de/sp/v1/mmdl/auslanz/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "report"
})
public class ReportAuslagerungsanzeigeWrapper {

    @XmlElement(name = "reportAuslagerungsanzeige", required = true, namespace = "http://mvp.bafin.de/sp/v1/mmdl/auslanz/")
    private ReportAuslagerungsanzeige report;

    /**
     * Creates a wrapper instance for the given report.
     *
     * @param report The report to wrap
     * @return A new wrapper instance containing the report
     */
    public static ReportAuslagerungsanzeigeWrapper of(ReportAuslagerungsanzeige report) {
        return new ReportAuslagerungsanzeigeWrapper(report);
    }
}
