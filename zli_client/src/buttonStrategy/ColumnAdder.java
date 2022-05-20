package buttonStrategy;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ColumnAdder {

	private static <E> void addButtonToTable(TableView<E> Table,String colName,String buttonName,AbstractButtonOperation bo,ObservableList<E> information) {
		TableColumn<E, Void> colBtn = new TableColumn<>(colName);

		Callback<TableColumn<E, Void>, TableCell<E, Void>> cellFactory = new Callback<TableColumn<E, Void>, TableCell<E, Void>>() {
			@Override
			public TableCell<E, Void> call(final TableColumn<E, Void> param) {
				final TableCell<E, Void> cell = new TableCell<E, Void>() {

					private Button btn = new Button(buttonName);
					{
						btn.setOnAction((ActionEvent event) -> {
							bo.buttonAction(information);
							
//							E data = getTableView().getItems().get(getIndex());
//							HashMap<String, Object> message = new HashMap<>();
//							message.put("command", Commands.CHANGE_USER_STATUS);
//							message.put("id","" );
//							Object response = ClientFormController.client.accept(message);
//							
//							if (response.equals("Suspended")&&data.getStatus().equals("Active")) {
//								information.get(information.indexOf(data)).setStatus("Suspended");
//							} else {
//								if(response.equals("Active"))
//									information.get(information.indexOf(data)).setStatus("Active");
//							}
							Table.refresh();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		colBtn.setCellFactory(cellFactory);
		Table.getColumns().add(colBtn);
	}
}
