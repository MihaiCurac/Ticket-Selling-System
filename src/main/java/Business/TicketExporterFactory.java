package Business;

public class TicketExporterFactory {

    public TicketExporter getTicketExporter(String format) {
        if (format.equalsIgnoreCase("csv")) {
            return new CsvTicketExporter("tickets.csv");
        } else if (format.equalsIgnoreCase("xml")) {
            return new XmlTicketExplorer("tickets.xml");
        } else {
            throw new IllegalArgumentException("Invalid format: " + format);
        }
    }
}
