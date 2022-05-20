package buttonStrategy;

import javafx.collections.ObservableList;

public abstract class AbstractButtonOperation  {
	public abstract <E> void buttonAction(ObservableList<E> information);
}
