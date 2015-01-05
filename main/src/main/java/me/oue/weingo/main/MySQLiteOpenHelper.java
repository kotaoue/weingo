package me.oue.weingo.main;

/**
 * MySQL Lite関連処理
 * Created by kotaoue.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    // コンストラクタ
    public MySQLiteOpenHelper(Context context) {
        // 任意のデータベースファイル名と、バージョンを指定する
        super(context, "weingo.db", null, 1);
    }


    /**
     * このデータベースを初めて使用する時に実行される処理
     * テーブルの作成や初期データの投入を行う
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成。SQLの文法は通常のSQLiteと同様
        db.execSQL(
                "create table weight_table ("
                        + "date  integer primary key not null, "
                        + "weight text not null, "
                        + "forecast  integer )"
        );
        // 必要なら、ここで他のテーブルを作成したり、初期データを挿入したりする
    }


    /**
     * アプリケーションの更新などによって、データベースのバージョンが上がった場合に実行される処理
     ｊ* 今回は割愛
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 取りあえず、空実装でよい
    }
}