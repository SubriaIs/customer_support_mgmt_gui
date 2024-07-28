module com.swfias.csmdesktopui {
    requires javafx.controls;
    requires javafx.fxml;
    requires csm;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.swfias.csmdesktopui.tableModel to javafx.base;
    opens com.swfias.csmdesktopui to javafx.fxml;
    exports com.swfias.csmdesktopui;
}