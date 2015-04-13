package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//import HibernateProject3.Log2;
//import HibernateProject3.Query;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	// Create controls.

	// The Statement for processing queries.
	private static SessionFactory factory;
	private Statement stmt;
	public static Label databaseConnectedlb = new Label("Database not Connected");

	// Override the start method in the Application class.
	public void start(Stage primaryStage) {
		VBox vBox = new VBox(5);

		HBox hBox1 = new HBox(5);
		// Add first row of fields to window.
		Label idlb = new Label("ID");
		TextField idtf = new TextField();
		hBox1.getChildren().addAll(idlb, idtf);

		HBox hBox2 = new HBox(5);
		// Add second row of fields to window.
		// Set the size of these fields.
		Label lastNamelb = new Label("Last Name");
		TextField lastNametf = new TextField();
		Label firstNamelb = new Label("Last Name");
		TextField firstNametf = new TextField();
		Label milb = new Label("MI");
		TextField tfmi = new TextField();
		hBox2.getChildren().addAll(lastNamelb, lastNametf, firstNamelb,
				firstNametf, milb, tfmi);

		HBox hBox3 = new HBox(5);
		// Add third row of fields to window.
		Label addresslb = new Label("Address");
		TextField addresstf = new TextField();
		hBox3.getChildren().addAll(addresslb, addresstf);

		HBox hBox4 = new HBox(5);
		// Add fourth row of fields to window.
		Label citylb = new Label("City");
		TextField citytf = new TextField();
		Label statelb = new Label("State");
		TextField statetf = new TextField();
		hBox4.getChildren().addAll(citylb, citytf, statelb, statetf);

		HBox hBox5 = new HBox(5);
		// Add fifth row of fields to window.
		Label telephonelb = new Label("Telephone");
		TextField telephonetf = new TextField();
		Label emaillb = new Label("email");
		TextField emailtf = new TextField();
		hBox5.getChildren().addAll(telephonelb, telephonetf, emaillb, emailtf);

		// Add horizontal boxes to vertical box.
		vBox.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5);

		HBox hBox = new HBox(5);
		// Add buttons.
		// Set the buttons to be centered.
		Button button1 = new Button("View");
		Button button2 = new Button("Insert");
		Button button3 = new Button("Update");
		Button button4 = new Button("Clear");
		Button button5 = new Button("Delete");
		hBox.getChildren().addAll(button1, button2, button3, button4, button5);

		// Add vertical box to center.
		// Add status to the top.
		// Add buttons to the bottom.

		BorderPane pane = new BorderPane(vBox, databaseConnectedlb, null, hBox,
				null);

		hBox.setAlignment(Pos.CENTER);

		// Create a scene and place it in the stage.
		Scene scene = new Scene(pane, 600, 600);

		// Set the stage title.
		// Stage primaryStage = new Stage();
		primaryStage.setTitle("Staff Data");

		// Place the scene in the stage.
		primaryStage.setScene(scene);

		// Display the stage.
		primaryStage.show();

		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		databaseConnectedlb.setText("Database Connected");

		// Set the actions for the buttons.
		button1.setOnAction((ActionEvent e) -> {
			view(idtf, lastNametf, firstNametf, tfmi, addresstf, citytf,
					statetf, telephonetf, emailtf);
		});
		button2.setOnAction((ActionEvent e) -> {
			insert(idtf, lastNametf, firstNametf, tfmi, addresstf, citytf,
					statetf, telephonetf, emailtf);
		});
		button3.setOnAction((ActionEvent e) -> {
			update(idtf, lastNametf, firstNametf, tfmi, addresstf, citytf,
					statetf, telephonetf, emailtf);
		});
		button4.setOnAction((ActionEvent e) -> {
			clear(idtf, lastNametf, firstNametf, tfmi, addresstf, citytf,
					statetf, telephonetf, emailtf);
		});
		button5.setOnAction((ActionEvent e) -> {
			delete(idtf, lastNametf, firstNametf, tfmi, addresstf, citytf,
					statetf, telephonetf, emailtf);
		});

	}// end start

	private void delete(TextField tfid, TextField tflastName,
			TextField tffirstName, TextField tfmi, TextField tfaddress,
			TextField tfcity, TextField tfstate, TextField tftelephone,
			TextField tfemail) {

		String tfid2 = tfid.getText();
		String tflastName2 = tflastName.getText();
		String tffirstName2 = tffirstName.getText();
		String tfmi2 = tfmi.getText();
		String tfaddress2 = tfaddress.getText();
		String tfcity2 = tfcity.getText();
		String tfstate2 = tfstate.getText();
		String tftelephone2 = tftelephone.getText();
		String tfemail2 = tfemail.getText();

		Person p = new Person(tfid2, tflastName2, tffirstName2, tfmi2,
				tfaddress2, tfcity2, tfstate2, tftelephone2, tfemail2);

		try {
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.delete(p);
			tx.commit();
			databaseConnectedlb.setText("Person with id = " + tfid2
					+ " has been deleted");
		} catch (HibernateException e) {
			databaseConnectedlb.setText("Person does not exist");
		}
	}// end delete

	/** View record by ID **/
	private void view(TextField tfid, TextField tflastName,
			TextField tffirstName, TextField tfmi, TextField tfaddress,
			TextField tfcity, TextField tfstate, TextField tftelephone,
			TextField tfemail) {
		// Build a SQL SELECT statement.
		// String query = "select * from Staff where id = " + tfid.getText();

		String tfid2 = tfid.getText();

		try {
			Session session = factory.openSession();
			Person p = (Person) session.get(Person.class, tfid2);
			if (p == null) {
				databaseConnectedlb.setText("Person not in database");
			}
			loadToTextField(tfid, tflastName, tffirstName, tfmi, tfaddress,
					tfcity, tfstate, tftelephone, tfemail);
		} catch (HibernateException e) {
			databaseConnectedlb.setText("Person does not exist");
		}

	}// end view

	/** Load the record into text fields **/
	private void loadToTextField(TextField tfid, TextField tflastName,
			TextField tffirstName, TextField tfmi, TextField tfaddress,
			TextField tfcity, TextField tfstate, TextField tftelephone,
			TextField tfemail) {

		String tfid2 = tfid.getText();
		String tflastName2 = tflastName.getText();
		String tffirstName2 = tffirstName.getText();
		String tfmi2 = tfmi.getText();
		String tfaddress2 = tfaddress.getText();
		String tfcity2 = tfcity.getText();
		String tfstate2 = tfstate.getText();
		String tftelephone2 = tftelephone.getText();
		String tfemail2 = tfemail.getText();

		Session session = factory.openSession();
		Person p = (Person) session.get(Person.class, tfid2);

		if (p != null) {
			tfid.setText(p.getId());
			tflastName.setText(p.getLastName());
			tffirstName.setText(p.getFirstName());
			tfmi.setText(p.getMi());
			tfaddress.setText(p.getAddress());
			tfcity.setText(p.getCities());
			tfstate.setText(p.getState());
			tftelephone.setText(p.getTelephone());
			tfemail.setText(p.getEmail());
		}

	}// end loadToTextField

	/** Insert a new record **/
	private void insert(TextField tfid, TextField tflastName,
			TextField tffirstName, TextField tfmi, TextField tfaddress,
			TextField tfcity, TextField tfstate, TextField tftelephone,
			TextField tfemail) {

		String tfid2 = tfid.getText();
		String tflastName2 = tflastName.getText();
		String tffirstName2 = tffirstName.getText();
		String tfmi2 = tfmi.getText();
		String tfaddress2 = tfaddress.getText();
		String tfcity2 = tfcity.getText();
		String tfstate2 = tfstate.getText();
		String tftelephone2 = tftelephone.getText();
		String tfemail2 = tfemail.getText();

		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Person p = (Person) session.get(Person.class, tfid2);

		if (p != null) {
			databaseConnectedlb
					.setText("Person already exists.  Try using update");
		}

		else {

			try {
				p = new Person(tfid2, tflastName2, tffirstName2, tfmi2,
						tfaddress2, tfcity2, tfstate2, tftelephone2, tfemail2);

				session.save(p);
				tx.commit();
				databaseConnectedlb.setText("Insert Complete");
			} catch (HibernateException e) {
			}
		}

	}// end insert

	/** Update a record **/

	public void update(TextField tfid, TextField tflastName,
			TextField tffirstName, TextField tfmi, TextField tfaddress,
			TextField tfcity, TextField tfstate, TextField tftelephone,
			TextField tfemail) {
		// Build a SQL UPDATE statement
		String tfid2 = tfid.getText();
		String tflastName2 = tflastName.getText();
		String tffirstName2 = tffirstName.getText();
		String tfmi2 = tfmi.getText();
		String tfaddress2 = tfaddress.getText();
		String tfcity2 = tfcity.getText();
		String tfstate2 = tfstate.getText();
		String tftelephone2 = tftelephone.getText();
		String tfemail2 = tfemail.getText();

		Session session = factory.openSession();
		Person p = (Person) session.get(Person.class, tfid2);

		if (p != null) {

			if (!tfid2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setId(tfid2);
				session.update(person);
				tx.commit();
			}

			if (!tflastName2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setLastName(tflastName2);
				session.update(person);
				tx.commit();
			}

			if (!tffirstName2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setFirstName(tffirstName2);
				session.update(person);
				tx.commit();
			}

			if (!tfmi2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setMi(tfmi2);
				session.update(person);
				tx.commit();
			}

			if (!tfaddress2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setAddress(tfaddress2);
				session.update(person);
				tx.commit();
			}

			if (!tfcity2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setCities(tfcity2);
				session.update(person);
				tx.commit();
			}

			if (!tfstate2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setState(tfstate2);
				session.update(person);
				tx.commit();
			}

			if (!tftelephone2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setTelephone(tftelephone2);
				session.update(person);
				tx.commit();
			}

			if (!tfemail2.isEmpty()) {
				Transaction tx = session.beginTransaction();
				Person person = (Person) session.get(Person.class, tfid2);
				person.setEmail(tfemail2);
				session.update(person);
				tx.commit();
			}

			databaseConnectedlb.setText("Update complete");
		} else {
			databaseConnectedlb
					.setText("Person does not exist.  Try hitting 'insert' button");
		}
	}// end update

	/** Clear text fields **/
	private void clear(TextField tfid, TextField tflastName,
			TextField tffirstName, TextField tfmi, TextField tfaddress,
			TextField tfcity, TextField tfstate, TextField tftelephone,
			TextField tfemail) {

		tfid.setText(null);
		tflastName.setText(null);
		tffirstName.setText(null);
		tfmi.setText(null);
		tfaddress.setText(null);
		tfcity.setText(null);
		tfstate.setText(null);
		;
		tftelephone.setText(null);
		tfemail.setText(null);
	}// end clear

	/**
	 * The main method is only needed for the IDE with limited javaFX support.
	 * Not needed for running from the command line.
	 */

	public static void main(String[] args) {
		launch(args);

	}// end main
}// end Main
