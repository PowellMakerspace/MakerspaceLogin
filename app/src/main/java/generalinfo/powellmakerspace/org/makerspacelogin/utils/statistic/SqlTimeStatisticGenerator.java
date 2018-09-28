package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlTimeStatisticGenerator implements StatisticGenerator {

    private SQLiteDatabase db;
    private String sqlQuery;
    private String fieldName;

    public SqlTimeStatisticGenerator(String fieldName, String sqlQuery, SQLiteDatabase db){
        this.fieldName = fieldName;
        this.sqlQuery = sqlQuery;
        this.db = db;
    }

    @Override
    public StatisticResult generateStatistic(){

        long result;

        try(Cursor c = db.rawQuery(sqlQuery, null)){
            c.moveToNext();
            result = c.getLong(0);
        }

        return new TimeStatisticResult(fieldName, result);
    }
}
