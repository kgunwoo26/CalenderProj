package com.example.calenderproj;

import android.provider.BaseColumns;

public final class EventContract {
    public static final String DB_NAME="event.db";
    public static final int DATABASE_VERSION = 17; //컴파일 할 때마다 순차적으로 1씩 증가시키면 됩니다.
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private EventContract() {}
//상세정보 : (String)제목, (cal)날짜, 시작/종료시간, 장소, 메모
    public static class Events implements BaseColumns {
        public static final String TABLE_NAME="Events";
        public static final String KEY_TITLE="Title";
        public static final String KEY_DATE="Date";
        public static final String KEY_START_TIME="StartTime";
        public static final String KEY_END_TIME="EndTime";
        public static final String KEY_PLACE="Place";
        public static final String KEY_MEMO="Memo";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            KEY_TITLE + TEXT_TYPE + COMMA_SEP +
            KEY_DATE + TEXT_TYPE + COMMA_SEP +
            KEY_START_TIME + TEXT_TYPE + COMMA_SEP +
            KEY_END_TIME + TEXT_TYPE + COMMA_SEP +
            KEY_PLACE + TEXT_TYPE + COMMA_SEP +
            KEY_MEMO + TEXT_TYPE +  " )";
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}



