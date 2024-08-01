package com.swfias.csmdesktopui;

import com.swfias.csmdesktopui.tableModel.TableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.swfias.daos.CaseDao;
import org.swfias.daos.PersonDao;
import org.swfias.dtos.CaseDto;
import org.swfias.dtos.StaffDto;
import org.swfias.enums.StatusType;
import org.swfias.services.CaseService;
import org.swfias.services.PersonService;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class CSMStaffController implements Initializable {

    @FXML
    private Label staffNameLabel;
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
    private TextField searchFieldForTitle;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private ComboBox<StatusType> filterComboBox2;

    @FXML
    public TextArea reportDetails;

    @FXML
    public DatePicker datePicker;

    private ObservableList<TableModel> data;
    private FilteredList<TableModel> filteredData;
    private List<CaseDto> caseDtos;
    private StaffDto staff;


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

        this.filterComboBox.getItems().addAll("Title", "Status", "Severity", "Created", "Fix Date");
        filterComboBox.getSelectionModel().select("Title");
        filterComboBox2.getItems().addAll(EnumSet.allOf(StatusType.class));
        filterComboBox2.getSelectionModel().select(StatusType.OPEN);
        // Load data from database on a background thread
        loadTask();
    }

    private void loadDataFromDatabase() {
        CaseService caseService = new CaseService(new CaseDao(), new PersonDao());
        PersonService personService = new PersonService(new PersonDao());
        caseDtos = caseService.getByAssignedToId(staff.getId());
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

    public void onLogOutStaffClick(ActionEvent actionEvent) {
        try {
            CSMApplication.switchToLogInView();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }

    public void setStaffName(String staffName) {
        staffNameLabel.setText("Welcome, " + staffName + "!");
    }

    public void setStaff(StaffDto staff) {
        this.staff = staff;
    }


    public void onCreateClickStaff(ActionEvent actionEvent) {
        CaseService caseService = new CaseService(new CaseDao(), new PersonDao());
        LocalDate localDate = datePicker.getValue();
        Date resolvedDate = (localDate != null) ? Date.valueOf(localDate) : null;
        String searchTitle = searchFieldForTitle.getText().trim();
        if (searchTitle.isEmpty()) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("No title entered!");
            warningAlert.setHeaderText("Please enter a title to search.");
            warningAlert.showAndWait();
            return;
        }
        CaseDto matchingCase = (CaseDto) caseService.getCaseByTitle(searchTitle);
        if (matchingCase == null) {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("No case found!");
            warningAlert.setHeaderText("No case found with the given title.");
            warningAlert.showAndWait();
            return;
        }

        matchingCase.setResolutionDetails(reportDetails.getText());
        matchingCase.setResolvedDate(resolvedDate);
        matchingCase.setStatus(filterComboBox2.getValue());
        if (caseService.updateCase(matchingCase)) {
            reportDetails.setText("");
            datePicker.setValue(null);
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Report has been saved!");
            infoAlert.setHeaderText("Report :" + matchingCase.getTitle());
            infoAlert.setContentText("Report Details :" + matchingCase.getDescription());
            infoAlert.showAndWait();
            loadTask();
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Something went wrong!");
            warningAlert.setHeaderText("System Error!");
            warningAlert.setContentText("Contact system administrator.");
            warningAlert.showAndWait();
        }
    }
}
