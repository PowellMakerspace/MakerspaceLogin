package generalinfo.powellmakerspace.org.makerspacelogin.MainApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Member;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Survey;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Tour;
import generalinfo.powellmakerspace.org.makerspacelogin.Classes.Visit;

import static java.lang.String.format;

/**
 *  This class defines the creation of the database and interactions with it.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MakerspaceDatabase";

    // Table Names
    private static final String TABLE_MEMBERS = "members";
    private static final String TABLE_VISITS = "visits";
    private static final String TABLE_SURVEYS = "surveys";
    private static final String TABLE_TOURS = "tours";

    // Column Names -- Members
    public static final String KEY_MEMBER_ID = "member_id";
    public static final String KEY_MEMBER_NAME = "member_name";
    public static final String KEY_MEMBERSHIP_TYPE = "membership_type";
    public static final String KEY_PUNCH_PASSES = "punch_passes";

    // Column Names -- Visits
    public static final String KEY_VISIT_ID = "visit_id";
    //public static final String KEY_MEMBER_ID = "member_id"; --> just a reminder that this ID is used again in this table
    public static final String KEY_ARRIVAL_TIME = "arrival_time";
    public static final String KEY_DEPARTURE_TIME = "departure_time";
    public static final String KEY_VISIT_PURPOSE = "visit_purpose";

    // Column Names -- Surveys
    public static final String KEY_SURVEY_ID = "survey_id";
    public static final String KEY_LEARNED_ABOUT = "learned_about";

    // Column Names -- Tours
    public static final String KEY_TOUR_ID = "tour_id";
    public static final String KEY_TOUR_NAME = "tour_name";
    public static final String KEY_TOUR_VISITOR_NUMBER = "tour_visitor_number";
    public static final String KEY_TOUR_ARRIVAL_TIME = "tour_arrival";
    public static final String KEY_TOUR_DEPARTURE_TIME = "tour_departure";

    // Table Create Statements
    // Members table create statement
    private static final String CREATE_TABLE_MEMBERS = "CREATE TABLE " + TABLE_MEMBERS + "(" + KEY_MEMBER_ID +
            " INTEGER PRIMARY KEY," + KEY_MEMBER_NAME + " TEXT," + KEY_MEMBERSHIP_TYPE + " TEXT," +
            KEY_PUNCH_PASSES + " INTEGER" +  ")" ;

    // Visit table create statement
    public static final String CREATE_TABLE_VISITS = "CREATE TABLE " + TABLE_VISITS + "(" + KEY_VISIT_ID +
            " INTEGER PRIMARY KEY," + KEY_MEMBER_ID + " INTEGER," + KEY_ARRIVAL_TIME + " INTEGER," +
            KEY_DEPARTURE_TIME + " INTEGER," + KEY_VISIT_PURPOSE + " TEXT" + ")";

    // Survey table create statement
    public static final String CREATE_TABLE_SURVEYS = "CREATE TABLE " + TABLE_SURVEYS + "(" + KEY_SURVEY_ID +
            " INTEGER PRIMARY KEY," + KEY_LEARNED_ABOUT + " TEXT" + ")";

    // Tour table create statement
    public static final String CREATE_TABLE_TOUR = "CREATE TABLE " + TABLE_TOURS + "(" + KEY_TOUR_ID +
            " INTEGER PRIMARY KEY," + KEY_TOUR_NAME + " TEXT," + KEY_TOUR_VISITOR_NUMBER + " INTEGER," +
            KEY_TOUR_ARRIVAL_TIME + " INTEGER," + KEY_TOUR_DEPARTURE_TIME + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *  _________________________________________________________________________________________
     * Methods for initiating the database
     */


    /**
     * Creates the tables when the database is first made
     *
     * @param db Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create required tables
        db.execSQL(CREATE_TABLE_MEMBERS);
        db.execSQL(CREATE_TABLE_VISITS);
        db.execSQL(CREATE_TABLE_SURVEYS);
        db.execSQL(CREATE_TABLE_TOUR);
    }

    /**
     * Drops old tables and adds new when upgrading
     *
     * @param db         Database
     * @param oldVersion Previous version of the Database
     * @param newVersion New version of the Database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables -- why? Won't this loose data?
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);

        // Create new tables
        onCreate(db);
    }


    /**
     * __________________________________________________________________________________________
     * Methods for interacting with the Member Table of the Database
     */

    /**
     * Create a member in the database
     *
     * @param member Member object to be added to the database
     * @return Member ID number
     */
    public long createMember(Member member) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect information for database
        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_NAME, member.getMemberName());
        values.put(KEY_MEMBERSHIP_TYPE, member.getMembershipType());
        values.put(KEY_PUNCH_PASSES, member.getPunchPasses());


        //insert row
        //--> this appears to be defining the member id based on the row number - might want to change that.
        long member_id = db.insert(TABLE_MEMBERS, null, values);

        return member_id;
    }

    /**
     * Get member based on ID -> may be changed to search by name instead
     *
     * @param member_id Id number for the member being searched
     * @return member object with all of the relevant member information
     */
    public Member getMember(long member_id) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Define query string
        String selectQuery = "SELECT * FROM " + TABLE_MEMBERS + " WHERE " + KEY_MEMBER_ID + " = " + member_id;

        // Add query to database log
        Log.e(LOG, selectQuery);

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // As long as the cursor isn't null, move to the first
        if (c != null)
            c.moveToFirst();

        // Create member object from database
        Member member = new Member();
        member.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
        member.setMemberName(c.getString(c.getColumnIndex(KEY_MEMBER_NAME)));
        member.setMembershipType(c.getString(c.getColumnIndex(KEY_MEMBERSHIP_TYPE)));
        member.setPunchPasses(c.getInt(c.getColumnIndex(KEY_PUNCH_PASSES)));

        // Return member object
        return member;
    }

    /**
     * Get member based on name
     *
     * @param member_name String for the name of the member being searched
     * @return member object with all of the relevant member information
     * There seems to be a problem with this code... unknown why
     */
    public Member getMemberByName(String member_name) {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define query string
        String selectQuery = "SELECT * FROM " + TABLE_MEMBERS + " WHERE " + KEY_MEMBER_NAME + " = " + "'" + member_name + "'";

        // Add query to database log
        Log.e(LOG, selectQuery);

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // As long as the cursor isn't null, move to the first
        if (c != null)
            c.moveToFirst();

        // Create member object from database
        Member member = new Member();
        member.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
        member.setMemberName(c.getString(c.getColumnIndex(KEY_MEMBER_NAME)));
        member.setMembershipType(c.getString(c.getColumnIndex(KEY_MEMBERSHIP_TYPE)));
        member.setPunchPasses(c.getInt(c.getColumnIndex(KEY_PUNCH_PASSES)));

        // Return member object
        return member;
    }

    /**
     * Gets all of the member information
     *
     * @return List of member objects for each member in the database
     */
    public List<Member> getAllMembers() {

        // Define member list
        List<Member> members = new ArrayList<Member>();

        // Define query string
        String selectQuery = "SELECT * FROM " + TABLE_MEMBERS;

        // Add query to database log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all rows and add them to list
        if (c.moveToFirst()) {
            do {
                Member member = new Member();
                member.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
                member.setMemberName(c.getString(c.getColumnIndex(KEY_MEMBER_NAME)));
                member.setMembershipType(c.getString(c.getColumnIndex(KEY_MEMBERSHIP_TYPE)));
                member.setPunchPasses(c.getInt(c.getColumnIndex(KEY_PUNCH_PASSES)));

                // Add to member list
                members.add(member);
            } while (c.moveToNext());
        }

        //Return member list
        return members;
    }

    /**
     * Gets all members of a certain membership type
     *
     * @param membership_type membership type for search
     * @return List of member ojects for each member of given type
     */
    public List<Member> getAllMembersByType(String membership_type) {

        // Define member list
        List<Member> members = new ArrayList<>();

        // Define query string
        String selectQuery = "SELECT * FROM " + TABLE_MEMBERS + " WHERE " + KEY_MEMBERSHIP_TYPE + " = " + membership_type;

        // Add query to database log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all rows and add them to list
        if (c.moveToFirst()) {
            do {
                Member member = new Member();
                member.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
                member.setMemberName(c.getString(c.getColumnIndex(KEY_MEMBER_NAME)));
                member.setMembershipType(c.getString(c.getColumnIndex(KEY_MEMBERSHIP_TYPE)));
                member.setPunchPasses(c.getInt(c.getColumnIndex(KEY_PUNCH_PASSES)));

                // Add to member list
                members.add(member);
            } while (c.moveToNext());
        }

        //Return member list
        return members;
    }

    /**
     * Updates the database for a given member
     *
     * @param member Member object containing updated information
     * @return Integer indicating number of rows affected
     */
    public int updateMember(Member member) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect data
        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_ID, member.getMemberID());
        values.put(KEY_MEMBER_NAME, member.getMemberName());
        values.put(KEY_MEMBERSHIP_TYPE, member.getMembershipType());
        values.put(KEY_PUNCH_PASSES, member.getPunchPasses());

        // Define where arguments
        String[] whereArgument = {String.valueOf(member.getMemberID())};

        // update row
        return db.update(TABLE_MEMBERS, values, KEY_MEMBER_ID + " = ?", whereArgument);
    }


    /**
     * Deletes member information from the database.  This optionally includes the visits for that member
     *
     * @param member                   Object for the member to be deleted
     * @param should_delete_all_visits Boolean determining whether all member visits should be deleted as well
     */
    public void deleteMember(Member member, boolean should_delete_all_visits) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Before deleting member check whether all visits should be deleted too
        if (should_delete_all_visits) {
            // Get all visits under this member
            List<Visit> allMemberVisits = getAllVisitsByMember(member.getMemberID());

            // Delete all Visits
            for (Visit visit : allMemberVisits) {
                // Delete visit
                deleteVisit(visit);
            }
        }

        // Delete member from database
        db.delete(TABLE_MEMBERS, KEY_MEMBER_ID + " = ?",
                new String[]{String.valueOf(member.getMemberID())});
    }


    /**
     * __________________________________________________________________________________________
     * Methods for interacting with the Visit Table of the Database
     */


    /**
     * Creates visit record in the database
     *
     * @param visit Object containing the information about the visit
     * @return visit ID number
     */
    public long createVisit(Visit visit) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect Content to be put in database
        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_ID, visit.getMemberID());
        values.put(KEY_ARRIVAL_TIME, visit.getArrivalTime());
        values.put(KEY_DEPARTURE_TIME, visit.getDepartureTime());
        values.put(KEY_VISIT_PURPOSE, visit.getVisitPurpose());

        // Insert row
        long visit_id = db.insert(TABLE_VISITS, null, values);

        // Return visit ID
        return visit_id;
    }

    public Visit getVisit(long visit_id) {

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define query string
        String selectQuery = "SELECT * FROM " + TABLE_VISITS + " WHERE " + KEY_VISIT_ID + " = " + visit_id;

        // Add query to database log
        Log.e(LOG, selectQuery);

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // As long as the cursor isn't null, move to the first
        if (c != null)
            c.moveToFirst();

        // Create member object from database
        Visit visit = new Visit();
        visit.setVisitID(c.getLong(c.getColumnIndex(KEY_VISIT_ID)));
        visit.setMemberID(c.getLong(c.getColumnIndex(KEY_MEMBER_ID)));
        visit.setArrivalTime(c.getInt(c.getColumnIndex(KEY_ARRIVAL_TIME)));
        visit.setDepartureTime(c.getInt(c.getColumnIndex(KEY_DEPARTURE_TIME)));
        visit.setVisitPurpose(c.getString(c.getColumnIndex(KEY_VISIT_PURPOSE)));

        // Cursor Close
        c.close();

        // Return member object
        return visit;
    }

    /**
     * Updates visit record in the database
     *
     * @param visit Object containing the information about the visit
     * @return number of rows affected
     */
    public int updateVisit(Visit visit) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect Content
        ContentValues values = new ContentValues();
        values.put(KEY_MEMBER_ID, visit.getMemberID());
        values.put(KEY_ARRIVAL_TIME, visit.getArrivalTime());
        values.put(KEY_DEPARTURE_TIME, visit.getDepartureTime());
        values.put(KEY_VISIT_PURPOSE, visit.getVisitPurpose());

        // Update row
        return db.update(TABLE_VISITS, values, KEY_VISIT_ID + " = ?",
                new String[]{String.valueOf(visit.getVisitID())});
    }

    /**
     * Deletes an individual visit from the database
     *
     * @param visit Object containing the visit to be deleted.
     */
    public void deleteVisit(Visit visit) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete Visit
        db.delete(TABLE_VISITS, KEY_VISIT_ID + " = ?",
                new String[]{String.valueOf(visit.getVisitID())});
    }

    /**
     * Get all visit records based on a member ID that corresponds to it
     *
     * @param memberID Member id to be searched
     * @return List of Visit Objects corresponding to that id
     */
    public List<Visit> getAllVisitsByMember(long memberID) {

        // Create visit list
        List<Visit> visits = new ArrayList<>();

        // Define query
        String selectQuery = "SELECT * FROM " + TABLE_VISITS + " WHERE " + KEY_MEMBER_ID + " = " + memberID;

        // Add query to Database log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Visit visit = new Visit();
                visit.setVisitID(c.getInt(c.getColumnIndex(KEY_VISIT_ID)));
                visit.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
                visit.setArrivalTime(c.getInt(c.getColumnIndex(KEY_ARRIVAL_TIME)));
                visit.setDepartureTime(c.getInt(c.getColumnIndex(KEY_DEPARTURE_TIME)));
                visit.setVisitPurpose(c.getString(c.getColumnIndex(KEY_VISIT_PURPOSE)));

                // Add to the visit list
                visits.add(visit);
            } while (c.moveToNext());
        }

        // Cursor Close
        c.close();

        // Return list of visits
        return visits;
    }

    /**
     * Method for retrieving active visits from the database
     *
     * @return List of active visit records
     */
    public List<Visit> getActiveVisits() {

        // Create visit list
        List<Visit> visits = new ArrayList<>();

        // Define query
        String selectQuery = "SELECT * FROM " + TABLE_VISITS + " WHERE " + KEY_DEPARTURE_TIME + " = 0";

        // Add query to Database log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Visit visit = new Visit();
                visit.setVisitID(c.getInt(c.getColumnIndex(KEY_VISIT_ID)));
                visit.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
                visit.setArrivalTime(c.getLong(c.getColumnIndex(KEY_ARRIVAL_TIME)));
                visit.setDepartureTime(c.getLong(c.getColumnIndex(KEY_DEPARTURE_TIME)));
                visit.setVisitPurpose(c.getString(c.getColumnIndex(KEY_VISIT_PURPOSE)));

                // Add to the visit list
                visits.add(visit);
            } while (c.moveToNext());
        }

        // Close cursor
        c.close();

        // Return list of visits
        return visits;
    }

    /**
     * Return all visits with a given time frame
     *
     * @param startDateTime long unix timestamp for beginning of time frame
     * @param endDateTime   long unix timestamp for ending of time frame
     * @return list of all visit objects within time frame
     */
    public List<Visit> getVisitsFromRange(long startDateTime, long endDateTime) {

        // Create visit list
        List<Visit> visits = new ArrayList<>();

        // Define query
        String selectQuery = "SELECT * FROM " + TABLE_VISITS + " WHERE " + KEY_ARRIVAL_TIME + " > " + startDateTime
                + " AND " + KEY_DEPARTURE_TIME + " < " + endDateTime;

        // Add query to Database Log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (c.moveToFirst()) {
            do {
                Visit visit = new Visit();
                visit.setVisitID(c.getInt(c.getColumnIndex(KEY_VISIT_ID)));
                visit.setMemberID(c.getInt(c.getColumnIndex(KEY_MEMBER_ID)));
                visit.setArrivalTime(c.getLong(c.getColumnIndex(KEY_ARRIVAL_TIME)));
                visit.setDepartureTime(c.getLong(c.getColumnIndex(KEY_DEPARTURE_TIME)));
                visit.setVisitPurpose(c.getString(c.getColumnIndex(KEY_VISIT_PURPOSE)));

                // Add to the visit list
                visits.add(visit);
            } while (c.moveToNext());
        }

        // Close cursor
        c.close();

        // Return list of visits
        return visits;
    }

    /**
     * _______________________________________________________________________________________
     * Methods for interacting with the Survey Table of the Database
     */


    /**
     * Creates the record of the survey results
     *
     * @param survey Object containing the survey results
     * @return Survey id number
     */
    public long createSurvey(Survey survey) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect Content
        ContentValues values = new ContentValues();
        values.put(KEY_LEARNED_ABOUT, survey.getLearnedAbout());

        // Insert Row
        long survey_id = db.insert(TABLE_SURVEYS, null, values);

        // Return survey_id
        return survey_id;
    }

    /**
     * Updates the record of the survey results
     *
     * @param survey Object containing survey updated results
     * @return Number of rows affected
     */
    public int updateSurvey(Survey survey) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect Content
        ContentValues values = new ContentValues();
        values.put(KEY_SURVEY_ID, survey.getSurveyID());
        values.put(KEY_LEARNED_ABOUT, survey.getLearnedAbout());

        // Update Row
        return db.update(TABLE_SURVEYS, values, KEY_SURVEY_ID + " = ?",
                new String[]{String.valueOf(survey.getSurveyID())});
    }

    /**
     * Delete Survey Record
     *
     * @param survey Object containing survey information
     */
    public void deleteSurvey(Survey survey) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //Delete Survey
        db.delete(TABLE_SURVEYS, KEY_SURVEY_ID + " = ?",
                new String[]{String.valueOf(survey.getSurveyID())});
    }

    /**
     * ______________________________________________________________________________________
     * Methods for interacting with the Tours table of the database
     */

    /**
     * Create a new tour record in the database
     *
     * @param tour object to be recorded in the database
     * @return the ID number of the tour record
     */
    public long createTour(Tour tour) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect Content
        ContentValues values = new ContentValues();
        values.put(KEY_TOUR_NAME, tour.getTour_name());
        values.put(KEY_TOUR_VISITOR_NUMBER, tour.getTour_visitor_number());
        values.put(KEY_TOUR_ARRIVAL_TIME, tour.getTour_arrival_time());
        values.put(KEY_TOUR_DEPARTURE_TIME, tour.getTour_departure_time());

        // Insert row
        long tour_id = db.insert(TABLE_TOURS, null, values);

        // Return visit ID
        return tour_id;
    }

    /**
     * Pulls the tour record from the database based on ID
     *
     * @param tour_id ID number of the tour to query the database
     * @return tour object from the database
     */
    public Tour getTour(long tour_id) {

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define query string
        String selectQuery = "SELECT * FROM " + TABLE_TOURS + " WHERE " + KEY_TOUR_ID + " = " + tour_id;

        // Add query to database log
        Log.e(LOG, selectQuery);

        // Define cursor for query
        Cursor c = db.rawQuery(selectQuery, null);

        // As long as the cursor isn't null, move to the first
        if (c != null)
            c.moveToFirst();

        // Create member object from database
        Tour tour = new Tour();
        tour.setTour_id(c.getLong(c.getColumnIndex(KEY_TOUR_ID)));
        tour.setTour_name(c.getString(c.getColumnIndex(KEY_TOUR_NAME)));
        tour.setTour_visitor_number(c.getInt(c.getColumnIndex(KEY_TOUR_VISITOR_NUMBER)));
        tour.setTour_arrival_time(c.getLong(c.getColumnIndex(KEY_TOUR_ARRIVAL_TIME)));
        tour.setTour_departure_time(c.getLong(c.getColumnIndex(KEY_TOUR_DEPARTURE_TIME)));

        // Cursor Close
        c.close();

        // Return member object
        return tour;
    }

    /**
     * Updates a given tour record in the database
     *
     * @param tour tour record to be updated
     * @return number of rows affected by the update
     */
    public int updateTour(Tour tour) {

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Collect Content
        ContentValues values = new ContentValues();
        values.put(KEY_TOUR_ID, tour.getTour_id());
        values.put(KEY_TOUR_NAME, tour.getTour_name());
        values.put(KEY_TOUR_VISITOR_NUMBER, tour.getTour_visitor_number());
        values.put(KEY_TOUR_ARRIVAL_TIME, tour.getTour_arrival_time());
        values.put(KEY_TOUR_DEPARTURE_TIME, tour.getTour_departure_time());

        // Update Row
        return db.update(TABLE_TOURS, values, KEY_TOUR_ID + " = ?",
                new String[]{String.valueOf(tour.getTour_id())});
    }

    /**
     * Gets a list of all active tours in the database
     *
     * @return List of all active tour objects
     */
    public List<Tour> getActiveTours() {

        // Create visit list
        List<Tour> tours = new ArrayList<>();

        // Define query
        String selectQuery = "SELECT * FROM " + TABLE_TOURS + " WHERE " + KEY_TOUR_DEPARTURE_TIME + " = 0";

        // Add query to Database log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query - Try with Resourse Block
        try (Cursor c = db.rawQuery(selectQuery, null)) {

            // Loop through all rows and add to list
            if (c.moveToFirst()) {
                do {
                    Tour tour = new Tour();
                    tour.setTour_id(c.getLong(c.getColumnIndex(KEY_TOUR_ID)));
                    tour.setTour_name(c.getString(c.getColumnIndex(KEY_TOUR_NAME)));
                    tour.setTour_visitor_number(c.getInt(c.getColumnIndex(KEY_TOUR_VISITOR_NUMBER)));
                    tour.setTour_arrival_time(c.getLong(c.getColumnIndex(KEY_TOUR_ARRIVAL_TIME)));
                    tour.setTour_departure_time(c.getLong(c.getColumnIndex(KEY_TOUR_DEPARTURE_TIME)));

                    // Add to the visit list
                    tours.add(tour);
                } while (c.moveToNext());
            }
        }

        // Return list of visits
        return tours;
    }

    public List<Tour> getToursFromRange(long startDateTime, long endDateTime) {

        // Create visit list
        List<Tour> tours = new ArrayList<>();

        // Define query
        String selectQuery = "SELECT * FROM " + TABLE_TOURS + " WHERE " + KEY_TOUR_ARRIVAL_TIME + " > " + startDateTime
                + " AND " + KEY_TOUR_DEPARTURE_TIME + " < " + endDateTime;

        // Add query to Database log
        Log.e(LOG, selectQuery);

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define cursor for query - Try with Resourse Block
        try (Cursor c = db.rawQuery(selectQuery, null)) {

            // Loop through all rows and add to list
            if (c.moveToFirst()) {
                do {
                    Tour tour = new Tour();
                    tour.setTour_id(c.getLong(c.getColumnIndex(KEY_TOUR_ID)));
                    tour.setTour_name(c.getString(c.getColumnIndex(KEY_TOUR_NAME)));
                    tour.setTour_visitor_number(c.getInt(c.getColumnIndex(KEY_TOUR_VISITOR_NUMBER)));
                    tour.setTour_arrival_time(c.getLong(c.getColumnIndex(KEY_TOUR_ARRIVAL_TIME)));
                    tour.setTour_departure_time(c.getLong(c.getColumnIndex(KEY_TOUR_DEPARTURE_TIME)));

                    // Add to the visit list
                    tours.add(tour);
                } while (c.moveToNext());
            }
        }

        // Return list of visits
        return tours;
    }

    /**
     * Methods to run data analysis report
     * ____________________________________________________________________________________________
     */


    /**
     * Method to query the database to return totals for all membership types
     * @param startDate - starting bound for the search time frame
     * @param endDate - ending bound for the search time frame
     * @return Map containing membership type, total pairs
     */
    public Map<String, Integer> getMembershipTotals(long startDate, long endDate){

        String selectQuery = "SELECT " +
                                 "Members.membership_type, " +
                                 "Count(*) " +
                             "FROM Visits " +
                             "LEFT JOIN Members ON " +
                                 "Members.member_id = Visits.member_id " +
                             "WHERE " +
                                 "Visits.arrival_time > " + startDate + " and " +
                                 "Visits.arrival_time < " + endDate + " " +
                             "GROUP BY Members.membership_type";

        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> membershipTotals = new HashMap();

        try (Cursor c = db.rawQuery(selectQuery,null)) {
            while (c.moveToNext()){
                Log.e("Debugging", format("Member Type %s;  Visit Count %d", c.getString(0), c.getInt(1)));

                membershipTotals.put(c.getString(0),c.getInt(1));
            }
        }

        return membershipTotals;
    }

    /**
     * Method to query the database to return toal times for all purposes
     * @param startDate - starting bound for the search time frame
     * @param endDate - ending bound for the search time frame
     * @return Map containing membership type, total pairs
     */
    public Map<String, Integer> getPurposeTimes(long startDate, long endDate){

        String selectQuery = "SELECT " +
                                 "Visits.visit_purpose, " +
                             "sum(Visits.departure_time - Visits.arrival_time) as time " +
                             "FROM Visits " +
                             "WHERE " +
                                  "Visits.arrival_time > " + startDate + " and " +
                                  "Visits.arrival_time < " + endDate + " " +
                             "GROUP BY " +
                                  "Visits.visit_purpose";

        SQLiteDatabase db = this.getReadableDatabase();
        Map<String, Integer> PurposeTimes = new HashMap<>();

        try (Cursor c = db.rawQuery(selectQuery,null)){
            while(c.moveToNext()){
                Log.e("Debuging", format("Purpose: %s; Total Time: %d",c.getString(0), c.getInt(1)));

                PurposeTimes.put(c.getString(0),c.getInt(1));
            }
        }

       return PurposeTimes;
    }

    /**
     * Method to return the total number of visits within a given time frame
     * @param startDate - starting bound for the search time frame
     * @param endDate - ending bound for the search time frame
     * @return int containing the number of visits
     */
    public int getTotalVisits(long startDate, long endDate) {

        String selectQuery = "SELECT " +
                                 "Count(*)" +
                             "FROM Visits " +
                             "WHERE " +
                                "Visits.arrival_time > " + startDate + " and " +
                                "Visits.arrival_time < " + endDate;

        SQLiteDatabase db = this.getReadableDatabase();
        int totalVisits;

        try(Cursor c = db.rawQuery(selectQuery,null)){
            c.moveToNext();
            totalVisits = c.getInt(0);
            Log.e("Total Visits: ",Integer.toString(c.getInt(0)));
        }

        return totalVisits;
    }

    public int getUniqueVisits(long startDate, long endDate) {

        String selectQuery = "SELECT DISTINCT Visits.member_id " +
                "FROM Visits " +
                "WHERE " +
                "Visits.arrival_time > " + startDate + " and " +
                "Visits.arrival_time < " + endDate;

        SQLiteDatabase db = this.getReadableDatabase();

        int uniqueVisits = 0;

        try(Cursor c = db.rawQuery(selectQuery,null)){
            while(c.moveToNext()){
                c.moveToNext();
                uniqueVisits = c.getInt(0);
                Log.e("Debugging",c.getString(0));

            }
        }

        return uniqueVisits;
    }

    /**
     * _____________________________________________________________________________________________
     * Function used for fetching data for exporting database
     */

    public Cursor raw(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + table , new String[]{});
        return res;
    }

    public List<String> getTableNames(){

        List<String> tableList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", new String[]{});
        while (res.moveToNext()){
            tableList.add(res.getString(0));
        }
        res.close();

        return tableList;
    }
}
