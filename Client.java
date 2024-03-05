package pack;

import javafx.util.converter.LocalDateStringConverter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public String name;
    public int age;
    public String height;
    public double weight;
    public String number;
    public String email;
    public String desc;
    public LocalDate date;
    public String time;

    public Client(String name, int age, String height, double weight, String number, String email, String desc, LocalDate date, String time)
    {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.number = number;
        this.email = email;
        this.desc = desc;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return name + ", " + age + ", " + height + ", " + weight + ", " + number + ", " + email + ", " + desc + ", " + date + ", " + time;
    }

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getDesc() {
        return desc;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String to_csv()
    {
        return String.format("%s,%d,%s,%f,%s,%s,%s,%s,%s", this.name, this.age, this.height, this.weight, this.number, this.email, this.desc, this.date.toString(), this.time);
    }

    public void serialize() throws IOException
    {
        FileWriter file = new FileWriter("Clients.txt", true);

        file.write(this.to_csv() + "\n");

        file.close();
    }

    public static void  clear() throws IOException
    {
        new FileWriter("Clients.txt", false).close();
    }

    public static void deserialize()
    {
        try {
            FileReader file = new FileReader("Clients.txt");
            Scanner file_iter = new Scanner(file);


            while (file_iter.hasNextLine()) {
                String line = file_iter.nextLine();

                if (line.isEmpty())
                    break;

                String[] fields = line.split(",");

                LocalDate date = LocalDate.parse(fields[7]);

                Controller.Clients.add(new Client(fields[0],
                        Integer.parseInt(fields[1]),
                        fields[2],
                        Double.parseDouble(fields[3]),
                        fields[4],
                        fields[5],
                        fields[6],
                        date,
                        fields[8]));
            }
            file_iter.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}