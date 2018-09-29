package generalinfo.powellmakerspace.org.makerspacelogin.utils.statistic;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

//        List<String> types = new ArrayList<String>();
//        List<Integer> results = new ArrayList<Integer>();
//
//        try(Cursor c = db.rawQuery(sqlQuery, null)){
//
//
//            while (c.moveToNext()){
//                 types.add(c.getString(0));
//                 results.add(c.getInt(1));
//            }
//
//        }
        int result;
        try(Cursor c = db.rawQuery(sqlQuery, null)){
            c.moveToNext();
            result = c.getInt(0);
        }

        return new TimeStatisticResult(fieldName, result);
    }
}
