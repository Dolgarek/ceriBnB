package com.ceri.ceribnb;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class GraphicalCalendar extends BorderPane {
    private Calendar currentMonth;

    public GraphicalCalendar() {
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);

        drawCalendar();/*from ww  w. j  ava2s  . c  o  m*/
    }

    private void drawCalendar() {
        drawHeader();
        drawBody();
        drawFooter();
    }

    private void drawHeader() {
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
        Text tHeader = new Text(monthString + ", " + yearString);

        setTop(tHeader);
        setAlignment(tHeader, Pos.CENTER);
        setMargin(tHeader, new Insets(15));
    }

    private void drawBody() {
        GridPane gpBody = new GridPane();
        gpBody.setHgap(10);
        gpBody.setVgap(10);
        gpBody.setAlignment(Pos.CENTER);
        gpBody.setMinHeight(300);

        // Draw days of the week
        for (int day = 1; day <= 7; day++) {
            Text tDayName = new Text(getDayName(day));
            gpBody.add(tDayName, day - 1, 0);
        }

        // Draw days in month
        int currentDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            Text tDate = new Text(String.valueOf(currentDay));
            gpBody.add(tDate, dayOfWeek - 1, row);
            currentDay++;
            dayOfWeek++;
        }

        // Draw previous month days
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (currentDay != 1) {
            Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {
                Text tDate = new Text(String.valueOf(daysInPrevMonth));
                tDate.setFill(Color.GRAY);
                gpBody.add(tDate, i, 1);
                daysInPrevMonth--;
            }
        }

        // Draw next month days
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 7) {
            int day = 1;
            for (int i = dayOfWeek; i < 7; i++) {
                Text tDate = new Text(String.valueOf(day));
                tDate.setFill(Color.GRAY);
                gpBody.add(tDate, i, row);
                day++;
            }
        }

        setCenter(gpBody);
        setMargin(gpBody, new Insets(30));
    }

    private void drawFooter() {
        Button btPrev = new Button("Prev");
        Button btNext = new Button("Next");

        btPrev.setOnAction(e -> previous());
        btNext.setOnAction(e -> next());

        HBox hbFooter = new HBox(10);
        hbFooter.getChildren().addAll(btPrev, btNext);
        hbFooter.setAlignment(Pos.CENTER);

        setBottom(hbFooter);
        setMargin(hbFooter, new Insets(15));
    }

    private void previous() {
        getChildren().clear();
        currentMonth = getPreviousMonth(currentMonth);
        drawCalendar();
    }

    private void next() {
        getChildren().clear();
        currentMonth = getNextMonth(currentMonth);
        drawCalendar();
    }

    private GregorianCalendar getPreviousMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 0 ? 11 : cMonth - 1;
        int pYear = cMonth == 0 ? cal.get(Calendar.YEAR) - 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private GregorianCalendar getNextMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 11 ? 0 : cMonth + 1;
        int pYear = cMonth == 11 ? cal.get(Calendar.YEAR) + 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private String getDayName(int n) {
        StringBuilder sb = new StringBuilder();
        switch (n) {
            case 1:
                sb.append("Sunday");
                break;
            case 2:
                sb.append("Monday");
                break;
            case 3:
                sb.append("Tuesday");
                break;
            case 4:
                sb.append("Wednesday");
                break;
            case 5:
                sb.append("Thursday");
                break;
            case 6:
                sb.append("Friday");
                break;
            case 7:
                sb.append("Saturday");
        }
        return sb.toString();
    }

    private String getMonthName(int n) {
        String[] monthNames = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        return monthNames[n];
    }
}

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GraphicalCalendar gc = new GraphicalCalendar();

        Scene scene = new Scene(gc);
        primaryStage.setTitle("java2s.com");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}