package pack;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class Controller implements Initializable
{

    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, Integer> ageColumn;
    @FXML
    private TableColumn<Client, String> heightColumn;
    @FXML
    private TableColumn<Client, Double> weightColumn;
    @FXML
    private TableColumn<Client, String> numberColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;
    @FXML
    private TableColumn<Client, String> descColumn;
    @FXML
    private TableColumn<Client, LocalDate> aptColumn;
    @FXML
    private TableColumn<Client, String> timeColumn;


    @FXML
    private TextField fNameIn;
    @FXML
    private TextField mNameIn;
    @FXML
    private TextField lNameIn;
    @FXML
    private TextField ageIn;
    @FXML
    private TextField heightIn;
    @FXML
    private TextField weightIn;
    @FXML
    private TextField numberIn;
    @FXML
    private TextField emailIn;
    @FXML
    private TextField descIn;

    @FXML
    private Label nameLabel;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeText;


    private String fName;
    private String mName;
    private String lName;

    private String name;
    private int age;
    private String height;
    private double weight;
    private String number;
    private String email;
    private String desc;

    static Client tempEntry = null;

    private LocalDate date;
    private String time;

    static String tempName;

    public static ArrayList<Client> Clients = new ArrayList<>();


    public void submit(ActionEvent event) throws IOException
    {
        try
        {

            fName = fNameIn.getText().trim();
            mName = mNameIn.getText().trim();
            lName = lNameIn.getText().trim();
            name = String.format("%s %s. %s", fName, mName, lName);
            age = Integer.parseInt(ageIn.getText());
            height = heightIn.getText().trim();
            weight = Double.parseDouble(weightIn.getText());
            number = numberIn.getText().trim();
            email = emailIn.getText().trim();
            desc = descIn.getText().trim();
            Clients.add(new Client(name, age, height, weight, number, email, desc, LocalDate.now(), "00:00"));
            fNameIn.setText(null);
            mNameIn.setText(null);
            lNameIn.setText(null);
            ageIn.setText(null);
            heightIn.setText(null);
            weightIn.setText(null);
            numberIn.setText(null);
            emailIn.setText(null);
            descIn.setText(null);
            table.getItems().clear();
            table.getItems().addAll(Clients);
        }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please use numbers in the fields age and weight.");
            alert.showAndWait();
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        Client.clear();
        for (Client x : Clients)
        {
            x.serialize();
        }
    }

    public void createApt()
    {
        tempEntry = table.getSelectionModel().getSelectedItem();
        if (tempEntry != null)
        {
            tempName = table.getSelectionModel().getSelectedItem().getName();
            nameLabel.setText(tempName);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a client before scheduling an appointment");
            alert.showAndWait();
        }
    }

    public void submitApt(ActionEvent event) throws IOException
    {
        try
        {
            if (tempEntry != null)
            {
                date = datePicker.getValue();
                time = timeText.getText().trim();
                for (int i = 0; i < Clients.size(); i++)
                {
                    if (Clients.get(i).getName().equals(tempName))
                    {
                        Clients.get(i).setDate(date);
                        Clients.get(i).setTime(time);
                        table.getItems().clear();
                        Clients.set(i, new Client(Clients.get(i).getName(),
                                Clients.get(i).getAge(), Clients.get(i).getHeight(),
                                Clients.get(i).getWeight(), Clients.get(i).getNumber(),
                                Clients.get(i).getEmail(), Clients.get(i).getDesc(), date, time));
                        timeText.setText(null);
                        datePicker.setValue(null);
                        nameLabel.setText("Select Client on Chart");
                    }
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a client before scheduling an appointment");
                alert.showAndWait();
            }
            table.getItems().clear();
            table.getItems().addAll(Clients);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        Client.clear();
        for (Client x : Clients)
        {
            x.serialize();
        }
    }

    LocalDate today = LocalDate.now();
    LocalDate yesterday = today.minusDays(1);
    LocalDate endDate = today.plusDays(8);


    public void noFilter(ActionEvent event)
    {
        table.getItems().clear();
        table.getItems().addAll(Clients);
    }

    public void filterToday(ActionEvent event)
    {
        ArrayList<Client> tempClients = new ArrayList<>();
        for (Client x : Clients)
        {
            if (x.getDate().equals(today))
            {
                tempClients.add(x);
            }
            table.getItems().clear();
            table.getItems().addAll(tempClients);
        }
    }

    public void filterWeek(ActionEvent event)
    {
        ArrayList<Client> tempClients = new ArrayList<>();
        for (Client x : Clients) {
            if (x.getDate().isAfter(yesterday) && x.getDate().isBefore(endDate))
            {
                tempClients.add(x);
            }
            table.getItems().clear();
            table.getItems().addAll(tempClients);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        nameColumn.setCellValueFactory((new PropertyValueFactory<Client, String>("name")));
        ageColumn.setCellValueFactory((new PropertyValueFactory<Client, Integer>("age")));
        heightColumn.setCellValueFactory((new PropertyValueFactory<Client, String>("height")));
        weightColumn.setCellValueFactory((new PropertyValueFactory<Client, Double>("weight")));
        numberColumn.setCellValueFactory((new PropertyValueFactory<Client, String>("number")));
        emailColumn.setCellValueFactory((new PropertyValueFactory<Client, String>("email")));
        descColumn.setCellValueFactory((new PropertyValueFactory<Client, String>("desc")));
        aptColumn.setCellValueFactory((new PropertyValueFactory<Client, LocalDate>("date")));
        timeColumn.setCellValueFactory((new PropertyValueFactory<Client, String>("time")));

        Client.deserialize();
        table.getItems().addAll(Clients);
    }
}