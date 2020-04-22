package org.budowa.flow.shared;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.budowa.entities.Building;
import org.budowa.entities.BuildingStatus;
import org.budowa.flow.kanban.KanbanController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

public abstract class DashboardBaseController implements Initializable {

    @FXML
    public KanbanController kanbanController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setBuildings(this.loadBuildings());
    }

    protected abstract Collection<Building> loadBuildings();

    public void setBuildings(Collection<Building> buildings){
        this.kanbanController.toDoList.getItems().setAll(this.getFilteredLabels(buildings, BuildingStatus.TODO));
        this.kanbanController.foundationList.getItems().setAll(this.getFilteredLabels(buildings, BuildingStatus.FOUNDATIONS));
        this.kanbanController.wallsList.getItems().setAll(this.getFilteredLabels(buildings, BuildingStatus.WALLS));
        this.kanbanController.ceilingList.getItems().setAll(this.getFilteredLabels(buildings, BuildingStatus.CEILING));
        this.kanbanController.roofList.getItems().setAll(this.getFilteredLabels(buildings, BuildingStatus.ROOF));
        this.kanbanController.doneList.getItems().setAll(this.getFilteredLabels(buildings, BuildingStatus.DONE));
    }

    private Label[] getFilteredLabels(Collection<Building> buildings, BuildingStatus status) {
        var filteredBuildings = new ArrayList<Label>();
        for (var building : buildings) {
            if(building.getStatus() != status) {
                continue;
            }
            var label = getBuildingLabel(building);
            filteredBuildings.add(label);
        }
        return filteredBuildings.toArray(Label[]::new);
    }

    private Label getBuildingLabel(Building building) {
        var label = new Label(building.getName());
        label.setCursor(Cursor.HAND);
        label.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pokaż detailsy", ButtonType.CLOSE);
            alert.showAndWait();
        });
        return label;
    }
}