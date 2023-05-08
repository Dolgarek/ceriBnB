package com.ceri.ceribnb.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.ceri.ceribnb.entity.Sejour;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GraphicalCalendar extends BorderPane {
    private Calendar currentMonth;

    private Calendar initialMonth;

    private List<Sejour> hostSejour;

    private DateFormat sourceFormat;

    public GraphicalCalendar() throws ParseException {
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
        hostSejour = GlobalData.getInstance().getOwnSejour();
        sourceFormat = new SimpleDateFormat("dd/MM/yyyy");

        drawCalendar();/*from ww  w. j  ava2s  . c  o  m*/
    }

    private void drawCalendar() throws ParseException {
        drawHeader();
        drawBody();
        drawFooter();
    }

    private void drawHeader() {
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
        Text tHeader = new Text(monthString + ", " + yearString);
        tHeader.setStyle("-fx-font-size: 21;");

        setTop(tHeader);
        setAlignment(tHeader, Pos.CENTER);
        setMargin(tHeader, new Insets(15));
    }

    private void drawBody() throws ParseException {
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
            String days = String.valueOf(currentDay);
            String month = String.valueOf(currentMonth.get(Calendar.MONTH) + 1);
            if (days.length() == 1) {
                days = "0" + days;
            }
            if (month.length() == 1) {
                month = "0" + month;
            }
            //System.out.println(days + "/" + month + "/" + currentMonth.get(Calendar.YEAR));
            String finalDate = days + "/" + month + "/" + currentMonth.get(Calendar.YEAR);
            Date checkDate = sourceFormat.parse(finalDate);
            for(Sejour s : hostSejour) {
                Date before = sourceFormat.parse(s.getDateDebut());
                Date after = sourceFormat.parse(s.getDateFin());
                System.out.println("Sejour (" + s.getId() + "): " + s.getStatus());

                if (before.before(checkDate) && after.after(checkDate) && s.getStatus() != null) {
                    if (s.getStatus().equals("VALIDE")) {
                        tDate.setFill(Color.RED);
                    }
                }
            }

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
        Button btPrev = new Button("Précédent");
        Button reset = new Button("Reset");
        Button btNext = new Button("Suivant");

        btPrev.setOnAction(e -> {
            try {
                previous();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        reset.setOnAction(e -> {
            try {
                reset();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        btNext.setOnAction(e -> {
            try {
                next();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox hbFooter = new HBox(10);
        hbFooter.getChildren().addAll(btPrev, reset, btNext);
        hbFooter.setAlignment(Pos.CENTER);

        setBottom(hbFooter);
        setMargin(hbFooter, new Insets(15));
    }

    private void previous() throws ParseException {
        getChildren().clear();
        currentMonth = getPreviousMonth(currentMonth);
        drawCalendar();
    }

    private void next() throws ParseException {
        getChildren().clear();
        currentMonth = getNextMonth(currentMonth);
        drawCalendar();
    }

    private void reset() throws ParseException {
        getChildren().clear();
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
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
                sb.append("Dimanche");
                break;
            case 2:
                sb.append("Lundi");
                break;
            case 3:
                sb.append("Mardi");
                break;
            case 4:
                sb.append("Mercredi");
                break;
            case 5:
                sb.append("Jeudi");
                break;
            case 6:
                sb.append("Vendredi");
                break;
            case 7:
                sb.append("Samedi");
        }
        return sb.toString();
    }

    private String getMonthName(int n) {
        String[] monthNames = { "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre",
                "Octobre", "Novembre", "Decembre" };
        return monthNames[n];
    }
}

