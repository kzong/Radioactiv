package Rad;
//change2
import java.util.EventListener;

import javax.swing.event.TableModelEvent;

public interface TableModelListener extends EventListener {
    public void tableChanged(TableModelEvent e);
}
