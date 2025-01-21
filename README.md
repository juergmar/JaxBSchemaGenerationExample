# JAXB Schema Generation Example

This project demonstrates XML schema-based code generation and serialization/deserialization using JAXB in a Spring Boot application. It provides a practical example of handling complex XML documents with proper namespace management and validation.

## Prerequisites

- Java 21 or later
- Maven 3.8.x or later
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── de/ma/form/
│   │       ├── generated/                  # Generated JAXB classes
│   │       │   └── ReportAuslagerungsanzeigeWrapper.java
│   │       └── jaxbschemagenerationexample/
│   │           ├── ReportController.java   # REST endpoint
│   │           ├── ReportService.java      # Business logic
│   │           └── XmlConfig.java          # XML configuration
│   └── resources/
│       ├── bindings.xjb                    # JAXB bindings configuration
│       ├── schema.xsd                      # Main XML schema
│       └── mmdl.xsd                        # Common types schema
```

## How It Works

### 1. XML Schema and Code Generation

The project uses two main XSD files:
- `schema.xsd`: Defines the main report structure
- `mmdl.xsd`: Contains common data types

JAXB code generation is configured using:
- `bindings.xjb`: Controls the JAXB binding process
- Maven's `jaxb2-maven-plugin`: Handles the actual code generation

During the Maven build process:
1. The JAXB plugin reads the schema files
2. Generates Java classes in the target directory
3. The build-helper plugin adds the generated sources to the project

### 2. XML Serialization/Deserialization

The project implements a wrapper approach for proper XML handling:

```java
@XmlRootElement(name = "reportAuslagerungsanzeige", 
                namespace = "http://mvp.bafin.de/sp/v1/mmdl/auslanz/")
public class ReportAuslagerungsanzeigeWrapper {
    private ReportAuslagerungsanzeige report;
    // ... rest of implementation
}
```

This wrapper ensures:
- Correct root element handling
- Proper namespace management
- Valid XML structure

## Building and Running

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on port 8081 (configurable in application.properties).

## Usage

### Generating a Report

To get a report in XML format, send a GET request to the endpoint:

```bash
curl -X GET http://localhost:8081/api/report \
     -H "Accept: application/xml"
```

### Response Example

```xml
<?xml version="1.0" encoding="UTF-8"?>
<reportAuslagerungsanzeige xmlns="http://mvp.bafin.de/sp/v1/mmdl/auslanz/">
    <allgemeineAngaben>
        <artUnternehmen>KI</artUnternehmen>
        <artMeldung>ABSICHT</artMeldung>
    </allgemeineAngaben>
    <!-- ... rest of the XML structure -->
</reportAuslagerungsanzeige>
```

## Code Generation Details

### Maven Plugin Configuration

The JAXB code generation is configured in `pom.xml`:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>jaxb2-maven-plugin</artifactId>
    <configuration>
        <sources>
            <source>${project.basedir}/src/main/resources/schema.xsd</source>
        </sources>
        <xjbSources>
            <xjbSource>${project.basedir}/src/main/resources/bindings.xjb</xjbSource>
        </xjbSources>
        <outputDirectory>${project.build.directory}/generated-sources/jaxb</outputDirectory>
        <clearOutputDir>false</clearOutputDir>
    </configuration>
</plugin>
```

### Customizing Generated Code

To customize the generated classes:

1. Modify the `bindings.xjb` file to change:
    - Package names
    - Class names
    - Property naming
    - Type mappings

2. Update the XSD files for:
    - Data structure changes
    - Validation rules
    - New elements or types

## Troubleshooting

Common issues and solutions:

1. **Generated classes not found**
    - Run `mvn clean install` to regenerate classes
    - Check the `target/generated-sources` directory
    - Ensure your IDE has properly imported the generated sources

2. **XML serialization errors**
    - Verify namespace declarations
    - Check wrapper class annotations
    - Validate XML against schema

3. **Build failures**
    - Verify Java version compatibility
    - Check for schema validation errors
    - Ensure all dependencies are resolved

## Dependencies

Key dependencies used in the project:

- Spring Boot Starter Web
- JAXB API and Runtime
- Jackson XML formatter
- Spring OXM
- Project Lombok

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
