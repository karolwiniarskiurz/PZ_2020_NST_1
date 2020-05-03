package org.budowa.flow.users;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.budowa.entities.User;
import org.budowa.entities.UserRole;
import org.budowa.router.Route;
import org.budowa.router.Router;
import org.budowa.services.DialogService;
import org.budowa.services.UsersService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUserScene implements Initializable {
	private final Router router = Router.inject();
	private final DialogService dialogService = DialogService.inject();
	private final UsersService usersService = UsersService.inject();
	private User user = new User();

	@FXML
	private TextField textFieldName;

	@FXML
	private TextField textFieldLogin;

	@FXML
	private TextField textFieldPassword;

	@FXML
	private ChoiceBox<UserRole> choiceBoxRole;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceBoxRole.getItems().setAll(UserRole.values());
	}

	@FXML
	private void controlAction() {
		removeErrorFrom(textFieldName);
		removeErrorFrom(textFieldLogin);
		removeErrorFrom(textFieldPassword);
		removeErrorFrom(choiceBoxRole);
	}

	@FXML
	private void addUserButtonAction () {
		try {
			if (isValid()) {
				user.setFullName(textFieldName.getText());
				user.setUsername(textFieldLogin.getText());
				user.setPassword(textFieldPassword.getText());
				user.setUserRole(choiceBoxRole.getSelectionModel().getSelectedItem());

				usersService.create(user);
				dialogService.showInfoDialog("Udało się stworzyć użytkownika!");
			}
		} catch (Exception e) {
			dialogService.showErrorDialog("Coś poszło nie tak");
		}
	}

	private boolean isValid() {
		boolean isValid = true;
		if (isTextValid(textFieldName.getText())) {
			setErrorFor(textFieldName);
			isValid = false;
		}

		if (isTextValid(textFieldLogin.getText())) {
			setErrorFor(textFieldLogin);
			isValid = false;
		}

		if (isTextValid(textFieldPassword.getText())) {
			setErrorFor(textFieldPassword);
			isValid = false;
		}

		if (choiceBoxRole.getValue() == null) {
			setErrorFor(choiceBoxRole);
			isValid = false;
		}

		return isValid;
	}

	private boolean isTextValid(String text) {
		return text == null || text.isEmpty();
	}

	private void setErrorFor(Control control) {
		control.setStyle("" +
				"-fx-border-color: red; " +
				"-fx-border-width: 1px; " +
				"-fx-border-radius: 2px; "
		);
	}

	@FXML
	private void backButtonAction () {
		try {
			this.router.goTo(Route.DASHBOARD);
		} catch (IOException exception) {
			dialogService.showErrorDialog("Coś poszło nie tak");
		}
	}

	private void removeErrorFrom(Control control) {
		control.setStyle(null);
	}
}