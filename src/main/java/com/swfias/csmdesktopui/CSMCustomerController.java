package com.swfias.csmdesktopui;

import com.swfias.csmdesktopui.tableModel.TableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.swfias.daos.CaseDao;
import org.swfias.daos.PersonDao;
import org.swfias.dtos.CaseDto;
import org.swfias.dtos.CustomerDto;
import org.swfias.enums.SeverityType;
import org.swfias.enums.StatusType;
import org.swfias.services.CaseService;
import org.swfias.services.PersonService;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;


public class CSMCustomerController implements Initializable {

    private CustomerDto customer;

    @FXML
    private ComboBox<SeverityType> reportSeverity;
    @FXML
    public TextField reportTitle;
    @FXML
    public TextArea reportDescription;
    @FXML
    private Label customerNameLabel;
    @FXML
    private TableView<TableModel> tableView;
    @FXML
    private TableColumn<TableModel, String> column1;
    @FXML
    private TableColumn<TableModel, String> column2;
    @FXML
    private TableColumn<TableModel, String> column3;
    @FXML
    private TableColumn<TableModel, String> column4;
    @FXML
    private TableColumn<TableModel, String> column5;
    @FXML
    private TableColumn<TableModel, String> column6;
    @FXML
    private TableColumn<TableModel, String> column7;
    @FXML
    private TableColumn<TableModel, String> column8;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterComboBox;
    private ObservableList<TableModel> data;
    private FilteredList<TableModel> filteredData;
    private List<CaseDto> caseDtos;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column2.setCellValueFactory(new PropertyValueFactory<>("description"));
        column3.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        column4.setCellValueFactory(new PropertyValueFactory<>("status"));
        column5.setCellValueFactory(new PropertyValueFactory<>("severity"));
        column6.setCellValueFactory(new PropertyValueFactory<>("solvedBy"));
        column7.setCellValueFactory(new PropertyValueFactory<>("resolutionDetails"));
        column8.setCellValueFactory(new PropertyValueFactory<>("resolvedDate"));

        reportSeverity.getItems().addAll(EnumSet.allOf(SeverityType.class));
        this.filterComboBox.getItems().addAll("Title", "Status", "Severity", "Created", "Fix Date");
        filterComboBox.getSelectionModel().select("Title");
        // Load data from database on a background thread
        loadTask();
    }

    private void loadDataFromDatabase() {
        CaseService caseService = new CaseService(new CaseDao(), new PersonDao());
        PersonService personService = new PersonService(new PersonDao());
        caseDtos = caseService.getAllById(customer.getId());
        List<TableModel> tableData = new ArrayList<>();
        SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        TableModel tableModelInstance;
        for (CaseDto caseDto : caseDtos) {
            tableModelInstance = new TableModel(
                    caseDto.getTitle(),
                    caseDto.getDescription(),
                    caseDto.getCreatedDate() != null ? FORMATTER.format(caseDto.getCreatedDate()) : "",
                    caseDto.getStatus().toString(),
                    caseDto.getSeverity().toString(),
                    personService.getPersonById(caseDto.getAssignedTo()).get().getFirstName() + " " + personService.getPersonById(caseDto.getAssignedTo()).get().getLastName(),
                    caseDto.getResolutionDetails(),
                    caseDto.getResolvedDate() != null ? FORMATTER.format(caseDto.getResolvedDate()) : ""
            );
            tableData.add(tableModelInstance);
        }
        data = FXCollections.observableArrayList(tableData);
        tableView.setItems(data);

    }

    private void setupFiltering() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tableModel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String selectedColumn = filterComboBox.getSelectionModel().getSelectedItem();

                switch (selectedColumn) {
                    case "Title":
                        return tableModel.getTitle().toLowerCase().contains(lowerCaseFilter);
                    case "Status":
                        return tableModel.getStatus().toLowerCase().contains(lowerCaseFilter);
                    case "Severity":
                        return tableModel.getSeverity().toLowerCase().contains(lowerCaseFilter);
                    case "Created":
                        return tableModel.getCreatedDate().toLowerCase().contains(lowerCaseFilter);
                    case "Fix Date":
                        return tableModel.getResolvedDate().toLowerCase().contains(lowerCaseFilter);
                    default:
                        return false;
                }
            });
        });

        filterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchField.setText("");
        });
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public void onLogOutCustomerClick(ActionEvent event) {

        try {
            CSMApplication.switchToLogInView();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }

    public void setCustomerNameLabel(String customerName) {
        customerNameLabel.setText("Welcome, " + customerName + "!");
    }


    public void onCreateClick(ActionEvent actionEvent) {
        CaseService caseService = new CaseService(new CaseDao(), new PersonDao());
        CaseDto caseDto = new CaseDto(this.customer.getId(), reportTitle.getText(), reportDescription.getText(), new Date(System.currentTimeMillis()), StatusType.OPEN, reportSeverity.getValue(), -1, null, null);
        try {
            if (caseService.addNewCase(caseDto)) {
                reportTitle.setText("");
                reportDescription.setText("");
                reportSeverity.cancelEdit();
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Report has been saved!");
                infoAlert.setHeaderText("Report :" + caseDto.getTitle());
                infoAlert.setContentText("Report Details :" + caseDto.getDescription());
                infoAlert.showAndWait();
                loadTask();
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Something went wrong!");
                warningAlert.setHeaderText("Duplicate title!");
                warningAlert.setContentText("Please enter new title.");
                warningAlert.showAndWait();
            }
        } catch (IllegalArgumentException e) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Duplicate Report Title!");
            warningAlert.setHeaderText("Case Title Error!");
            warningAlert.setContentText(e.getMessage());
            warningAlert.showAndWait();
            reportTitle.setText("");
            reportDescription.setText("");
            reportSeverity.cancelEdit();
        }
    }

    private void loadTask() {
        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                loadDataFromDatabase();
                return null;
            }
        };

        loadDataTask.setOnSucceeded(e -> {
            filteredData = new FilteredList<>(data, p -> true);
            tableView.setItems(filteredData);
            setupFiltering();
        });

        new Thread(loadDataTask).start();
    }
}
