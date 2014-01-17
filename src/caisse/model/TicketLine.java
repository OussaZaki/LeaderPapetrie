package caisse.model;

public class TicketLine {

    private String text, style;

    public TicketLine(String text, String style) {
        this.text = text;
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public String getStyle() {
        return style;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}