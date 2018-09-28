package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlIntegerStatisticGenerator implements StatisticGenerator {

    private SQLiteDatabase db;
    private String sqlQuery;
    private String fieldName;

    public SqlIntegerStatisticGenerator(String fieldName, String sqlQuery, SQLiteDatabase db){
        this.fieldName = fieldName;
        this.sqlQuery = sqlQuery;
        this.db = db;
    }

    @Override
    public StatisticResult generateStatistic(){

        int result;
        try(Cursor c = db.rawQuery(sqlQuery,null)){
            c.moveToNext();
            result = c.getInt(0);
        }

        return new IntegerStatisticResult(fieldName, result);
    }
}
