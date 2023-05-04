package com.ceri.ceribnb;

import com.ceri.ceribnb.entity.Sejour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchTask {

  @FXML
  private TextField searchField;
  @FXML
  private ListView<Sejour> sejourListView;
  private final ObservableList<Sejour> allResults = FXCollections.observableArrayList();
  private final FilteredList<Sejour> filteredResults = new FilteredList<>(allResults);
  private final StringProperty searchQuery = new SimpleStringProperty("");

  public void init(ListView<Sejour> sejourListView) {

  }

}
