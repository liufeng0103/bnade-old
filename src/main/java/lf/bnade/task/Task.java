package lf.bnade.task;

import java.sql.SQLException;
import java.util.List;

public interface Task {
	
	void process(String realm) throws SQLException, Exception;
	
	void process(List<String> realms) throws SQLException, Exception;
	
	int getType();

	String getName();
	
	String getDescription();
}
