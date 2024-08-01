package com.swfias.csmdesktopui;

import com.swfias.csmdesktopui.tableModel.PersonTableModel;
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
import org.swfias.dtos.PersonDto;
import org.swfias.enums.PersonType;
import org.swfias.enums.SeverityType;
import org.swfias.services.CaseService;
import org.swfias.services.PersonService;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;

public class CSMAdminController implements Initializable {

    @FXML
    private Label adminNameLabel;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<PersonType> typeField;

    @FXML
    private TableView<PersonTableModel> tableView;
    @FXML
    private TableColumn<PersonTableModel, String> column1;
    @FXML
    private TableColumn<PersonTableModel, String> column2;
    @FXML
    private TableColumn<PersonTableModel, String> column3;
    @FXML
    private TableColumn<PersonTableModel, String> column4;
    @FXML
    private TableColumn<PersonTableModel, String> column5;
    @FXML
    private TableColumn<PersonTableModel, String> column6;
    @FXML
    private TableColumn<PersonTableModel, String> column7;
    @FXML
    private TableColumn<PersonTableModel, String> column8;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterComboBox;

    private ObservableList<PersonTableModel> data;
    private FilteredList<PersonTableModel> filteredData;

    private List<PersonDto> personDtos;
    // For the second view
    @FXML
    private TableView<TableModel> tableView2;
    @FXML
    private TableColumn<TableModel, String> column1_1;
    @FXML
    private TableColumn<TableModel, String> column2_1;
    @FXML
    private TableColumn<TableModel, String> column3_1;
    @FXML
    private TableColumn<TableModel, String> column4_1;
    @FXML
    private TableColumn<TableModel, String> column5_1;
    @FXML
    private TableColumn<TableModel, String> column6_1;
    @FXML
    private TableColumn<TableModel, String> column7_1;
    @FXML
    private TableColumn<TableModel, String> column8_1;
    @FXML
    private TextField searchField2;
    @FXML
    private ComboBox<String> filterComboBox2;

    private ObservableList<TableModel> data2;
    private FilteredList<TableModel> filteredData2;
    private List<CaseDto> caseDtos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.typeField.getItems().addAll(EnumSet.allOf(PersonType.class));
        column1.setCellValueFactory(new PropertyValueFactory<>("userId"));
        column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        column3.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        column5.setCellValueFactory(new PropertyValueFactory<>("password"));
        column6.setCellValueFactory(new PropertyValueFactory<>("email"));
        column7.setCellValueFactory(new PropertyValueFactory<>("address"));
        column8.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        this.filterComboBox.getItems().addAll("Type", "FirstName", "LastName", "UserId", "Email","Phone");
        filterComboBox.getSelectionModel().select("Type");
        loadTask();


        // For second view
        column1_1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column2_1.setCellValueFactory(new PropertyValueFactory<>("description"));
        column3_1.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        column4_1.setCellValueFactory(new PropertyValueFactory<>("status"));
        column5_1.setCellValueFactory(new PropertyValueFactory<>("severity"));
        column6_1.setCellValueFactory(new PropertyValueFactory<>("solvedBy"));
        column7_1.setCellValueFactory(new PropertyValueFactory<>("resolutionDetails"));
        column8_1.setCellValueFactory(new PropertyValueFactory<>("resolvedDate"));

        this.filterComboBox2.getItems().addAll("Title", "Status", "Severity", "Created", "Fix Date");
        filterComboBox2.getSelectionModel().select("Title");
        loadTask2();
    }

    public void onLogOutAdminClick(ActionEvent actionEvent) {
        try{
            CSMApplication.switchToLogInView();
        }catch (Exception e){
            System.out.println("Can't load new window");
        }
    }
    public void setAdmin(String adminName) {
        adminNameLabel.setText("Welcome, " + adminName  + "!");
    }

    public void onCreateNewEmployee(ActionEvent actionEvent) {
        PersonService personService = new PersonService(new PersonDao());
        boolean isAdded = personService.addNewPerson(typeField.getValue(), firstNameField.getText(), lastNameField.getText(), passwordField.getText(), emailField.getText(), addressField.getText(), phoneNumberField.getText());
        if (isAdded == true) {

            typeField.setValue(null);
            firstNameField.setText("");
            lastNameField.setText("");
            passwordField.setText("");
            emailField.setText("");
            addressField.setText("");
            phoneNumberField.setText("");

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Saved!");
            infoAlert.setHeaderText(" Your Information has been saved!");
            infoAlert.showAndWait();
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Something went wrong!");
            warningAlert.setHeaderText("System Error!");
            warningAlert.setContentText("Contact system administrator.");
            warningAlert.showAndWait();
        }
    }

    private void loadDataFromDatabase() {
        PersonService personService = new PersonService(new PersonDao());
        personDtos = personService.getAllPersons();
        List<PersonTableModel> tableData = new ArrayList<>();
        PersonTableModel tableModelInstance;
        for (PersonDto personDto : personDtos) {
            tableModelInstance = new PersonTableModel(
                    personDto.getUserId(),
                    personDto.getType().toString(),
                    personDto.getFirstName(),
                    personDto.getLastName(),
                    personDto.getPassword(),
                    personDto.getEmail(),
                    personDto.getAddress(),
                    personDto.getPhoneNumber()
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
                    case "Type":
                        return tableModel.getType().toLowerCase().contains(lowerCaseFilter);
                    case "FirstName":
                        return tableModel.getFirstName().toLowerCase().contains(lowerCaseFilter);
                    case "LastName":
                        return tableModel.getLastName().toLowerCase().contains(lowerCaseFilter);
                    case "UserId":
                        return tableModel.getUserId().toLowerCase().contains(lowerCaseFilter);
                    case "Email":
                        return tableModel.getEmail().toLowerCase().contains(lowerCaseFilter);
                    case "Phone":
                        return tableModel.getPhoneNumber().toLowerCase().contains(lowerCaseFilter);
                    default:
                        return false;
                }
            });
        });

        filterComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchField.setText("");
        });
    }

    private void loadTask(){
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


    private void loadDataFromDatabase2() {
        CaseService caseService = new CaseService(new CaseDao(), new PersonDao());
        PersonService personService = new PersonService(new PersonDao());
        caseDtos = caseService.getAllCases();
        List<TableModel> tableData2 = new ArrayList<>();
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
                    caseDto.getResolvedDate()!= null ? FORMATTER.format(caseDto.getResolvedDate()) : ""
            );
            tableData2.add(tableModelInstance);
        }
        data2 = FXCollections.observableArrayList(tableData2);

        tableView2.setItems(data2);

    }

    private void setupFiltering2() {
        searchField2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData2.setPredicate(tableModel2 -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String selectedColumn = filterComboBox2.getSelectionModel().getSelectedItem();

                switch (selectedColumn) {
                    case "Title":
                        return tableModel2.getTitle().toLowerCase().contains(lowerCaseFilter);
                    case "Status":
                        return tableModel2.getStatus().toLowerCase().contains(lowerCaseFilter);
                    case "Severity":
                        return tableModel2.getSeverity().toLowerCase().contains(lowerCaseFilter);
                    case "Created":
                        return tableModel2.getCreatedDate().toLowerCase().contains(lowerCaseFilter);
                    case "Fix Date":
                        return tableModel2.getResolvedDate().toLowerCase().contains(lowerCaseFilter);
                    default:
                        return false;
                }
            });
        });

        filterComboBox2.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchField2.setText("");
        });
    }
    private void loadTask2(){
        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                loadDataFromDatabase2();
                return null;
            }
        };

        loadDataTask.setOnSucceeded(e -> {
            filteredData2 = new FilteredList<>(data2, p -> true);
            tableView2.setItems(filteredData2);
            setupFiltering2();
        });

        new Thread(loadDataTask).start();
    }
}
