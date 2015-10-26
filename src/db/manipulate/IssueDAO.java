package db.manipulate;


import model.Issue;

import java.sql.Connection;
import java.util.List;

public class IssueDAO {
    public static boolean create(Issue newIssue, Connection connect){
        return true;
    }

    public static boolean update(Issue toUpdate, Connection connect){
        return true;
    }

    public static List<Issue> search(int start, int limit, Connection connection){
        return null;
    }
}
